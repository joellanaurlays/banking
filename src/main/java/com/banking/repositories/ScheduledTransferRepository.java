/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banking.repositories;

import com.banking.entities.ScheduledTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author joella
 */
@Repository
public interface ScheduledTransferRepository extends JpaRepository<ScheduledTransfer, String> {
    List<ScheduledTransfer> findByScheduledDateBeforeAndActiveTrue(LocalDateTime date);
}

