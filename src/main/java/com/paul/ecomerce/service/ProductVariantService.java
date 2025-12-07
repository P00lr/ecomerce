package com.paul.ecomerce.service;

import org.springframework.data.domain.Pageable;

import com.paul.ecomerce.dto.PageResponseDto;
import com.paul.ecomerce.dto.productvariant.ProductVariantRequestDto;
import com.paul.ecomerce.dto.productvariant.ProductVariantRequestUpdateDto;
import com.paul.ecomerce.dto.productvariant.ProductVariantResponseDto;
import com.paul.ecomerce.model.entity.ProductVariant;

public interface ProductVariantService {

    PageResponseDto<ProductVariantResponseDto> getAllProductVariantPaged(Pageable pageable);

    ProductVariantResponseDto createProductVariant(Long id, ProductVariantRequestDto productVariantDto);

    public ProductVariantResponseDto updateProductVariant(
        Long productVariantId, 
        ProductVariantRequestUpdateDto productDto);

    void deactivateProductVariant(Long id);
    
    void decreaseStock(ProductVariant productVariant, Integer quantity);
    
    void increaseStock(ProductVariant productVariant, Integer quantity);
}
