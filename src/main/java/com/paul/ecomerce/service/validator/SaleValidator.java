package com.paul.ecomerce.service.validator;

import org.springframework.stereotype.Component;

import com.paul.ecomerce.dto.sale.SaleRequestDto;
import com.paul.ecomerce.exception.custom.ResourceNotFoundException;
import com.paul.ecomerce.model.entity.Sale;
import com.paul.ecomerce.repository.SaleRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SaleValidator {

    private final SaleRepository saleRepository;

    public Sale getSaleOrThrow(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro la venta con id: " + id));
    }

    public void validateForCreate(SaleRequestDto saleDto) {
        
    }
}
