package com.paul.ecomerce.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paul.ecomerce.dto.PageResponseDto;
import com.paul.ecomerce.dto.productvariant.ProductVariantRequestDto;
import com.paul.ecomerce.dto.productvariant.ProductVariantRequestUpdateDto;
import com.paul.ecomerce.dto.productvariant.ProductVariantResponseDto;
import com.paul.ecomerce.service.ProductVariantService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductVariantController {

    private final ProductVariantService productVariantService;

    @GetMapping("/variants")
    public ResponseEntity<PageResponseDto<ProductVariantResponseDto>> getAllProductVariantDtos(Pageable pageable) {
        return ResponseEntity.ok(productVariantService.getAllProductVariantPaged(pageable));
    }

    @PostMapping("/{id}/variants")
    public ResponseEntity<ProductVariantResponseDto> createProductVariant(
            @PathVariable Long id,
            @Valid @RequestBody ProductVariantRequestDto productVariantRequestDto) {
        return ResponseEntity.ok(productVariantService.createProductVariant(id, productVariantRequestDto));
    }

    @PutMapping("/{id}/variants")
    public ResponseEntity<ProductVariantResponseDto> updateProductVariant(
            @PathVariable Long id,
            @Valid @RequestBody ProductVariantRequestUpdateDto productDto) {
                return ResponseEntity.ok(productVariantService.updateProductVariant(id, productDto));
    }

}
