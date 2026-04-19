/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banking.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joella
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    private String phone;
    
    private String address;
    
    @Enumerated(EnumType.STRING)
    private Role role;  // Utilise l'enum importé
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // Dans User.java
    @Builder.Default
    private List<Account> accounts = new ArrayList<>();
}
