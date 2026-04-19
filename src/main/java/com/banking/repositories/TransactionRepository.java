/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banking.repositories;

import com.banking.entities.Transaction;
import com.banking.entities.TransactionStatus;
import com.banking.entities.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author joella
 */

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    
    // Trouver les transactions par ID de compte, triées par date décroissante
    Page<Transaction> findByAccountIdOrderByTransactionDateDesc(String accountId, Pageable pageable);
    
    // Trouver les transactions par ID de compte entre deux dates
    List<Transaction> findByAccountIdAndTransactionDateBetween(String accountId, LocalDateTime start, LocalDateTime end);
    
    // Trouver les transactions par ID de compte et par type
    @Query("SELECT t FROM Transaction t WHERE t.account.id = :accountId AND t.type = :type")
    Page<Transaction> findByAccountIdAndType(@Param("accountId") String accountId,
                                              @Param("type") TransactionType type,
                                              Pageable pageable);
    
    // Trouver les transactions par statut
    List<Transaction> findByStatus(TransactionStatus status);
    
    // Trouver les transactions par référence
    Transaction findByReference(String reference);
    
    // Compter les transactions par compte
    long countByAccountId(String accountId);
} 