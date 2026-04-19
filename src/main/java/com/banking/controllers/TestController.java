/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banking.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author joella
 */
@RestController
@RequestMapping("/api/test")
public class TestController {
    
    @GetMapping("/status")
    public Map<String, Object> getStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("status", "OK");
        status.put("message", "Banking API fonctionne !");
        status.put("timestamp", LocalDateTime.now());
        status.put("database", "PostgreSQL connecté");
        return status;
    }
}