package com.paul.ecomerce.service;

import org.springframework.data.domain.Pageable;

import com.paul.ecomerce.dto.PageResponseDto;
import com.paul.ecomerce.dto.supplier.SupplierRequestDto;
import com.paul.ecomerce.dto.supplier.SupplierRequestUpdateDto;
import com.paul.ecomerce.dto.supplier.SupplierResponseDto;

public interface SupplierService {
    PageResponseDto<SupplierResponseDto> getAllSuppliersPage(Pageable pageable);
    SupplierResponseDto getSupplierById(Long id);
    SupplierResponseDto createSupplier(SupplierRequestDto supplierDto);
    SupplierResponseDto updateSupplier(Long id, SupplierRequestUpdateDto supplierDto);
    void deactivateSupplierById(Long id);
}
