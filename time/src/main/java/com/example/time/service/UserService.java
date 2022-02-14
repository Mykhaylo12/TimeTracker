package com.example.time.service;

import com.example.time.dto.UserDto;
import com.example.time.model.User;

import java.util.List;

public interface UserService {
    User register(User user);

    User findByEmail(String email);

    void deleteById(Long id);

    UserDto getUserById(Long userId);

    List<UserDto> getAllUsers();

    void editUser(User user);
}