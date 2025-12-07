package com.paul.ecomerce.mapper;

import org.springframework.stereotype.Component;

import com.paul.ecomerce.dto.supplier.SupplierRequestDto;
import com.paul.ecomerce.dto.supplier.SupplierResponseDto;
import com.paul.ecomerce.model.entity.Supplier;

@Component
public class SupplierMapper {

    public SupplierResponseDto entityToSupplierDto(Supplier supplier) {
        return new SupplierResponseDto(
            supplier.getId(),
            supplier.getName(),
            supplier.getEmail(),
            supplier.getPhone(),
            supplier.getAddress(),
            supplier.isEnabled()
        );
    }

    public Supplier dtoToEntity(SupplierRequestDto supplierDto) {
        Supplier supplier = new Supplier();
        supplier.setName(supplierDto.name());
        supplier.setEmail(supplierDto.email());
        supplier.setPhone(supplierDto.phone());
        supplier.setAddress(supplierDto.address());

        return supplier;
    }
}
