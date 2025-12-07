package com.paul.ecomerce.dto.productvariant;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductVariantRequestDto(
    @NotNull(message = "El id del color no debe estar vacio")
    Long colorId,

    @NotNull(message = "El id de la talla no debe estar vacio")
    Long sizeId,

    @Positive
    BigDecimal price,

    @Positive
    Integer stock
) {

}
