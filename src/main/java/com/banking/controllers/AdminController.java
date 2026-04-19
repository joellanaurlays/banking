/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banking.controllers;

import com.banking.entities.User;
import com.banking.entities.Transaction;
import com.banking.repositories.UserRepository;
import com.banking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author joella
 */
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @PutMapping("/users/{id}/role")
    public User updateUserRole(@PathVariable String id, @RequestParam String role) {
        User user = userRepository.findById(id).orElseThrow();
        user.setRole(com.banking.entities.Role.valueOf(role));
        return userRepository.save(user);
    }
}

