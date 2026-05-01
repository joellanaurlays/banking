/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banking.dtos;

import lombok.Data;

/**
 *
 * @author joella
 */
@Data
public class UserUpdateDTO {
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
}

