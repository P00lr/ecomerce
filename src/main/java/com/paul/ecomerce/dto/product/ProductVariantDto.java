package com.paul.ecomerce.dto.product;

import java.math.BigDecimal;

public record ProductVariantDto(
    Long id,
    String sku,
    BigDecimal price,
    Integer stock,
    String colorName,
    String sizeName,
    boolean enabled
) {

}
