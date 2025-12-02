package com.example.security_demo;

import org.springframework.stereotype.Service;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
@Entity
@Data
@Service
public class users {
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    private int id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
}
