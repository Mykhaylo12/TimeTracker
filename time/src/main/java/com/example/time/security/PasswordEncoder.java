package com.example.time.security;

import com.google.common.hash.Hashing;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;

@Configuration
public class PasswordEncoder {
    public String encode(String rawPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException();
        }
        return Hashing.sha256()
                .hashString(rawPassword, StandardCharsets.UTF_8)
                .toString();

    }
}