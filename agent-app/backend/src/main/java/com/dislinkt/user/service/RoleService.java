package com.dislinkt.user.service;

import com.dislinkt.user.dao.RoleRepository;
import com.dislinkt.user.model.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role getRole(String name) {
        return roleRepository.findRoleByName(name);
    }
}
