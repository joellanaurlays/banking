/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banking.services;

import com.banking.entities.Account;
import com.banking.entities.Card;
import com.banking.repositories.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 *
 * @author joella
 */
@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final AccountService accountService;

    private String generateCardNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            if (i > 0) sb.append(" ");
            sb.append(String.format("%04d", random.nextInt(10000)));
        }
        return sb.toString();
    }

    @Transactional
    public Card requestCard(String accountId, String cardType) {
        Account account = accountService.getAccountByIdEntity(accountId);
        
        Card card = new Card();
        card.setAccount(account);
        card.setCardNumber(generateCardNumber());
        card.setCardType(cardType);
        card.setExpiryDate(LocalDate.now().plusYears(4));
        card.setBlocked(false);
        card.setDailyLimit(500000);
        card.setMonthlySpent(0);
        card.setPinCode(UUID.randomUUID().toString().substring(0, 4));
        
        return cardRepository.save(card);
    }

    @Transactional
    public void blockCard(String cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow();
        card.setBlocked(true);
        cardRepository.save(card);
    }

    public List<Card> getCardsByAccount(String accountId) {
        return cardRepository.findByAccountId(accountId);
    }
}

