package com.dbproject2024.egshopper_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dbproject2024.egshopper_backend.model.User;
import com.dbproject2024.egshopper_backend.payload.RegisterRequest;
import com.dbproject2024.egshopper_backend.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(RegisterRequest registerRequest) throws Exception {
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new Exception("Username already exists.");
        }

        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new Exception("Email already exists.");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setFullName(registerRequest.getFullName());
        user.setRole("USER"); // Default role

        userRepository.save(user);
    }
}