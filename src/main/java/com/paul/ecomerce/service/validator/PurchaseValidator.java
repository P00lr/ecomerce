package com.paul.ecomerce.service.validator;

import org.springframework.stereotype.Component;

import com.paul.ecomerce.dto.purchase.PurchaseRequestDto;
import com.paul.ecomerce.exception.custom.ResourceNotFoundException;
import com.paul.ecomerce.model.entity.Purchase;
import com.paul.ecomerce.repository.PurchaseRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PurchaseValidator {

    private final PurchaseRepository purchaseRepository;

    public Purchase getPurchaseOrThrow(Long id) {
        return purchaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro la compra con ID: " + id));
    }

    public void validateForCreate(PurchaseRequestDto purchaseDto) {

    }

}
