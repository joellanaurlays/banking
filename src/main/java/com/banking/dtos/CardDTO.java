/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banking.dtos;

import java.time.LocalDate;
import lombok.Data;

/**
 *
 * @author joella
 */

@Data
public class CardDTO {

    private String id;
    private String cardNumber;
    private LocalDate expiryDate;
    private String cardType;
    private boolean blocked;
    private String accountId;
    private int dailyLimit;
    private int monthlySpent;

    public CardDTO(String cardNumber, LocalDate expiryDate, String cardType, boolean blocked, String id0) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public CardDTO() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setId(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

