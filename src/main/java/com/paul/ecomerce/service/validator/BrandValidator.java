package com.paul.ecomerce.service.validator;

import org.springframework.stereotype.Component;

import com.paul.ecomerce.dto.brand.BrandRequestDto;
import com.paul.ecomerce.dto.brand.BrandRequestUpdateDto;
import com.paul.ecomerce.exception.custom.BusinessException;
import com.paul.ecomerce.exception.custom.ResourceAlreadyExistsException;
import com.paul.ecomerce.exception.custom.ResourceNotFoundException;
import com.paul.ecomerce.model.entity.Brand;
import com.paul.ecomerce.repository.BrandRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BrandValidator {
    private final BrandRepository brandRepository;

    public Brand getBrandOrThrow(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro la marca con ID: " + id));
    }

    public void validateExists(Long id) {
        if(brandRepository.existsById(id))
            throw new ResourceNotFoundException("No se encontro la marca con ID: " + id);
    }

    public void validateForCreate(BrandRequestDto brandDto) {
        validateName(brandDto.name());
    }

    public Brand validateForUpdate(Long id, BrandRequestUpdateDto brandDto) {
        Brand brand = getBrandOrThrow(id);
        validateNameForUpdate(brand.getName(), brandDto.name());
        validateEnabledForUpdate(brandDto.enabled());
        return brand;
    }

    public void validateDeactivation(Brand brand) {
        if (!brand.isEnabled())
            throw new BusinessException("Ya esta desactivada la marca con ID: " + brand.getId());
    }

    // METODOS AUXILIARES

    private void validateName(String name) {
        if (brandRepository.existsByName(name))
            throw new ResourceAlreadyExistsException("El nombre: " + name + " ya existe en las marcas");
    }

    private void validateNameForUpdate(String currentName, String newName) {
        if (!currentName.equals(newName) &&
                brandRepository.existsByName(newName)) {
            throw new ResourceAlreadyExistsException("El nombre ya esta registrada");
        }
    }

    private void validateEnabledForUpdate(boolean enabled) {
        if(enabled == false)
            throw new BusinessException("Aqui solo se puede habilitar no desabilitar");
        
    }
}
