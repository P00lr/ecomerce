package com.paul.ecomerce.dto.product;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProductRequestDto(
    @NotBlank(message = "El nombre no debe de estar vacio")
    @Size(min = 3, max = 50, message = "El nombre debe contener entre 3 y 50 caracteres")
    String name,

    @NotNull(message = "El precio base no debe de estar vacio")
    @Positive
    BigDecimal basePrice,

    @NotBlank(message = "La descripcion no debe de estar vacia")
    @Size(min = 3, max = 150, message = "El nombre debe contener entre 3 y 50 caracteres")
    String description,

    @NotNull
    Long categoryId,

    @NotNull
    Long brandId
) {

}
