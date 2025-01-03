package com.dbproject2024.egshopper_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is required.")
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank(message = "Password is required.")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Email is required.")
    @Email(message = "Email should be valid.")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Full name is required.")
    @Column(nullable = false)
    private String fullName;

    @NotBlank(message = "Role is required.")
    @Column(nullable = false)
    private String role;
}