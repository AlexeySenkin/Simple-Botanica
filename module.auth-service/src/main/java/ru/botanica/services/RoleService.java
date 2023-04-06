package ru.botanica.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.botanica.entities.Role;
import ru.botanica.repositories.RoleRepository;


@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole(String name) {
        return roleRepository.findByName(name).get();
    }
}