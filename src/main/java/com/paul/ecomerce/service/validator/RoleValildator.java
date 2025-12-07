package com.paul.ecomerce.service.validator;

import org.springframework.stereotype.Component;

import com.paul.ecomerce.dto.role.RoleRequestDto;
import com.paul.ecomerce.exception.custom.ResourceAlreadyExistsException;
import com.paul.ecomerce.exception.custom.ResourceNotFoundException;
import com.paul.ecomerce.model.entity.Role;
import com.paul.ecomerce.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class RoleValildator {

    private final RoleRepository roleRepository;

    public static final String ROLE_PREFIX = "ROLE_"; 

    public Role getRoleByIdOrThrow(Long id) {
        return roleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No se encontro el rol con ID: " + id));
    }

    public Role getRoleByNameOrThrow(String name) {
        return roleRepository.findByName(name)
            .orElseThrow(() -> new ResourceAlreadyExistsException("No se encontro el rol: " + name));
    }

    public void validateForCreate(RoleRequestDto roleDto) {
        String normalizedName = normalize(roleDto.name());
        validateName(normalizedName);
    }

    public Role validateForUpdate(Long id, RoleRequestDto roleDto) {
        Role role = getRoleByIdOrThrow(id);
        String normalizedNewName = normalize(roleDto.name());
        validateNameForUpdate(role.getName(), normalizedNewName);

        return role;
    }

    public void validateName(String name){
        if(roleRepository.existsByName(name))
            throw new ResourceAlreadyExistsException("El rol: " + name + " ya esta registrado");
    }

    public void validateNameForUpdate(String currentName, String newName) {

        if(!currentName.equalsIgnoreCase(newName) &&
            roleRepository.existsByName(newName))
            throw new ResourceAlreadyExistsException("El rol: " + newName + " ya esta registrado");

    }

    private String normalize(String name) {
        return ROLE_PREFIX + name.trim().toUpperCase();
    }
}
