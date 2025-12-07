package com.paul.ecomerce.dto.sale;

import java.util.Set;

import com.paul.ecomerce.dto.saledetail.SaleDetailRequestDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record SaleRequestDto(

    @NotNull(message = "El id del usuario es obligatorio")
    Long userId,

    @NotEmpty(message = "Los detalles de ventas son obligatorios")
    Set<SaleDetailRequestDto> saleDetails
) {
    
}
