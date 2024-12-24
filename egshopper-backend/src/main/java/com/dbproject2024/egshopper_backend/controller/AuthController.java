package com.dbproject2024.egshopper_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.dbproject2024.egshopper_backend.model.User;
import com.dbproject2024.egshopper_backend.repository.UserRepository;
import com.dbproject2024.egshopper_backend.util.JwtUtil;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    /*
     * 1) "Register" endpoint
     * POST /api/auth/register
     * Allows a new user to create an account.
     *
     * Example JSON body:
     * {
     * "username": "john_doe",
     * "password": "securepassword",
     * "email": "john@example.com",
     * "fullName": "John Doe"
     * }
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        // Check if username or email already exists
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username is already taken.");
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email is already in use.");
        }

        // Create new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setRole("USER");

        // Save user
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully.");
    }

    /*
     * 2) "Login" endpoint
     * POST /api/auth/login
     * Authenticates a user and returns a JWT token.
     *
     * Example JSON body:
     * {
     * "username": "john_doe",
     * "password": "securepassword"
     * }
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest request) {
        try {
            // Authenticate the user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            // Fetch user details
            Optional<User> userOpt = userRepository.findByUsername(request.getUsername());
            if (!userOpt.isPresent()) {
                return ResponseEntity.badRequest().body("User not found.");
            }
            User user = userOpt.get();

            // Generate JWT token
            String token = jwtUtil.generateJwtToken(user);

            return ResponseEntity.ok(new JwtResponse(token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid username or password.");
        }
    }

    /*
     * DTO Classes for Request and Response Bodies
     */

    // RegisterRequest DTO
    public static class RegisterRequest {
        private String username;
        private String password;
        private String email;
        private String fullName;

        // Getters and Setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        // In a real application, ensure passwords are hashed before setting
        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }
    }

    // LoginRequest DTO
    public static class LoginRequest {
        private String username;
        private String password;

        // Getters and Setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        // ensure passwords are hashed before setting
        public void setPassword(String password) {
            this.password = password;
        }
    }

    // JwtResponse DTO
    public static class JwtResponse {
        private String token;

        public JwtResponse(String token) {
            this.token = token;
        }

        // Getter
        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}