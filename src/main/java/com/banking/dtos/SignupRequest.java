/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banking.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 *
 * @author joella
 */
@Data
public class SignupRequest {
    @NotBlank
    @Email
    private String email;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
    
    @NotBlank
    private String firstName;
    
    @NotBlank
    private String lastName;
    
    private String phone;
    private String address;
}

