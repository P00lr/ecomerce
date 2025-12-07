package com.paul.ecomerce.dto.productvariant;

import java.math.BigDecimal;

public record ProductVariantResponseDto(
    Long id,
    String productName,
    String colorName,
    String sizeName,
    BigDecimal price,
    Integer stock,
    String sku,
    boolean enabled
    
) {

}
