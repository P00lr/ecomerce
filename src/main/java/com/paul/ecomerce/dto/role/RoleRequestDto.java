package com.paul.ecomerce.dto.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RoleRequestDto(
    @NotBlank(message = "El campo nombre no debe estar vacio")
    @Size(min = 3, max = 50, message = "El campo nombre debe tener entre 3 y 50 caracteres")
    String name
) {

}
