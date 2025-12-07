package com.paul.ecomerce.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CategoryRequestUpdateDto(
    @NotBlank(message = "El nombre no debe ser vacio")
    @Size(min = 3, max = 50, message = "El nombre debe contener entre 3 y 50 caracteres")
    String name,

    @NotNull
    boolean enabled
) {

}
