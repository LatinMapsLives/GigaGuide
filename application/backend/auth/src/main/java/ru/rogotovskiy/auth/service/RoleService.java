package ru.rogotovskiy.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rogotovskiy.auth.entity.Role;
import ru.rogotovskiy.auth.repositoy.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role findByName(String name) {
        return roleRepository.findByName(name).orElseThrow();
    }
}
