package com.paul.ecomerce.service;

import org.springframework.data.domain.Pageable;

import com.paul.ecomerce.dto.PageResponseDto;
import com.paul.ecomerce.dto.permission.PermissionRequestDto;
import com.paul.ecomerce.dto.permission.PermissionResponseDto;

public interface PermissionService {
    PageResponseDto<PermissionResponseDto> getAllPermissionsPaged(Pageable pageable);
    PermissionResponseDto getPermissionById(Long id);
    PermissionResponseDto createPermission(PermissionRequestDto permissionDto);
    PermissionResponseDto updatePermission(Long id, PermissionRequestDto permissionDto);
    void deactivatePermissionById(Long id);

}
