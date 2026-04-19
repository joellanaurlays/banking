/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banking.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author joella
 */
@Entity
@DiscriminatorValue("CURRENT")
@Getter
@Setter
@NoArgsConstructor
public class CurrentAccount extends Account {
    
    @Column(nullable = false)
    private BigDecimal overdraftLimit = BigDecimal.ZERO;
}