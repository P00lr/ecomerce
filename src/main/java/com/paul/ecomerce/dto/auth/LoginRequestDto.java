package com.paul.ecomerce.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
    @NotBlank(message = "El username es obligatorio")
    String username,

    @NotBlank(message = "La contraseña es obligatorio")
    String password
) {

}
