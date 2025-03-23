package com.continuo.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecSecurityConfig {

//    The Authentication Provider is backed by a simple,
//    in-memory implementation, InMemoryUserDetailsManager.
//    This is useful for rapid prototyping when a full
//    persistence mechanism is not yet necessary

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {

        UserDetails user1= User.withUsername("user1")
                .password("user1pass")
                .roles("USER")
                .build();
        UserDetails user2 =User.withUsername("user2")
                .password("user2pass")
                .roles("USER")
                .build();
        UserDetails admin =User.withUsername("admin")
                .password("adminPass")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(request -> request.
                        requestMatchers("/public/**").permitAll()
                        .anyRequest().authenticated()
                ).formLogin((form)->form
                        .loginPage("/login")
                        .permitAll()
                ).authorizeHttpRequests(request->request
                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")
                        .anyRequest()
                        .authenticated()
                ).authorizeHttpRequests(request->request
                        .requestMatchers("/user/**")
                        .hasRole("USER")
                        .anyRequest()
                        .authenticated()
                )
                .logout(LogoutConfigurer::permitAll
                );

        return http.build();
    }
}
