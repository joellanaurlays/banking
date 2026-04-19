/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banking.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author joella
 */
@Service
public class LoggingService {

    private static final Logger logger = LoggerFactory.getLogger(LoggingService.class);

    public void logOperation(String operation, String userId, String accountNumber, String details) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String logMessage = String.format("[%s] OPERATION: %s | USER: %s | ACCOUNT: %s | DETAILS: %s",
                timestamp, operation, userId, accountNumber, details);
        
        logger.info(logMessage);
        
        // Pour les opérations critiques, aussi enregistrer dans un fichier séparé
        if (isCriticalOperation(operation)) {
            logger.warn("CRITICAL: " + logMessage);
        }
    }

    private boolean isCriticalOperation(String operation) {
        return operation.equals("TRANSFER") || 
               operation.equals("WITHDRAWAL") ||
               operation.equals("DELETE_USER") ||
               operation.equals("SUSPEND_ACCOUNT");
    }

    public void logError(String operation, String userId, String errorMessage) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        logger.error("[{}] ERROR: {} | USER: {} | MESSAGE: {}", timestamp, operation, userId, errorMessage);
    }
}
