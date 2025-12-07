package com.paul.ecomerce.service.impl;

import static com.paul.ecomerce.service.validator.RoleValildator.ROLE_PREFIX;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paul.ecomerce.dto.PageResponseDto;
import com.paul.ecomerce.dto.role.RoleRequestDto;
import com.paul.ecomerce.dto.role.RoleResponseDto;
import com.paul.ecomerce.mapper.PaginationMapper;
import com.paul.ecomerce.mapper.RoleMapper;
import com.paul.ecomerce.model.entity.Role;
import com.paul.ecomerce.repository.RoleRepository;
import com.paul.ecomerce.service.RoleService;
import com.paul.ecomerce.service.validator.RoleValildator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleValildator roleValildator;
    private final RoleMapper roleMapper;
    private final PaginationMapper paginationMapper;

    @Override
    @Transactional
    public RoleResponseDto createRole(RoleRequestDto roleDto) {
        log.info("Validando datos para crear el rol");
        roleValildator.validateForCreate(roleDto);

        Role role = roleMapper.toRole(roleDto);

        role.setName(ROLE_PREFIX + role.getName());

        roleRepository.save(role);

        return roleMapper.toResponseDto(role);
    }

    @Override
    @Transactional
    public RoleResponseDto updateRole(Long id, RoleRequestDto roleDto) {
        log.info("Validando datos para actualizar el rol con ID: " + id);
        Role role = roleValildator.validateForUpdate(id, roleDto);

        roleMapper.updateFromDto(role, roleDto);

        role.setName(ROLE_PREFIX + role.getName());

        roleRepository.save(role);

        return roleMapper.toResponseDto(role);
    }

    @Override
    public PageResponseDto<RoleResponseDto> getAllRolesPaged(Pageable pageable) {
        log.info("Verificando registros de roles");
        Page<Role> rolePaged = roleRepository.findAll(pageable);
        return paginationMapper.toPageResponseDto(rolePaged, roleMapper::toResponseDto);
    }

    @Transactional
    @Override
    public RoleResponseDto getRoleById(Long id) {
        log.info("Verificando existencia del role con ID: " + id);
        Role role = roleValildator.getRoleByIdOrThrow(id);
        return roleMapper.toResponseDto(role);
    }

    @Override
    @Transactional
    public void deactivateRoleById(Long id) {
        log.info("Verificando existencia del role con ID: " + id);
        Role role = roleValildator.getRoleByIdOrThrow(id);
        role.deactivate();

        roleRepository.save(role);
    }

}
