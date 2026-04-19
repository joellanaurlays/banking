/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banking.controllers;

import com.banking.dtos.AccountDTO;
import com.banking.dtos.CreateAccountRequest;
import com.banking.services.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author joella
 */
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Tag(name = "Gestion des comptes", description = "API pour gérer les comptes bancaires")
@CrossOrigin(origins = "*", maxAge = 3600) 
public class AccountController {
    
    private final AccountService accountService;
    
    @PostMapping
    @Operation(summary = "Créer un nouveau compte")
    public ResponseEntity<AccountDTO> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        return new ResponseEntity<>(accountService.createAccount(request), HttpStatus.CREATED);
    }
    
    @GetMapping("/user/{userId}")
    @Operation(summary = "Liste des comptes d'un utilisateur")
    public ResponseEntity<List<AccountDTO>> getAccountsByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(accountService.getAccountsByUserId(userId));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un compte par ID")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable String id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }
    
    @PostMapping("/{id}/suspend")
    @Operation(summary = "Suspendre un compte")
    public ResponseEntity<Void> suspendAccount(@PathVariable String id) {
        accountService.suspendAccount(id);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/{id}/activate")
    @Operation(summary = "Activer un compte")
    public ResponseEntity<Void> activateAccount(@PathVariable String id) {
        accountService.activateAccount(id);
        return ResponseEntity.ok().build();
    }
}
