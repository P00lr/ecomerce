package com.paul.ecomerce.service.validator;

import org.springframework.stereotype.Component;

import com.paul.ecomerce.exception.custom.ResourceNotFoundException;
import com.paul.ecomerce.model.entity.ProductVariant;
import com.paul.ecomerce.repository.ProductVariantRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductVariantValidator {

    private final ProductVariantRepository productVariantRepository;

    public ProductVariant geProductVariantOrThrow(Long id) {
        return productVariantRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No se encontro a la variante del producto con ID: " + id));
    }

    public boolean skuExists(String sku) {
        return productVariantRepository.existsBySku(sku);
    }
}
