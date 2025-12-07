package com.paul.ecomerce.service;

import org.springframework.data.domain.Pageable;

import com.paul.ecomerce.dto.PageResponseDto;
import com.paul.ecomerce.dto.purchase.PurchaseRequestDto;
import com.paul.ecomerce.dto.purchase.PurchaseResponseDto;

public interface PurchaseService {
    PageResponseDto<PurchaseResponseDto> getAllPurchasesPaged(Pageable pageable); 
    PurchaseResponseDto createPurchase(PurchaseRequestDto purchaseDto);
    PurchaseResponseDto getPurchaseById(Long id);
    void deactivatePurchaseById(Long id);
}
