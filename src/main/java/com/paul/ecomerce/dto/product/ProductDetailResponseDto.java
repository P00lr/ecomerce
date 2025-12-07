package com.paul.ecomerce.dto.product;

import java.math.BigDecimal;
import java.util.List;

    public record ProductDetailResponseDto(
        Long id,
        String name,
        BigDecimal basePrice,
        String description,
        String productCode,
        boolean enabled,
        List<ProductVariantDto> productVariants
    ) {

    }
