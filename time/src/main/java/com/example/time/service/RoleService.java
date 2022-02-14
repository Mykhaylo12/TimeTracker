package com.example.time.service;

import com.example.time.model.Role;

public interface RoleService {
    Role findByName(String role_user);
    Role save(Role role);
}