/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banking.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author joella
 */
@Data
@AllArgsConstructor
public class AuthResponseDTO {
    private String token;
    private String type = "Bearer";
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
}
  
