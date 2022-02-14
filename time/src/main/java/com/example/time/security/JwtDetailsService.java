package com.example.time.security;

import com.example.time.model.User;
import com.example.time.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtDetailsService implements UserDetailsService {
    private final UserService userService;

    public JwtDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User with email " + email + " not  found");
        }
        return JwtUserFactory.create(user);
    }
}