package com.continuo.ecommerce.Security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
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
    throws ServletException, IOException{
        String authHeader = httpServletRequest.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            chain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        String token=authHeader.substring(7);
        String email=jwtUtil.extractEmail(token);

        if (email!=null && SecurityContextHolder.getContext().getAuthentication()==null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            if (jwtUtil.validateToken(token)){
                UsernamePasswordAuthenticationToken authenticationToken=
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
chain.doFilter(httpServletRequest, httpServletResponse);
    }

}
