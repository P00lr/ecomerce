package com.paul.ecomerce.mapper;

import org.springframework.stereotype.Component;

import com.paul.ecomerce.dto.permission.PermissionRequestDto;
import com.paul.ecomerce.dto.permission.PermissionResponseDto;
import com.paul.ecomerce.model.entity.Permission;

@Component
public class PermissionMapper {

    public Permission toPermission(PermissionRequestDto permissionDto) {
        Permission permission = new Permission();
        permission.setName(permissionDto.name());

        return permission;
    }

    public PermissionResponseDto toResponseDto(Permission permission) {
        return new PermissionResponseDto(
            permission.getId(),
            permission.getName(),
            permission.isEnabled()
        );
    }

    public void updateFromDto(Permission permission, PermissionRequestDto roleDto) {
        permission.setName(roleDto.name().toUpperCase());
    }

}
