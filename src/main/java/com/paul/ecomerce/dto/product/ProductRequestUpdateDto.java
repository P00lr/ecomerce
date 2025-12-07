package com.paul.ecomerce.dto.product;

import java.math.BigDecimal;

public record ProductRequestUpdateDto(
    String name,
    BigDecimal basePrice,
    String description
) {

}
