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
import com.paul.ecomerce.dto.product.ProductRequestDto;
import com.paul.ecomerce.dto.product.ProductResponseDto;
import com.paul.ecomerce.dto.product.ProductDetailResponseDto;
import com.paul.ecomerce.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<PageResponseDto<ProductResponseDto>> getAllProductsPaged(Pageable pageable) {
        return ResponseEntity.ok(productService.getAllProductsPaged(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailResponseDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductRequestDto productDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productDto));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> updateProduct(
        @PathVariable Long productId, 
        @Valid @RequestBody ProductRequestDto productDto) {
            return ResponseEntity.ok(productService.updateProduct(productId, productDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deactivateProduct(@PathVariable Long id) {
        productService.deactivateProduct(id);
        return ResponseEntity.ok("Se desactivo correctamente el producto con ID: " + id + " y todas sus variante");
        
    } 
}
