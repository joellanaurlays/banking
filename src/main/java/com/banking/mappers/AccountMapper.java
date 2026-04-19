/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banking.mappers;

import com.banking.dtos.AccountDTO;
import com.banking.entities.Account;
import com.banking.entities.CurrentAccount;
import com.banking.entities.SavingsAccount;
import org.springframework.stereotype.Component;

/**
 *
 * @author joella
 */
@Component
public class AccountMapper {
    
    public AccountDTO toDTO(Account account) {
        if (account == null) return null;
        
        AccountDTO dto = new AccountDTO();
        dto.setId(account.getId());
        dto.setAccountNumber(account.getAccountNumber());
        dto.setBalance(account.getBalance());
        dto.setCurrency(account.getCurrency());
        dto.setActive(account.isActive());
        
        if (account.getLastActivityAt() != null) {
            dto.setLastActivityAt(account.getLastActivityAt().toString());
        }
        
        // Déterminer le type de compte
        if (account instanceof CurrentAccount) {
            dto.setAccountType("CURRENT");
            dto.setOverdraftLimit(((CurrentAccount) account).getOverdraftLimit());
        } else if (account instanceof SavingsAccount) {
            dto.setAccountType("SAVINGS");
            dto.setInterestRate(((SavingsAccount) account).getInterestRate());
        }
        
        // Ajouter les infos utilisateur
        if (account.getUser() != null) {
            dto.setUserId(account.getUser().getId());
            dto.setUserFullName(account.getUser().getFirstName() + " " + account.getUser().getLastName());
        }
        
        return dto;
    }
}

    
