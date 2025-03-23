package com.continuo.ecommerce.Response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private String role;
}
