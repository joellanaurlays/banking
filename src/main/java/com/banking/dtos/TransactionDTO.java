/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banking.dtos;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author joella
 */
@Data
public class TransactionDTO {
    private String id;
    private String reference;
    private String type;
    private BigDecimal amount;
    private String description;
    private String accountNumber;
    private String targetAccountNumber;
    private BigDecimal balanceAfterTransaction;
    private LocalDateTime transactionDate;
    private String status;
    private String receiptNumber;
}
    

