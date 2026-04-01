package com.example.hotelreservationsystemforspecialevents.Service;

import com.example.hotelreservationsystemforspecialevents.User;
import com.example.hotelreservationsystemforspecialevents.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class DbUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("=== LOGIN ATTEMPT ===");
        System.out.println("Username entered: " + username);

        User u = userRepo.findByUsername(username)
                .orElseThrow(() -> {
                    System.out.println("User NOT found in database: " + username);
                    return new UsernameNotFoundException("User not found: " + username);
                });

        System.out.println("User found: " + u.getUsername());
        System.out.println("Password in DB: " + u.getPassword());
        System.out.println("Role: " + u.getRole());

        String role = u.getRole().trim();

        return org.springframework.security.core.userdetails.User
                .withUsername(u.getUsername())
                .password(u.getPassword())
                .authorities(role)
                .build();
    }
}