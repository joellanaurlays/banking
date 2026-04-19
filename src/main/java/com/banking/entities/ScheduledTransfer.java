/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banking.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;

/**
 *
 * @author joella
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class ScheduledTransfer extends BaseEntity {
    
    @ManyToOne
    private Account sourceAccount;
    
    @ManyToOne
    private Account targetAccount;
    
    private BigDecimal amount;
    private String description;
    private LocalDateTime scheduledDate;
    private String frequency; // ONCE, DAILY, WEEKLY, MONTHLY
    private boolean active;
    private int executionCount;
    private LocalDateTime lastExecution;
}
