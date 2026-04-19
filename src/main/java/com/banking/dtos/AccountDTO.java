/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banking.dtos;

import lombok.Data;
import java.math.BigDecimal;

/**
 *
 * @author joella
 */
@Data
public class AccountDTO {
    private String id;
    private String accountNumber;
    private BigDecimal balance;
    private String currency;
    private String accountType;  // "CURRENT" ou "SAVINGS"
    private BigDecimal overdraftLimit;  // Pour compte courant
    private BigDecimal interestRate;    // Pour compte épargne
    private String userId;
    private String userFullName;
    private boolean active;
    private String lastActivityAt;
}
    

