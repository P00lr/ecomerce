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
import com.paul.ecomerce.dto.permission.PermissionRequestDto;
import com.paul.ecomerce.dto.permission.PermissionResponseDto;
import com.paul.ecomerce.service.PermissionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    @GetMapping
    public ResponseEntity<PageResponseDto<PermissionResponseDto>> getAllPermissionsPaged(Pageable pageable) {
        return ResponseEntity.ok(permissionService.getAllPermissionsPaged(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermissionResponseDto> getPermissionById(@PathVariable Long id) {
        return ResponseEntity.ok(permissionService.getPermissionById(id));
    }

    @PostMapping
    public ResponseEntity<PermissionResponseDto> createPermission(@Valid @RequestBody PermissionRequestDto permissionDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(permissionService.createPermission(permissionDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PermissionResponseDto> updatePermission(@PathVariable Long id,
            @Valid @RequestBody PermissionRequestDto permissionDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(permissionService.updatePermission(id, permissionDto));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePermissionById(@PathVariable Long id) {
        permissionService.deactivatePermissionById(id);
        return ResponseEntity.ok("Se desactivo correctamente el permiso con ID: " + id);
    }
}
