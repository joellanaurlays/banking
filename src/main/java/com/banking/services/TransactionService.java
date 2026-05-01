/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banking.services;

import com.banking.dtos.DepositRequest;
import com.banking.dtos.TransactionDTO;
import com.banking.dtos.TransferRequest;
import com.banking.dtos.WithdrawRequest;
import com.banking.entities.Account;
import com.banking.entities.Transaction;
import com.banking.entities.TransactionStatus;
import com.banking.entities.TransactionType;
import com.banking.mappers.TransactionMapper;
import com.banking.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

/**
 * Service de gestion des transactions bancaires
 * @author joella
 */
@Service
@RequiredArgsConstructor
public class TransactionService {
    
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final TransactionMapper transactionMapper;
    private final LoggingService loggingService;
    private final NotificationService notificationService;
    
    // Générer une référence unique
    private String generateReference() {
        return "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    // Générer un numéro de reçu
    private String generateReceiptNumber() {
        return "RCP-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
    
    // Effectuer un dépôt
    @Transactional
    public TransactionDTO deposit(String accountNumber, DepositRequest request) {
        Account account = accountService.getAccountByNumberWithUser(accountNumber);
        
        BigDecimal newBalance = account.getBalance().add(request.getAmount());
        
        Transaction transaction = Transaction.builder()
                .reference(generateReference())
                .type(TransactionType.DEPOSIT)
                .amount(request.getAmount())
                .description(request.getDescription() != null ? request.getDescription() : "Dépôt d'argent")
                .account(account)
                .balanceAfterTransaction(newBalance)
                .transactionDate(LocalDateTime.now())
                .status(TransactionStatus.COMPLETED)
                .receiptNumber(generateReceiptNumber())
                .build();
        
        // Mettre à jour le solde
        accountService.updateBalance(account, newBalance);
        
        Transaction savedTransaction = transactionRepository.save(transaction);
        
        // LOGGING : Enregistrer l'opération critique
        loggingService.logOperation(
            "DEPOSIT",
            account.getUser().getId(),
            account.getAccountNumber(),
            String.format("Montant: %s Ar, Nouveau solde: %s Ar", request.getAmount(), newBalance)
        );
        
        // NOTIFICATION : Envoyer un email à l'utilisateur
        notificationService.notifyTransaction(
            account.getUser().getEmail(),
            "DÉPÔT",
            request.getAmount().toString(),
            account.getAccountNumber(),
            newBalance.toString()
        );
        
        return transactionMapper.toDTO(savedTransaction);
    }
    
    // Effectuer un retrait
    @Transactional
    public TransactionDTO withdraw(String accountNumber, WithdrawRequest request) {
        Account account = accountService.getAccountByNumberWithUser(accountNumber);
        
        // Vérifier le solde
        accountService.checkSufficientBalance(account, request.getAmount());
        
        BigDecimal newBalance = account.getBalance().subtract(request.getAmount());
        
        Transaction transaction = Transaction.builder()
                .reference(generateReference())
                .type(TransactionType.WITHDRAWAL)
                .amount(request.getAmount())
                .description(request.getDescription() != null ? request.getDescription() : "Retrait d'argent")
                .account(account)
                .balanceAfterTransaction(newBalance)
                .transactionDate(LocalDateTime.now())
                .status(TransactionStatus.COMPLETED)
                .receiptNumber(generateReceiptNumber())
                .build();
        
        // Mettre à jour le solde
        accountService.updateBalance(account, newBalance);
        
        Transaction savedTransaction = transactionRepository.save(transaction);
        
        // LOGGING : Enregistrer l'opération critique
        loggingService.logOperation(
            "WITHDRAWAL",
            account.getUser().getId(),
            account.getAccountNumber(),
            String.format("Montant: %s Ar, Nouveau solde: %s Ar", request.getAmount(), newBalance)
        );
        
        // NOTIFICATION : Envoyer un email à l'utilisateur
        notificationService.notifyTransaction(
            account.getUser().getEmail(),
            "RETRAIT",
            request.getAmount().toString(),
            account.getAccountNumber(),
            newBalance.toString()
        );
        
        return transactionMapper.toDTO(savedTransaction);
    }
    
    // Effectuer un virement
    @Transactional
    public TransactionDTO transfer(TransferRequest request) {
        Account sourceAccount = accountService.getAccountByNumberWithUser(request.getSourceAccountNumber());
        Account targetAccount = accountService.getAccountByNumberWithUser(request.getTargetAccountNumber());
    
        // Vérifier qu'on ne vire pas vers le même compte
        if (request.getSourceAccountNumber().equals(request.getTargetAccountNumber())) {
            loggingService.logError("TRANSFER", sourceAccount.getUser().getId(), "Tentative de virement vers le même compte");
            throw new RuntimeException("Impossible d'effectuer un virement vers le même compte");
        }
        
        // Vérifier le solde du compte source
        accountService.checkSufficientBalance(sourceAccount, request.getAmount());
        
        BigDecimal newSourceBalance = sourceAccount.getBalance().subtract(request.getAmount());
        BigDecimal newTargetBalance = targetAccount.getBalance().add(request.getAmount());
        
        // Créer la transaction
        Transaction transaction = Transaction.builder()
                .reference(generateReference())
                .type(TransactionType.TRANSFER)
                .amount(request.getAmount())
                .description(request.getDescription() != null ? request.getDescription() : "Virement bancaire")
                .account(sourceAccount)
                .targetAccount(targetAccount)
                .balanceAfterTransaction(newSourceBalance)
                .transactionDate(LocalDateTime.now())
                .status(TransactionStatus.COMPLETED)
                .receiptNumber(generateReceiptNumber())
                .build();
        
        // Mettre à jour les soldes
        accountService.updateBalance(sourceAccount, newSourceBalance);
        accountService.updateBalance(targetAccount, newTargetBalance);
        
        Transaction savedTransaction = transactionRepository.save(transaction);
        
        // LOGGING : Enregistrer l'opération critique (virement)
        loggingService.logOperation(
            "TRANSFER",
            sourceAccount.getUser().getId(),
            sourceAccount.getAccountNumber(),
            String.format("Montant: %s Ar, Vers: %s, Nouveau solde source: %s Ar", 
                request.getAmount(), targetAccount.getAccountNumber(), newSourceBalance)
        );
        
        // NOTIFICATION : Envoyer un email à l'utilisateur source
        notificationService.notifyTransaction(
            sourceAccount.getUser().getEmail(),
            "VIREMENT ÉMIS",
            request.getAmount().toString(),
            sourceAccount.getAccountNumber(),
            newSourceBalance.toString()
        );
        
        // NOTIFICATION : Envoyer un email à l'utilisateur cible (optionnel)
        notificationService.notifyTransaction(
            targetAccount.getUser().getEmail(),
            "VIREMENT REÇU",
            request.getAmount().toString(),
            targetAccount.getAccountNumber(),
            newTargetBalance.toString()
        );
        
        return transactionMapper.toDTO(savedTransaction);
    }
    
    // Récupérer l'historique d'un compte
    public Page<TransactionDTO> getTransactionHistory(String accountId, Pageable pageable) {
        return transactionRepository.findByAccountIdOrderByTransactionDateDesc(accountId, pageable)
                .map(transactionMapper::toDTO);
    }
    
    // Récupérer les transactions d'un compte par type
    public Page<TransactionDTO> getTransactionsByType(String accountId, TransactionType type, Pageable pageable) {
        return transactionRepository.findByAccountIdAndType(accountId, type, pageable)
                .map(transactionMapper::toDTO);
    }
    
    // Générer un relevé bancaire
    public List<TransactionDTO> generateStatement(String accountId, LocalDateTime start, LocalDateTime end) {
        List<TransactionDTO> statement = transactionRepository.findByAccountIdAndTransactionDateBetween(accountId, start, end)
                .stream()
                .map(transactionMapper::toDTO)
                .collect(Collectors.toList());
        
        // LOGGING : Enregistrer la génération de relevé
        Account account = accountService.getAccountByIdEntity(accountId);
        loggingService.logOperation(
            "GENERATE_STATEMENT",
            account.getUser().getId(),
            account.getAccountNumber(),
            String.format("Période: %s à %s, %d transactions", start, end, statement.size())
        );
        
        return statement;
    }
}