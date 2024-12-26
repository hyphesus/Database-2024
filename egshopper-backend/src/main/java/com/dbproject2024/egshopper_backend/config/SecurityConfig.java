package com.dbproject2024.egshopper_backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dbproject2024.egshopper_backend.security.JwtAuthenticationFilter;
import com.dbproject2024.egshopper_backend.service.UserDetailsServiceImpl;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    // Define password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Define AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // Define CORS configuration source
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Add allowed origins
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:8081",
                "http://localhost:8080",
                "http://192.168.0.101:8081",
                "http://192.168.0.101:8080"));

        // Allow specific HTTP methods
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Allow specific headers
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

        // Allow credentials (e.g., cookies, authorization headers)
        configuration.setAllowCredentials(true);

        // Set the max age for preflight requests
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // Apply CORS settings to all endpoints
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    // Define SecurityFilterChain with updated methods and CORS configuration
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Enable CORS with the defined configuration
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // Disable CSRF since we're using JWT
                .csrf(csrf -> csrf.disable())
                // Set session management to stateless
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Set authorization rules
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Allow unauthenticated access to auth endpoints
                        .requestMatchers("/api/discounts/create").hasRole("ADMIN") // Only admins can create discounts
                        .anyRequest().authenticated() // All other endpoints require authentication
                )
                // Add JWT filter before the UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}