package com.paul.ecomerce.service;

import org.springframework.data.domain.Pageable;

import com.paul.ecomerce.dto.PageResponseDto;
import com.paul.ecomerce.dto.product.ProductRequestDto;
import com.paul.ecomerce.dto.product.ProductResponseDto;
import com.paul.ecomerce.dto.product.ProductDetailResponseDto;

public interface ProductService {
    PageResponseDto<ProductResponseDto> getAllProductsPaged(Pageable pageable);
    ProductDetailResponseDto getProductById(Long id);
    ProductResponseDto createProduct(ProductRequestDto productDto);
    ProductResponseDto updateProduct(Long productId, ProductRequestDto productUpdateDto);
    void deactivateProduct(Long id);
}
