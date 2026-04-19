/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banking.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author joella
 */
@Data
public class CreateAccountRequest {
    @NotBlank(message = "Le type de compte est obligatoire")
    private String accountType;  // "CURRENT" ou "SAVINGS"
    
    private String currency;
    
    @NotNull(message = "L'ID utilisateur est obligatoire")
    private String userId;
}
    

