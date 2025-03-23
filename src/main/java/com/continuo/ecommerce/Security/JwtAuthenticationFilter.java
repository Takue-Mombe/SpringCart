package com.continuo.ecommerce.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JWTUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain chain)
            throws ServletException, IOException {
        // Get authorization header
        String authHeader = httpServletRequest.getHeader("Authorization");

        // If no Authorization header or it doesn't start with "Bearer ", skip token processing
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        // Extract token (remove "Bearer " prefix)
        String token = authHeader.substring(7);

        try {
            // Extract email from token
            String email = jwtUtil.extractEmail(token);

            // If we have a valid email and no authentication is set yet
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Load user details
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                // Validate token
                if (jwtUtil.validateToken(token)) {
                    // Create authentication token
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    // Set authentication in security context
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        } catch (Exception e) {

             logger.error("JWT Authentication failed", e);
        }

        // Continue filter chain
        chain.doFilter(httpServletRequest, httpServletResponse);
    }
}