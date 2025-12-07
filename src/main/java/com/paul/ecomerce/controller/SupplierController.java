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
import com.paul.ecomerce.dto.supplier.SupplierRequestDto;
import com.paul.ecomerce.dto.supplier.SupplierRequestUpdateDto;
import com.paul.ecomerce.dto.supplier.SupplierResponseDto;
import com.paul.ecomerce.service.SupplierService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping
    public ResponseEntity<PageResponseDto<SupplierResponseDto>> getAllSuppliersPaged(Pageable pageable) {
        return ResponseEntity.ok(supplierService.getAllSuppliersPage(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponseDto> getSupplierById(@PathVariable Long id) {
        return ResponseEntity.ok(supplierService.getSupplierById(id));
    }

    @PostMapping
    public ResponseEntity<SupplierResponseDto> createSupplier(@Valid @RequestBody SupplierRequestDto supplierDto) {
        return ResponseEntity.ok(supplierService.createSupplier(supplierDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierResponseDto> updateSupplier(
        @PathVariable Long id, 
        @Valid @RequestBody SupplierRequestUpdateDto supplierDto) {
            return ResponseEntity.ok(supplierService.updateSupplier(id, supplierDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deactivateSupplierById(@PathVariable Long id) {
        supplierService.deactivateSupplierById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Se desactivo correctamente el proveedor con ID: " + id);
    }
}
