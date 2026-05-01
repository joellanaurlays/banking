/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banking.dtos;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author joella
 */
@Data
@AllArgsConstructor
public class AccountWithUserDTO {
    private String id;
    private String accountNumber;
    private BigDecimal balance;
    private String currency;
    private String userId;
    private String userEmail;
    private String userFirstName;
    private String userLastName;
}

