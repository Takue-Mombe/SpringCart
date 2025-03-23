package com.continuo.ecommerce.Response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AuthResponse {
    private String token;
    private String role;

    public AuthResponse() {
    }

    public AuthResponse(String token, String role) {
        this.token = token;
        this.role = role;
    }
}