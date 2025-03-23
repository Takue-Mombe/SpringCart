package com.continuo.ecommerce.Services;


import com.continuo.ecommerce.DTO.LoginRequest;
import com.continuo.ecommerce.Enums.Role;
import com.continuo.ecommerce.Repository.UserRepository;
import com.continuo.ecommerce.Response.AuthResponse;
import com.continuo.ecommerce.Security.JWTUtil;
import com.continuo.ecommerce.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;


    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String registerUser(String email, String username,
                               String password, String firstName,
                               String lastName, Role role) {

        if (userRepository.existsByEmail(email)){
            throw  new RuntimeException("Email Already Exists");
        }

        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .role(role)
                .verified(false)
                .build();



        userRepository.save(user);

        return jwtUtil.generateToken(email);

    }

    public AuthResponse login(LoginRequest loginRequest) {
        try {
            System.out.println("Login attempt for email: " + loginRequest.getEmail());
            
            // Check if user exists before authentication
            if (!userRepository.existsByEmail(loginRequest.getEmail())) {
                System.out.println("User does not exist in database");
                throw new UsernameNotFoundException("User not found");
            }
            
            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("User Not Found"));
            System.out.println("Found user in database with role: " + user.getRole());
            
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            System.out.println("Authentication successful");

            String token = jwtUtil.generateToken(user.getEmail());
            return new AuthResponse(token, user.getRole().name());

        } catch (BadCredentialsException e) {
            System.out.println("Authentication failed: Bad credentials");
            throw new BadCredentialsException("Bad Credentials");
        } catch (Exception e) {
            System.out.println("Authentication failed with exception: " + e.getMessage());
            throw e;
        }
    }


}
