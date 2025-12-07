package com.paul.ecomerce.service.validator;

import org.springframework.stereotype.Component;

import com.paul.ecomerce.dto.supplier.SupplierRequestDto;
import com.paul.ecomerce.dto.supplier.SupplierRequestUpdateDto;
import com.paul.ecomerce.exception.custom.BusinessException;
import com.paul.ecomerce.exception.custom.ResourceAlreadyExistsException;
import com.paul.ecomerce.exception.custom.ResourceNotFoundException;
import com.paul.ecomerce.model.entity.Supplier;
import com.paul.ecomerce.repository.SupplierRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SupplierValidator {
    private final SupplierRepository supplierRepository;

    public Supplier getSupplierOrThrow(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro el proveedor con ID: " + id));
    }

    public void validateForCreate(SupplierRequestDto supplierDto) {
        validateName(supplierDto.name());
        validateAddress(supplierDto.address());
    }

    public Supplier validateForUpdate(Long id, SupplierRequestUpdateDto supplierDto) {
        Supplier supplier = getSupplierOrThrow(id);
        validateNameForUpdate(supplier.getName(), supplierDto.name());
        validateAddressForUpdate(supplier.getAddress(), supplierDto.address());
        validateEnabledForUpdate(supplierDto.enabled());
        return supplier;
    }

    public void validateDeactivation(Supplier supplier) {
        if (!supplier.isEnabled())
            throw new BusinessException("Ya esta desactivado el proveedor con ID: " + supplier.getId());
    }

    // METODOS AUXILIARES

    private void validateName(String name) {
        if (supplierRepository.existsByName(name))
            throw new ResourceAlreadyExistsException("El nombre: " + name + " ya existe en los proveedores");
    }

    private void validateAddress(String address) {
        if (supplierRepository.existsByAddress(address))
            throw new ResourceAlreadyExistsException("La direccion: " + address + " ya existe en los proveedores");
    }

    private void validateNameForUpdate(String currentName, String newName) {
        if (!currentName.equals(newName) &&
                supplierRepository.existsByName(newName)) {
            throw new ResourceAlreadyExistsException("El nombre ya esta registrado");
        }
    }

    private void validateAddressForUpdate(String currentAddress, String newAddress) {
        if (!currentAddress.equals(newAddress) &&
                supplierRepository.existsByAddress(newAddress)) {
            throw new ResourceAlreadyExistsException("La direccion ya esta registrada");
        }
    }

    private void validateEnabledForUpdate(boolean enabled) {
        if(enabled == false)
            throw new BusinessException("Aqui solo se puede habilitar no desabilitar");
        
    }
}
