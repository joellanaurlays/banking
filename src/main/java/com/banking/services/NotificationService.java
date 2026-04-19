/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banking.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author joella
 */
@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public void sendEmail(String to, String subject, String body) {
        // Pour l'instant, on logue seulement l'email
        logger.info("📧 EMAIL - À: {}, Sujet: {}, Corps: {}", to, subject, body);
        
        // TODO: Configurer l'envoi d'emails avec spring-boot-starter-mail
        // Une fois la dépendance ajoutée, décommentez le code ci-dessous
        /*
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
        */
    }

    public void notifyTransaction(String userEmail, String transactionType, String amount, String accountNumber, String newBalance) {
        String subject = "Transaction bancaire - Banking App";
        String body = String.format("""
            Bonjour,
            
            Une transaction a été effectuée sur votre compte.
            
            Type: %s
            Montant: %s Ar
            Compte: %s
            Nouveau solde: %s Ar
            Date: %s
            
            Cordialement,
            Banking App Team
            """, transactionType, amount, accountNumber, newBalance, java.time.LocalDateTime.now());
        
        sendEmail(userEmail, subject, body);
    }
    
    public void notifyScheduledTransfer(String userEmail, String amount, String sourceAccount, String targetAccount) {
        String subject = "Virement programmé - Banking App";
        String body = String.format("""
            Bonjour,
            
            Un virement programmé a été exécuté.
            
            Montant: %s Ar
            Compte source: %s
            Compte destinataire: %s
            Date: %s
            
            Cordialement,
            Banking App Team
            """, amount, sourceAccount, targetAccount, java.time.LocalDateTime.now());
        
        sendEmail(userEmail, subject, body);
    }
}
