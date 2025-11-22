package com.paul.ecomerce.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequestDto(
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 3 , max = 100, message = "El nombre no puede superar 100 caracteres")
    String name,

    @NotBlank(message = "El username no puede estar vacío")
    @Size(min = 3, max = 50, message = "El username debe tener entre 3 y 50 caracteres")
    String username,

    @NotBlank(message = "El email no puede estar vacío")
    @Pattern(
        regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
        message = "El email debe tener un formato válido"
    )
    @Size(max = 100, message = "El email no puede superar 100 caracteres")
    String email
) {}

