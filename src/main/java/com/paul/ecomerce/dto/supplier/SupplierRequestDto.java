package com.paul.ecomerce.dto.supplier;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SupplierRequestDto(
    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    String name,

    @NotBlank(message = "El email no puede estar vacio")
    @Email(message = "El email debe ser valido")
    String email,

    @NotBlank(message = "El telefono no puede estar vacio")
    @Pattern(regexp = "^[0-9\\-\\+\\s\\(\\)]+$", message = "El telefono debe contener solo numeros, simbolos y espacios")
    String phone,

    @NotBlank(message = "La direccion no puede estar vacia")
    @Size(min = 5, max = 255, message = "La direccion debe tener entre 5 y 255 caracteres")
    String address
) {

}
