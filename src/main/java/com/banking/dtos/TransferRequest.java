/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banking.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;

/**
 *
 * @author joella
 */
@Data
public class TransferRequest {
    @NotBlank(message = "Le numéro de compte source est obligatoire")
    private String sourceAccountNumber;
    
    @NotBlank(message = "Le numéro de compte cible est obligatoire")
    private String targetAccountNumber;
    
    @NotNull(message = "Le montant est obligatoire")
    @Positive(message = "Le montant doit être positif")
    private BigDecimal amount;
    
    private String description;
}
    

