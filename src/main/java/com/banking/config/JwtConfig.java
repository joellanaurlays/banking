/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banking.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author joella
 */
@Configuration
@ConfigurationProperties(prefix = "app.jwt")
public class JwtConfig {
    
    private String secret;
    private long expiration;
    
    public String getSecret() {
        return secret;
    }
    
    public void setSecret(String secret) {
        this.secret = secret;
    }
    
    public long getExpiration() {
        return expiration;
    }
    
    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }
}
    

