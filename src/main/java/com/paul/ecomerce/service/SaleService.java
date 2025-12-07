package com.paul.ecomerce.service;

import org.springframework.data.domain.Pageable;

import com.paul.ecomerce.dto.PageResponseDto;
import com.paul.ecomerce.dto.sale.SaleRequestDto;
import com.paul.ecomerce.dto.sale.SaleResponseDto;

public interface SaleService {
    PageResponseDto<SaleResponseDto> getAllSales(Pageable pageable);
    SaleResponseDto getSaleById(Long id);
    SaleResponseDto createSale(SaleRequestDto saleRequestDto);
    void deactivateSaleById(Long id);

}
