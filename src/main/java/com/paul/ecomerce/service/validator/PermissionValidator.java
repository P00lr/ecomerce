package com.paul.ecomerce.service.validator;

import org.springframework.stereotype.Component;

import com.paul.ecomerce.dto.permission.PermissionRequestDto;
import com.paul.ecomerce.exception.custom.ResourceAlreadyExistsException;
import com.paul.ecomerce.exception.custom.ResourceNotFoundException;
import com.paul.ecomerce.model.entity.Permission;
import com.paul.ecomerce.repository.PermissionRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class PermissionValidator {

    private final PermissionRepository permissionRepository;

    public Permission getPermissionOrThrow(Long id) {
        return permissionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No se encontro el permiso con ID: " + id));
    }

    public void validateForCreate(PermissionRequestDto permissionDto) {
        validateName(permissionDto.name());
    }

    public Permission validateForUpdate(Long id, PermissionRequestDto permissionDto) {
        Permission permission = getPermissionOrThrow(id);
        validateNameForUpdate(permission.getName(), permissionDto.name());

        return permission;
    }

    public void validateName(String name){
        if(permissionRepository.existsByName(name))
            throw new ResourceAlreadyExistsException("El permiso: " + name + " ya esta registrado");
    }

    public void validateNameForUpdate(String currentName, String newName) {
        String normalizedNewName = newName.toUpperCase();

        if(!currentName.equalsIgnoreCase(normalizedNewName) &&
            permissionRepository.existsByName(normalizedNewName))
            throw new ResourceAlreadyExistsException("El permiso: " + normalizedNewName + " ya esta registrado");

    }

}
