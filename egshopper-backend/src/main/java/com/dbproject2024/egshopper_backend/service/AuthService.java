package com.dbproject2024.egshopper_backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dbproject2024.egshopper_backend.model.User;
import com.dbproject2024.egshopper_backend.repository.UserRepository;
import com.dbproject2024.egshopper_backend.util.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    /*
     * Register a new user
     */
    public String registerUser(User user) throws IllegalArgumentException {
        // Check if username or email already exists
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Username is already taken.");
        }

        existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Email is already in use.");
        }

        // Encode the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save the user
        userRepository.save(user);

        return "User registered successfully.";
    }

    /*
     * Authenticate user and generate JWT
     */
    public String authenticateUser(String username, String password) throws IllegalArgumentException {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            // SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("User not found."));
            return jwtUtil.generateJwtToken(user);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid username or password.");
        }
    }
}