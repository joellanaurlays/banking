/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banking.repositories;

import com.banking.dtos.AccountWithUserDTO;
import com.banking.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author joella
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findByAccountNumber(String accountNumber);
    // List<Account> findByUser__id(String userId);
    boolean existsByAccountNumber(String accountNumber);
    List<Account> findByUserId(String userId);
    
    @Query("SELECT a FROM Account a LEFT JOIN FETCH a.user WHERE a.id = :id")
    Optional<Account> findByIdWithUser(@Param("id") String id);
    
    @Query("SELECT a FROM Account a LEFT JOIN FETCH a.user WHERE a.accountNumber = :accountNumber")
    Optional<Account> findByAccountNumberWithUser(@Param("accountNumber") String accountNumber);
    
    @Query("SELECT a FROM Account a LEFT JOIN FETCH a.user WHERE a.user.id = :userId")
    List<Account> findByUserIdWithUser(@Param("userId") String userId);
    
    @Query("SELECT a FROM Account a LEFT JOIN FETCH a.user LEFT JOIN FETCH a.transactions WHERE a.id = :id")
    Optional<Account> findByIdWithUserAndTransactions(@Param("id") String id);
    
    @Query("SELECT new com.banking.dtos.AccountWithUserDTO(a.id, a.accountNumber, a.balance, a.currency, u.id, u.email, u.firstName, u.lastName) " +
       "FROM Account a JOIN a.user u WHERE a.id = :id")
    Optional<AccountWithUserDTO> findAccountWithUserById(@Param("id") String id);
    
    @Query("SELECT SUM(a.balance) FROM Account a WHERE a.user.id = :userId")
    BigDecimal getTotalBalanceByUser(@Param("userId") String userId);
    
    @Query("SELECT a FROM Account a JOIN FETCH a.user WHERE a.user.id = :userId")
    List<Account> findAccountsByUserId(@Param("userId") String userId);
}
    

