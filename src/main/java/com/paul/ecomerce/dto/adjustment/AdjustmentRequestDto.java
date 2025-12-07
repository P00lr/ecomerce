package com.paul.ecomerce.dto.adjustment;

import java.util.List;

import com.paul.ecomerce.dto.adjustmentdetail.AdjustmentDetailDto;
import com.paul.ecomerce.model.enums.AdjustmentType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AdjustmentRequestDto(
        @NotNull(message = "El campo userId no debe de estar vacio")
        Long userId,

        @NotNull(message = "El campo tipo tiene que tener un estado valido IN o OUT")
        AdjustmentType type,

        @NotBlank(message = "El campo no debe estar vacio") 
        @Size(min = 3, max = 200, message = "La descripcion debe tener entre 3 y 200 caracteres maximo") 
        String description,

        @NotEmpty
        List<AdjustmentDetailDto> adjustmentDetails) {

}
