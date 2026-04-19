/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banking.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author joella
 */
@Entity
@DiscriminatorValue("SAVINGS")
@Getter
@Setter
@NoArgsConstructor
public class SavingsAccount extends Account {
    
    @Column(nullable = false)
    private BigDecimal interestRate = new BigDecimal("0.03"); // 3% annuel
    
    private LocalDateTime lastInterestCalculation;
}

    

