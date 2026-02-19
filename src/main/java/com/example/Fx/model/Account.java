package com.example.Fx.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "account")
public class Account {

    @Id
    @SequenceGenerator(
            name = "account_sequence", // Generator name used in your code
            sequenceName = "account_seq",// Actual DB sequence name
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "account_sequence"
    )
    private Long id;
    private String username;

    @Column(
            unique = true,
            nullable = false
    )
    private String email;
    private String passwordHash;
    private String fullName;
    private String phoneNumber;
    private boolean enabled;
    private boolean emailVerified;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
}
