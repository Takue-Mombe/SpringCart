package com.continuo.ecommerce.Services;

import com.continuo.ecommerce.models.User;
import com.continuo.ecommerce.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Attempting to load user with email: " + email);
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isPresent()) {
            System.out.println("No user found with email: " + email);
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        UserDetails user = userOptional.get();
        System.out.println("User found: " + user.getUsername() + " with role: " + ((User)user).getRole());
        return user;
    }
}
