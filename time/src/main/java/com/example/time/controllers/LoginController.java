package com.example.time.controllers;

import com.example.time.dto.UserDto;
import com.example.time.model.Role;
import com.example.time.model.User;
import com.example.time.security.JwtTokenProvider;
import com.example.time.security.PasswordEncoder;
import com.example.time.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/login")
public class LoginController {
    private final PasswordEncoder encoder;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;


    public LoginController(PasswordEncoder encoder, UserService userService,
                           JwtTokenProvider jwtTokenProvider) {
        this.encoder = encoder;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping
    public ResponseEntity login(@RequestBody UserDto userDto, BindingResult bindingResult) throws ValidationException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException("user is not valid");
        }
        User user = userService.findByEmail(userDto.getEmail());
        if (user == null || userDto.getPassword() == null || !user.getPassword().equals(encoder.encode(userDto.getPassword()))) {
            return new ResponseEntity("Invalid email or password", HttpStatus.BAD_REQUEST);
        }
        String token = jwtTokenProvider.createToken(user.getEmail(), user.getRoles());
        return getResponseEntity(token, user);
    }

    private ResponseEntity getResponseEntity(String token, User user) {
        List<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        Map<String, Object> response = new HashMap<>();
        response.put("email", user.getEmail());
        response.put("token", token);
        response.put("id", String.valueOf(user.getId()));
        response.put("username", user.getUsername());
        response.put("roles", roles);
        return ResponseEntity.ok(response);
    }
}
