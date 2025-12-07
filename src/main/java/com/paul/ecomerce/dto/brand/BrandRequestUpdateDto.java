package com.paul.ecomerce.dto.brand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BrandRequestUpdateDto(
    @NotBlank(message = "El nombre no debe ser vacio")
    @Size(min = 3, max = 100, message = "El nombre debe contener entre 3 y 100 caracteres")
    String name,

    @NotNull
    boolean enabled
) {

}
