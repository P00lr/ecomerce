package com.paul.ecomerce.mapper;

import org.springframework.stereotype.Component;

import com.paul.ecomerce.dto.role.RoleRequestDto;
import com.paul.ecomerce.dto.role.RoleResponseDto;
import com.paul.ecomerce.model.entity.Role;

@Component
public class RoleMapper {

    public Role toRole(RoleRequestDto roleDto) {
        Role role = new Role();
        role.setName(roleDto.name().trim().toUpperCase());

        return role;
    }

    public RoleResponseDto toResponseDto(Role role) {
        return new RoleResponseDto(
            role.getId(),
            role.getName(),
            role.isEnabled()
        );
    }

    public void updateFromDto(Role role, RoleRequestDto roleDto) {
        role.setName(roleDto.name().trim().toUpperCase());
    }
}
