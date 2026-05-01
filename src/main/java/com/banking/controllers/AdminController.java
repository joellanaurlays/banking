/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banking.controllers;

import com.banking.dtos.AccountDTO;
import com.banking.dtos.CardDTO;
import com.banking.dtos.TransactionDTO;
import com.banking.dtos.UserDTO;
import com.banking.entities.Account;
import com.banking.entities.Card;
import com.banking.entities.Transaction;
import com.banking.entities.User;
import com.banking.mappers.AccountMapper;
import com.banking.mappers.CardMapper;
import com.banking.mappers.TransactionMapper;
import com.banking.mappers.UserMapper;
import com.banking.repositories.AccountRepository;
import com.banking.repositories.CardRepository;
import com.banking.repositories.TransactionRepository;
import com.banking.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author joella
 */
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminController {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final CardRepository cardRepository;
    private final AccountMapper accountMapper;
    private final TransactionMapper transactionMapper;
    private final CardMapper cardMapper;
    
    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .toList();
    }

    @GetMapping("/accounts")
    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(accountMapper::toDTO)
                .toList();
    }

    @GetMapping("/transactions")
    public List<TransactionDTO> getAllTransactions() {
        return transactionRepository.findAll()
                .stream()
                .map(transactionMapper::toDTO)
                .toList();
    }
    
     @GetMapping("/cards")
    public List<Card> getAllCards() {
        System.out.println("=== GET /api/admin/cards ===");
        List<Card> cards = cardRepository.findAll();
        System.out.println("Cards found: " + cards.size());
        return cards;
    }

    @GetMapping("/cards/requests")
    public List<Card> getCardRequests() {
        System.out.println("=== GET /api/admin/cards/requests ===");
        // Récupérer les cartes en attente (non bloquées ou toutes)
        List<Card> cards = cardRepository.findAll();
        return cards;
    }

    @PostMapping("/cards/{cardId}/validate")
    public Map<String, String> validateCard(@PathVariable String cardId) {
        System.out.println("=== POST /api/admin/cards/" + cardId + "/validate ===");
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Carte non trouvée"));
        card.setBlocked(false);
        cardRepository.save(card);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Carte validée avec succès");
        return response;
    }

    @PostMapping("/cards/{cardId}/block")
    public Map<String, String> blockCard(@PathVariable String cardId) {
        System.out.println("=== POST /api/admin/cards/" + cardId + "/block ===");
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Carte non trouvée"));
        card.setBlocked(true);
        cardRepository.save(card);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Carte bloquée avec succès");
        return response;
    }

    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        System.out.println("=== GET /api/admin/stats ===");
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", userRepository.count());
        stats.put("totalAccounts", accountRepository.count());
        stats.put("totalTransactions", transactionRepository.count());
        stats.put("totalCards", cardRepository.count());
        
        double totalVolume = transactionRepository.findAll()
                .stream()
                .mapToDouble(t -> t.getAmount().doubleValue())
                .sum();
        stats.put("totalVolume", totalVolume);
        
        long activeUsers = userRepository.findAll().stream().filter(User::isActive).count();
        stats.put("activeUsers", activeUsers);
        
        long activeCards = cardRepository.findAll().stream().filter(c -> !c.isBlocked()).count();
        stats.put("activeCards", activeCards);
        
        return stats;
    }
}
