package com.example.time.controllers;

import com.example.time.dto.UserDto;
import com.example.time.model.User;
import com.example.time.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;

@RestController
@RequestMapping("/registration")
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> registration(@RequestBody UserDto registrationViewModel,
                                                BindingResult bindingResult) throws ValidationException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException("binding result has an error");
        }
        User user;
        UserDto userDto;
        try {
            user = UserDto.dtoToUser(registrationViewModel);
            User registerUser = userService.register(user);
            userDto = UserDto.userToDto(registerUser);
        } catch (Exception e) {
            return new ResponseEntity(e.toString(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(userDto);
    }
}