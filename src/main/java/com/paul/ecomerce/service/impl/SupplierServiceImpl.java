package com.paul.ecomerce.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paul.ecomerce.dto.PageResponseDto;
import com.paul.ecomerce.dto.supplier.SupplierRequestDto;
import com.paul.ecomerce.dto.supplier.SupplierRequestUpdateDto;
import com.paul.ecomerce.dto.supplier.SupplierResponseDto;
import com.paul.ecomerce.mapper.SupplierMapper;
import com.paul.ecomerce.mapper.PaginationMapper;
import com.paul.ecomerce.model.entity.Supplier;
import com.paul.ecomerce.repository.SupplierRepository;
import com.paul.ecomerce.service.SupplierService;
import com.paul.ecomerce.service.validator.SupplierValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService{

    private final SupplierRepository supplierRepository;
    private final PaginationMapper paginationMapper;
    private final SupplierMapper supplierMapper;
    private final SupplierValidator supplierValidator;

    @Override
    public PageResponseDto<SupplierResponseDto> getAllSuppliersPage(Pageable pageable) {
        log.info("Verificando lista de proveedores");
        Page<Supplier> suppliersPage = supplierRepository.findAll(pageable);
        return paginationMapper.toPageResponseDto(suppliersPage, supplierMapper::entityToSupplierDto);
    }

    @Override
    public SupplierResponseDto getSupplierById(Long id) {
        log.info("Verificando si existe el proveedor con ID: " + id);
        Supplier supplier = supplierValidator.getSupplierOrThrow(id);
        return supplierMapper.entityToSupplierDto(supplier);
    }

    @Override
    @Transactional
    public SupplierResponseDto createSupplier(SupplierRequestDto supplierDto) {
        log.info("Validando datos para crear el proveedor");
        supplierValidator.validateForCreate(supplierDto);

        Supplier supplier = supplierMapper.dtoToEntity(supplierDto);
        supplier.setEnabled(true);
        supplierRepository.save(supplier);

        return supplierMapper.entityToSupplierDto(supplier);


    }

    @Override
    @Transactional
    public SupplierResponseDto updateSupplier(Long id, SupplierRequestUpdateDto supplierDto) {
        log.info("Validando datos para actualizar el proveedor");
        Supplier supplier = supplierValidator.validateForUpdate(id, supplierDto);
        supplier.updateFromDto(supplierDto);

        supplierRepository.save(supplier);

        return supplierMapper.entityToSupplierDto(supplier);

    }

    @Override
    @Transactional
    public void deactivateSupplierById(Long id) {
        Supplier supplier = supplierValidator.getSupplierOrThrow(id);
        supplierValidator.validateDeactivation(supplier);

        supplier.setEnabled(false);

        supplierRepository.save(supplier);

    }
    
}
