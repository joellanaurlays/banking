/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banking.mappers;

import com.banking.dtos.CardDTO;
import com.banking.entities.Card;
import org.springframework.stereotype.Component;

/**
 *
 * @author joella
 */
@Component
public class CardMapper {

    public CardDTO toDTO(Card card) {
        return new CardDTO(
                card.getCardNumber(),
                card.getExpiryDate(),
                card.getCardType(),
                card.isBlocked(),
                card.getAccount().getId()
        );
    }
}