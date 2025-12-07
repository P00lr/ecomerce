package com.paul.ecomerce.dto.size;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SizeRequestDto(
    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(min = 1, max = 50, message = "El nombre debe tener entre 1 y 50 caracteres")
    String name
) {

}
