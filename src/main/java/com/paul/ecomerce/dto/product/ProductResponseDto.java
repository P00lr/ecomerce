package com.paul.ecomerce.dto.product;

import java.math.BigDecimal;

public record ProductResponseDto(
    Long id,
    String name,
    BigDecimal basePrice,
    String description,
    String categoryName,
    String brandName,
    boolean enableld
) {

}
