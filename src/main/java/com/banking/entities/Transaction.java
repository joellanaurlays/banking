/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banking.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author joella
 */
@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction extends BaseEntity {
    
    @Column(nullable = false, unique = true)
    private String reference;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;  // Utilise l'enum importé
    
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;
    
    @Column(length = 500)
    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_account_id")
    private Account targetAccount;
    
    private BigDecimal balanceAfterTransaction;
    
    private LocalDateTime transactionDate;
    
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;  // Utilise l'enum importé
    
    private String receiptNumber;
}
