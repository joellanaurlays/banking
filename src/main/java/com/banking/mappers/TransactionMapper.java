/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banking.mappers;

import com.banking.dtos.TransactionDTO;
import com.banking.entities.Transaction;
import org.springframework.stereotype.Component;

/**
 *
 * @author joella
 */
@Component
public class TransactionMapper {
    
    public TransactionDTO toDTO(Transaction transaction) {
        if (transaction == null) return null;
        
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setReference(transaction.getReference());
        dto.setAmount(transaction.getAmount());
        dto.setDescription(transaction.getDescription());
        dto.setBalanceAfterTransaction(transaction.getBalanceAfterTransaction());
        dto.setTransactionDate(transaction.getTransactionDate());
        dto.setReceiptNumber(transaction.getReceiptNumber());
        
        if (transaction.getType() != null) {
            dto.setType(transaction.getType().name());
        }
        
        if (transaction.getStatus() != null) {
            dto.setStatus(transaction.getStatus().name());
        }
        
        if (transaction.getAccount() != null) {
            dto.setAccountNumber(transaction.getAccount().getAccountNumber());
        }
        
        if (transaction.getTargetAccount() != null) {
            dto.setTargetAccountNumber(transaction.getTargetAccount().getAccountNumber());
        }
        
        return dto;
    }
}

    
