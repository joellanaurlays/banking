/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banking.controllers;

import com.banking.dtos.DepositRequest;
import com.banking.dtos.TransactionDTO;
import com.banking.dtos.TransferRequest;
import com.banking.dtos.WithdrawRequest;
import com.banking.services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author joella
 */
@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Tag(name = "Opérations bancaires", description = "API pour les dépôts, retraits et virements")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TransactionController {
    
    private final TransactionService transactionService;
    
    @PostMapping("/deposit/{accountNumber}")
    @Operation(summary = "Effectuer un dépôt")
    public ResponseEntity<TransactionDTO> deposit(
            @PathVariable String accountNumber,
            @Valid @RequestBody DepositRequest request) {
        return ResponseEntity.ok(transactionService.deposit(accountNumber, request));
    }
    
    @PostMapping("/withdraw/{accountNumber}")
    @Operation(summary = "Effectuer un retrait")
    public ResponseEntity<TransactionDTO> withdraw(
            @PathVariable String accountNumber,
            @Valid @RequestBody WithdrawRequest request) {
        return ResponseEntity.ok(transactionService.withdraw(accountNumber, request));
    }
    
    @PostMapping("/transfer")
    @Operation(summary = "Effectuer un virement")
    public ResponseEntity<TransactionDTO> transfer(@Valid @RequestBody TransferRequest request) {
        return ResponseEntity.ok(transactionService.transfer(request));
    }
    
    @GetMapping("/history/{accountId}")
    @Operation(summary = "Historique des transactions d'un compte")
    public ResponseEntity<Page<TransactionDTO>> getTransactionHistory(
            @PathVariable String accountId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(transactionService.getTransactionHistory(accountId, pageable));
    }
}

