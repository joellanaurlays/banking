/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banking.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 *
 * @author joella
 */
@Data
public class AuthRequestDTO {
    @NotBlank(message = "L'email est obligatoire")
    private String email;
    
    @NotBlank(message = "Le mot de passe est obligatoire")
    private String password;
}
    

