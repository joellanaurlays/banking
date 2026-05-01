/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banking.services;

import com.banking.dtos.AccountDTO;
import com.banking.dtos.CreateAccountRequest;
import com.banking.entities.Account;
import com.banking.entities.CurrentAccount;
import com.banking.entities.SavingsAccount;
import com.banking.entities.User;
import com.banking.exceptions.ResourceNotFoundException;
import com.banking.mappers.AccountMapper;
import com.banking.repositories.AccountRepository;
import com.banking.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author joella
 */
@Service
@RequiredArgsConstructor
public class AccountService {
    
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountMapper accountMapper;
    
    // Générer un numéro de compte unique (format XXXX XXXX XXXX XXXX)
    private String generateAccountNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            if (i > 0) sb.append(" ");
            sb.append(String.format("%04d", random.nextInt(10000)));
        }
        return sb.toString();
    }
    
    // Créer un compte
    public AccountDTO createAccount(CreateAccountRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
        
        Account account;
        
        if ("CURRENT".equalsIgnoreCase(request.getAccountType())) {
            CurrentAccount currentAccount = new CurrentAccount();
            currentAccount.setOverdraftLimit(BigDecimal.ZERO);
            account = currentAccount;
        } else if ("SAVINGS".equalsIgnoreCase(request.getAccountType())) {
            SavingsAccount savingsAccount = new SavingsAccount();
            savingsAccount.setInterestRate(new BigDecimal("0.03"));
            savingsAccount.setLastInterestCalculation(LocalDateTime.now());
            account = savingsAccount;
        } else {
            throw new RuntimeException("Type de compte invalide. Utilisez CURRENT ou SAVINGS");
        }
        
        account.setAccountNumber(generateAccountNumber());
        account.setBalance(BigDecimal.ZERO);
        account.setCurrency(request.getCurrency() != null ? request.getCurrency() : "MGA");
        account.setUser(user);
        account.setLastActivityAt(LocalDateTime.now());
        
        Account savedAccount = accountRepository.save(account);
        return accountMapper.toDTO(savedAccount);
    }
    
    // Récupérer tous les comptes d'un utilisateur
    @Transactional(readOnly = true)
    public List<AccountDTO> getAccountsByUserId(String userId) {
        return accountRepository.findAccountsByUserId(userId)
                .stream()
                .map(accountMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public Account getAccountByIdWithUser(String id) {
        return accountRepository.findById(id)
                .map(account -> {
                    // Forcer le chargement des relations LAZY
                    Hibernate.initialize(account.getUser());
                    Hibernate.initialize(account.getTransactions());
                    return account;
                })
                .orElseThrow(() -> new ResourceNotFoundException("Compte non trouvé"));
    }

    @Transactional(readOnly = true)
    public Account getAccountByIdEntity(String id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Compte non trouvé"));
    }
    
    // Récupérer un compte par son numéro
    public Account getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Compte non trouvé avec le numéro: " + accountNumber));
    }
    
    // Récupérer un compte par son ID
    public AccountDTO getAccountById(String id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Compte non trouvé"));
        return accountMapper.toDTO(account);
    }
    
    // Récupérer un compte avec son utilisateur (FETCH JOIN)
    @Transactional(readOnly = true)
    public Account getAccountByIdEntityWithUser(String id) {
        return accountRepository.findByIdWithUser(id)
                .orElseThrow(() -> new ResourceNotFoundException("Compte non trouvé avec l'ID: " + id));
    }

    // Récupérer un compte par numéro avec son utilisateur
    @Transactional(readOnly = true)
    public Account getAccountByNumberWithUser(String accountNumber) {
        return accountRepository.findByAccountNumberWithUser(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Compte non trouvé avec le numéro: " + accountNumber));
    }

    // Récupérer les comptes d'un utilisateur avec leurs relations
    @Transactional(readOnly = true)
    public List<Account> getAccountsByUserIdWithUser(String userId) {
        return accountRepository.findByUserIdWithUser(userId);
    }
    
     // Récupérer un compte avec chargement forcé des relations LAZY
    @Transactional(readOnly = true)
    public Account getAccountByIdWithLazyLoaded(String id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Compte non trouvé"));
        // Forcer le chargement des relations LAZY
        Hibernate.initialize(account.getUser());
        Hibernate.initialize(account.getTransactions());
        return account;
    }

    // Vérifier si un compte a assez de solde
    public void checkSufficientBalance(Account account, BigDecimal amount) {
        if (account instanceof CurrentAccount) {
            CurrentAccount currentAccount = (CurrentAccount) account;
            BigDecimal availableBalance = account.getBalance().add(currentAccount.getOverdraftLimit());
            if (availableBalance.compareTo(amount) < 0) {
                throw new RuntimeException("Solde insuffisant. Solde: " + account.getBalance() +
                        ", Découvert autorisé: " + currentAccount.getOverdraftLimit());
            }
        } else {
            if (account.getBalance().compareTo(amount) < 0) {
                throw new RuntimeException("Solde insuffisant. Solde actuel: " + account.getBalance());
            }
        }
    }
    
      // Mettre à jour le solde
    @Transactional
    public void updateBalance(Account account, BigDecimal newBalance) {
        account.setBalance(newBalance);
        account.setLastActivityAt(LocalDateTime.now());
        accountRepository.save(account);
    }

    // Suspendre un compte
    @Transactional
    public void suspendAccount(String id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Compte non trouvé"));
        account.setActive(false);
        accountRepository.save(account);
    }

    // Activer un compte
    @Transactional
    public void activateAccount(String id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Compte non trouvé"));
        account.setActive(true);
        accountRepository.save(account);
    }
}
    
