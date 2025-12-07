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
import com.paul.ecomerce.dto.brand.BrandRequestDto;
import com.paul.ecomerce.dto.brand.BrandRequestUpdateDto;
import com.paul.ecomerce.dto.brand.BrandResponseDto;
import com.paul.ecomerce.service.BrandService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/brands")
public class BrandController {

    private final BrandService brandService;

    @GetMapping
    public ResponseEntity<PageResponseDto<BrandResponseDto>> getAllBrandsPaged(Pageable pageable) {
        return ResponseEntity.ok(brandService.getAllBrandsPage(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandResponseDto> getBrandById(@PathVariable Long id) {
        return ResponseEntity.ok(brandService.getBrandById(id));
    }

    @PostMapping
    public ResponseEntity<BrandResponseDto> createBrand(@Valid @RequestBody BrandRequestDto brandDto) {
        return ResponseEntity.ok(brandService.createBrand(brandDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BrandResponseDto> updateBrand(
        @PathVariable Long id, 
        @Valid @RequestBody BrandRequestUpdateDto brandDto) {
            return ResponseEntity.ok(brandService.updateBrand(id, brandDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deactivateBrandById(@PathVariable Long id) {
        brandService.deactivateBrandById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Se desactivo correctamente la marca con ID: " + id);
    }
}
