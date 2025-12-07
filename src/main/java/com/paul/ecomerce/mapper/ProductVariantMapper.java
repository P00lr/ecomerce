package com.paul.ecomerce.mapper;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.paul.ecomerce.dto.product.ProductVariantDto;
import com.paul.ecomerce.dto.productvariant.ProductVariantResponseDto;
import com.paul.ecomerce.model.entity.ProductVariant;

@Component  
public class ProductVariantMapper {

    public ProductVariantResponseDto toProductVariantResponseDto(ProductVariant productVariant) {
        return new ProductVariantResponseDto(
            productVariant.getId(), 
            productVariant.getProduct().getName(), 
            productVariant.getColor().getName(), 
            productVariant.getSize().getName(), 
            productVariant.getPrice(), 
            productVariant.getStock(), 
            productVariant.getSku(),
            productVariant.isEnabled()
            );
    }

    public ProductVariantDto toProductVariantDto(ProductVariant productVariant) {
        return new ProductVariantDto(
            productVariant.getId(),
            productVariant.getSku(),
            productVariant.getPrice(),
            productVariant.getStock(),
            productVariant.getColor().getName(),
            productVariant.getSize().getName(),
            productVariant.isEnabled()
        );
    }

    public List<ProductVariantDto> toProductVariantDtos(Set<ProductVariant> productVariants) {
        return productVariants.stream()
            .map(this::toProductVariantDto)
            .toList();
    }
}
