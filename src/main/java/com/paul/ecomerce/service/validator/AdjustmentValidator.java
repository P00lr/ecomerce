package com.paul.ecomerce.service.validator;

import org.springframework.stereotype.Component;

import com.paul.ecomerce.dto.adjustment.AdjustmentRequestDto;
import com.paul.ecomerce.exception.custom.ResourceNotFoundException;
import com.paul.ecomerce.model.entity.Adjustment;
import com.paul.ecomerce.model.enums.AdjustmentType;
import com.paul.ecomerce.repository.AdjustmentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AdjustmentValidator {

    private final AdjustmentRepository adjustmentRepository;

    public Adjustment getAdjustmentOrThrow(Long id) {
        return adjustmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No se encontro el ajuste con ID: " + id));
    }

    public void validateForCreate(AdjustmentRequestDto adjustmentDto) {
        validateType(adjustmentDto.type());
        if(adjustmentDto.adjustmentDetails().isEmpty())
            throw new IllegalArgumentException("Debe tener al menos un detalle de ajuste");
    }

    public void validateType(AdjustmentType type) {
        if (type != AdjustmentType.IN && type != AdjustmentType.OUT) {
            throw new ResourceNotFoundException("Tipo de ajuste inválido: solo IN o OUT");
        }
    }

}
