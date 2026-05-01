/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banking;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author joella
 */
public class GeneratePassword {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        // Générer le hash pour "admin123"
        String hashedPassword = encoder.encode("admin123");
        System.out.println("Mot de passe: admin123");
        System.out.println("Hash: " + hashedPassword);
        
        // Vérifier si le hash correspond
        boolean matches = encoder.matches("admin123", hashedPassword);
        System.out.println("Vérification: " + matches);
    }
}

