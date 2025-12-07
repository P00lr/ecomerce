package com.paul.ecomerce.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paul.ecomerce.dto.PageResponseDto;
import com.paul.ecomerce.dto.permission.PermissionRequestDto;
import com.paul.ecomerce.dto.permission.PermissionResponseDto;
import com.paul.ecomerce.mapper.PaginationMapper;
import com.paul.ecomerce.mapper.PermissionMapper;
import com.paul.ecomerce.model.entity.Permission;
import com.paul.ecomerce.repository.PermissionRepository;
import com.paul.ecomerce.service.PermissionService;
import com.paul.ecomerce.service.validator.PermissionValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService{

    private final PermissionRepository permissionRepository;
    private final PermissionValidator permissionValidator;
    private final PermissionMapper permissionMapper;
    private final PaginationMapper paginationMapper;

    @Override
    @Transactional
    public PermissionResponseDto createPermission(PermissionRequestDto permissionDto) {
        log.info("Validando datos para crear el permiso");
        permissionValidator.validateForCreate(permissionDto);

        Permission permission = permissionMapper.toPermission(permissionDto);
        
        permissionRepository.save(permission);

        return permissionMapper.toResponseDto(permission);
    }

    @Override
    @Transactional
    public PermissionResponseDto updatePermission(Long id, PermissionRequestDto permissionDto) {
        log.info("Validando datos para actualizar el permiso con ID: " + id);
        Permission permission = permissionValidator.validateForUpdate(id, permissionDto);

        permissionMapper.updateFromDto(permission, permissionDto);

        permissionRepository.save(permission);

        return permissionMapper.toResponseDto(permission);
    }

    @Override
    public PageResponseDto<PermissionResponseDto> getAllPermissionsPaged(Pageable pageable) {
        log.info("Verificando registros de permisos");
        Page<Permission> permissionPaged = permissionRepository.findAll(pageable);
        return paginationMapper.toPageResponseDto(permissionPaged, permissionMapper::toResponseDto);
    }

    @Transactional
    @Override
    public PermissionResponseDto getPermissionById(Long id) {
        log.info("Verificando existencia del permiso con ID: " + id);
        Permission permission = permissionValidator.getPermissionOrThrow(id);
        return permissionMapper.toResponseDto(permission);
    }

    @Override
    @Transactional
    public void deactivatePermissionById(Long id) {
        log.info("Verificando existencia del permiso con ID: " + id);
        Permission permission = permissionValidator.getPermissionOrThrow(id);
        permission.deactivate();

        permissionRepository.save(permission);
    }

}
