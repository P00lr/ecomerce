package com.paul.ecomerce.dto.adjustmentdetail;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AdjustmentDetailDto(
    @NotNull(message = "El campo productVariantId no debe estar vacio")
    Long productVariantId,

    @NotNull(message = "La cantidad no debe estar vacio")
    @Positive
    Integer quantity
) {
    
}
