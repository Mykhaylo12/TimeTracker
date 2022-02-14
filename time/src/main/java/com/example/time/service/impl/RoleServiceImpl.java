package com.example.time.service.impl;

import com.example.time.model.Role;
import com.example.time.repository.RoleRepository;
import com.example.time.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByName(String role_user) {
        return roleRepository.findByName(role_user);
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }
}