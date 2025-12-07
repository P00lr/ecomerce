package com.paul.ecomerce.dto.saledetail;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SaleDetailRequestDto(
    @Positive(message = "La cantidad debe ser mayor a cero")
    Integer quantity,

    @NotNull(message = "El campo de la variante del producto es obligatorio")
    Long productVariantId
) {

}
