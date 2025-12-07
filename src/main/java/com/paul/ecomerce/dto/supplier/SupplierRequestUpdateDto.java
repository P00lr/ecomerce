package com.paul.ecomerce.dto.supplier;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SupplierRequestUpdateDto(
    @NotBlank(message = "El nombre no debe ser vacio")
    @Size(min = 3, max = 100, message = "El nombre debe contener entre 3 y 100 caracteres")
    String name,

    @NotBlank(message = "El email no debe ser vacio")
    @Email(message = "El email debe ser valido")
    String email,

    @NotBlank(message = "El telefono no debe ser vacio")
    @Pattern(regexp = "^[0-9\\-\\+\\s\\(\\)]+$", message = "El telefono debe contener solo numeros, simbolos y espacios")
    String phone,

    @NotBlank(message = "La direccion no debe ser vacia")
    @Size(min = 5, max = 255, message = "La direccion debe tener entre 5 y 255 caracteres")
    String address,

    @NotNull
    boolean enabled
) {

}
