package com.continuo.ecommerce.Controller;

import com.continuo.ecommerce.DTO.RegisterRequest;
import com.continuo.ecommerce.Enums.Role;
import com.continuo.ecommerce.Services.AuthService;
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
                request.getPassword(),
                request.getUsername(),
                request.getFirstName(),
                request.getLastName(),
                request.getRole()
        );
    }
}
