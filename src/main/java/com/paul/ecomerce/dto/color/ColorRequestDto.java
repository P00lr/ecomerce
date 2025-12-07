package com.paul.ecomerce.dto.color;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ColorRequestDto(
    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    String name
) {

}
