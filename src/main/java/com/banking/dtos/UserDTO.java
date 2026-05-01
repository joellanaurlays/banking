/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banking.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;


/**
 *
 * @author joella
 */
@Data
public class UserDTO {

    private String id;

    @Email(message = "Format d'email invalide")
    private String email;

    @NotBlank(message = "Le prénom est obligatoire")
    private String firstName;

    @NotBlank(message = "Le nom est obligatoire")
    private String lastName;

    @Pattern(
        regexp = "^$|^[0-9]{10}$",
        message = "Le téléphone doit contenir 10 chiffres"
    )
    private String phone;

    private String address;

    private String role;

    private boolean active;

    private String createdAt;
}
    

