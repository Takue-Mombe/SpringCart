package com.continuo.ecommerce.Controller;

import com.continuo.ecommerce.DTO.LoginRequest;
import com.continuo.ecommerce.DTO.RegisterRequest;
import com.continuo.ecommerce.Enums.Role;
import com.continuo.ecommerce.Response.AuthResponse;
import com.continuo.ecommerce.Services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.webauthn.api.AuthenticatorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return authService.registerUser(
                request.getEmail(),
                request.getUsername(),
                request.getPassword(),
                request.getFirstName(),
                request.getLastName(),
                request.getRole()
        );
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.login(loginRequest));
    }
}
