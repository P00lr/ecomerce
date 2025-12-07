package com.paul.ecomerce.service;

import org.springframework.data.domain.Pageable;

import com.paul.ecomerce.dto.PageResponseDto;
import com.paul.ecomerce.dto.role.RoleRequestDto;
import com.paul.ecomerce.dto.role.RoleResponseDto;

public interface RoleService {
    PageResponseDto<RoleResponseDto> getAllRolesPaged(Pageable pageable);
    RoleResponseDto getRoleById(Long id);
    RoleResponseDto createRole(RoleRequestDto roleDto);
    RoleResponseDto updateRole(Long id, RoleRequestDto roleDto);
    void deactivateRoleById(Long id);

}
