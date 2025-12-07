package com.paul.ecomerce.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paul.ecomerce.dto.PageResponseDto;
import com.paul.ecomerce.dto.role.RoleRequestDto;
import com.paul.ecomerce.dto.role.RoleResponseDto;
import com.paul.ecomerce.service.RoleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/roles")
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<PageResponseDto<RoleResponseDto>> getAllRolesPaged(Pageable pageable) {
        return ResponseEntity.ok(roleService.getAllRolesPaged(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDto> getRoleById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @PostMapping
    public ResponseEntity<RoleResponseDto> createRole(@Valid @RequestBody RoleRequestDto roleDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.createRole(roleDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponseDto> updateRole(@PathVariable Long id,
            @Valid @RequestBody RoleRequestDto roleDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.updateRole(id, roleDto));

    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteRoleById(@PathVariable Long id) {
        roleService.deactivateRoleById(id);
        return ResponseEntity.ok("Se desactivo correctamente el rol con ID: " + id);
    }
}
