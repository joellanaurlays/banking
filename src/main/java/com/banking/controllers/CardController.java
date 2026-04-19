/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banking.controllers;

import com.banking.entities.Card;
import com.banking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author joella
 */
@RestController
@RequestMapping("/api/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping("/request/{accountId}")
    public Card requestCard(@PathVariable String accountId, @RequestParam String cardType) {
        return cardService.requestCard(accountId, cardType);
    }

    @PostMapping("/{cardId}/block")
    public void blockCard(@PathVariable String cardId) {
        cardService.blockCard(cardId);
    }

    @GetMapping("/account/{accountId}")
    public List<Card> getAccountCards(@PathVariable String accountId) {
        return cardService.getCardsByAccount(accountId);
    }
}

