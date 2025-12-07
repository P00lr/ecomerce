package com.paul.ecomerce.dto.size;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SizeRequestUpdateDto(
    @NotBlank(message = "El nombre no debe ser vacio")
    @Size(min = 1, max = 50, message = "El nombre debe contener entre 1 y 50 caracteres")
    String name,

    @NotNull
    boolean enabled
) {

}
