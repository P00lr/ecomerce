package com.paul.ecomerce.mapper;

import org.springframework.stereotype.Component;

import com.paul.ecomerce.dto.product.ProductDetailResponseDto;
import com.paul.ecomerce.dto.product.ProductResponseDto;
import com.paul.ecomerce.dto.product.ProductVariantDto;
import com.paul.ecomerce.model.entity.Product;
import com.paul.ecomerce.model.entity.ProductVariant;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductMappper {

    private final ProductVariantMapper productVariantMapper;

    public ProductDetailResponseDto toProductDetailResponseDto(Product product) {
        return new ProductDetailResponseDto(
                product.getId(),
                product.getName(),
                product.getBasePrice(),
                product.getDescription(),
                product.getProductCode(),
                product.isEnabled(),
                productVariantMapper.toProductVariantDtos(product.getProductVariants()));
    }

    public ProductVariantDto toProductVariantDto(ProductVariant productVariant) {
        return new ProductVariantDto(
                productVariant.getId(),
                productVariant.getSku(),
                productVariant.getPrice(),
                productVariant.getStock(),
                productVariant.getColor().getName(),
                productVariant.getSize().getName(),
                productVariant.isEnabled());
    }

    public ProductResponseDto toProductResponseDto(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getBasePrice(),
                product.getDescription(),
                product.getCategory().getName(),
                product.getBrand().getName(),
                product.isEnabled());
    }
}
