package com.example.time.dbseeder;

import com.example.time.model.Role;
import com.example.time.model.User;
import com.example.time.repository.UserRepository;
import com.example.time.security.PasswordEncoder;
import com.example.time.service.RoleService;
import com.example.time.service.UserService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DbSeeder {

    private final RoleService roleService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String password = "11111";
    private static final String ROLE_USER = "ROLE_USER";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    public DbSeeder(RoleService roleService, UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void seedUserRoles() {

        Role roleUser = roleService.findByName(ROLE_USER);
        if (roleUser == null) {
            roleUser = new Role();
            roleUser.setName(ROLE_USER);
            roleService.save(roleUser);
        }

        Role roleAdmin = roleService.findByName(ROLE_ADMIN);
        if (roleAdmin == null) {
            roleAdmin = new Role();
            roleAdmin.setName(ROLE_ADMIN);
            roleService.save(roleAdmin);
        }

    }

    public void seedUserWithUserRole() {
        User userOnly = new User();
        userOnly.setPassword(password);
        userOnly.setUsername("userOnly");
        userOnly.setEmail("userOnly@gmail.com");
        try {
            userService.register(userOnly);
        } catch (Exception e) {
            //logger
        }
    }

    public void seedUserWithAdminRole() {
        Role roleAdmin = roleService.findByName(ROLE_ADMIN);
        List<Role> roles = new ArrayList<>();
        roles.add(roleAdmin);

        User adminOnly = new User();
        adminOnly.setRoles(roles);
        adminOnly.setPassword(passwordEncoder.encode(password));
        adminOnly.setUsername("adminOnly");
        adminOnly.setEmail("adminOnly@gmail.com");
        adminOnly.setRate(0);
        try {
            userRepository.save(adminOnly);
        } catch (Exception e) {
        //logger
        }
    }

    public void seedUserWithAdminAndUserRoles() {
        Role roleAdmin = roleService.findByName(ROLE_ADMIN);
        List<Role> roles = new ArrayList<>();
        roles.add(roleAdmin);

        User adminAndUser = new User();
        adminAndUser.setRoles(roles);
        adminAndUser.setPassword(password);
        adminAndUser.setUsername("adminAndUser");
        adminAndUser.setEmail("adminAndUser@gmail.com");
        try {
            userService.register(adminAndUser);
        } catch (Exception e) {
        //logger
        }

    }
}