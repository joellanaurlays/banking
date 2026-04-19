/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banking.services;

import com.banking.dtos.TransferRequest;
import com.banking.entities.ScheduledTransfer;
import com.banking.repositories.ScheduledTransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 *
 * @author joella
 */
@Service
@RequiredArgsConstructor
@EnableScheduling
public class ScheduledTransferService {

    private final ScheduledTransferRepository scheduledTransferRepository;
    private final TransactionService transactionService;
    private final NotificationService notificationService;
    private final AccountService accountService;

    @Scheduled(cron = "0 */5 * * * *") // Toutes les 5 minutes
    @Transactional
    public void processScheduledTransfers() {
        LocalDateTime now = LocalDateTime.now();
        var pendingTransfers = scheduledTransferRepository.findByScheduledDateBeforeAndActiveTrue(now);

        for (ScheduledTransfer transfer : pendingTransfers) {
            try {
                // Exécuter le virement
                TransferRequest request = new TransferRequest();
                request.setSourceAccountNumber(transfer.getSourceAccount().getAccountNumber());
                request.setTargetAccountNumber(transfer.getTargetAccount().getAccountNumber());
                request.setAmount(transfer.getAmount());
                request.setDescription(transfer.getDescription() != null ? transfer.getDescription() : "Virement programmé");
                
                transactionService.transfer(request);
                
                transfer.setExecutionCount(transfer.getExecutionCount() + 1);
                transfer.setLastExecution(now);
                
                // Planifier la prochaine exécution si nécessaire
                if ("ONCE".equals(transfer.getFrequency())) {
                    transfer.setActive(false);
                } else {
                    transfer.setScheduledDate(calculateNextDate(transfer));
                }
                
                scheduledTransferRepository.save(transfer);
                
                // Notifier l'utilisateur
                notificationService.notifyScheduledTransfer(
                    transfer.getSourceAccount().getUser().getEmail(),
                    transfer.getAmount().toString(),
                    transfer.getSourceAccount().getAccountNumber(),
                    transfer.getTargetAccount().getAccountNumber()
                );
                
            } catch (Exception e) {
                // Log l'erreur
                System.err.println("Erreur lors du virement programmé: " + e.getMessage());
            }
        }
    }

    private LocalDateTime calculateNextDate(ScheduledTransfer transfer) {
        LocalDateTime next = transfer.getScheduledDate();
        switch (transfer.getFrequency()) {
            case "DAILY":
                next = next.plusDays(1);
                break;
            case "WEEKLY":
                next = next.plusWeeks(1);
                break;
            case "MONTHLY":
                next = next.plusMonths(1);
                break;
            default:
                next = next.plusDays(1);
        }
        return next;
    }
    
    @Transactional
    public ScheduledTransfer scheduleTransfer(ScheduledTransfer transfer) {
        transfer.setActive(true);
        transfer.setExecutionCount(0);
        return scheduledTransferRepository.save(transfer);
    }
}
