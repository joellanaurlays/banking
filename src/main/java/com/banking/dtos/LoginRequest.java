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
public class LoginRequest {
    @NotBlank
    private String email;
    
    @NotBlank
    private String password;
}
