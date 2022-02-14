package com.example.time.service.impl;

import com.example.time.dto.UserDto;
import com.example.time.model.Role;
import com.example.time.model.User;
import com.example.time.repository.UserRepository;
import com.example.time.security.PasswordEncoder;
import com.example.time.service.RoleService;
import com.example.time.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.encoder = encoder;
    }

    @Override
    public User register(User user) {
        Role role = roleService.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRate(0);
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.getById(userId);
        return UserDto.userToDto(user);
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserDto::userToDto).collect(Collectors.toList());
    }

    @Override
    public void editUser(User user) {
        User userById = userRepository.findById(user.getId()).orElseThrow(NullPointerException::new);

        if (user.getRate() == null || user.getRate() > -1) {
            userById.setRate(user.getRate());
        }
        if (user.getUsername() != null || !user.getUsername().isEmpty()) {
            userById.setUsername((user.getUsername()));
        }
        if (user.getEmail() != null || user.getEmail().contains("@")) {
            userById.setEmail((user.getEmail()));
        }
        userRepository.save(userById);
    }

}
