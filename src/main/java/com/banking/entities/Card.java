/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banking.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import lombok.EqualsAndHashCode;

/**
 *
 * @author joella
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Card extends BaseEntity {
    
    @ManyToOne
    private Account account;
    
    private String cardNumber;
    private String pinCode; // Hashé
    private LocalDate expiryDate;
    private String cardType; // VISA, MASTERCARD
    private boolean blocked;
    private int dailyLimit;
    private int monthlySpent;   
}

    
