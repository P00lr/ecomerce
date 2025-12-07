package com.paul.ecomerce.service.validator;

import org.springframework.stereotype.Component;

import com.paul.ecomerce.dto.size.SizeRequestDto;
import com.paul.ecomerce.dto.size.SizeRequestUpdateDto;
import com.paul.ecomerce.exception.custom.BusinessException;
import com.paul.ecomerce.exception.custom.ResourceAlreadyExistsException;
import com.paul.ecomerce.exception.custom.ResourceNotFoundException;
import com.paul.ecomerce.model.entity.Size;
import com.paul.ecomerce.repository.SizeRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SizeValidator {
    private final SizeRepository sizeRepository;

    public Size getSizeOrThrow(Long id) {
        return sizeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro la talla con ID: " + id));
    }

    public void validateForCreate(SizeRequestDto sizeDto) {
        validateName(sizeDto.name());
    }

    public Size validateForUpdate(Long id, SizeRequestUpdateDto sizeDto) {
        Size size = getSizeOrThrow(id);
        validateNameForUpdate(size.getName(), sizeDto.name());
        validateEnabledForUpdate(sizeDto.enabled());
        return size;
    }

    public void validateDeactivation(Size size) {
        if (!size.isEnabled())
            throw new BusinessException("Ya esta desactivada la talla con ID: " + size.getId());
    }

    // METODOS AUXILIARES

    private void validateName(String name) {
        if (sizeRepository.existsByName(name))
            throw new ResourceAlreadyExistsException("El nombre: " + name + " ya existe en las tallas");
    }

    private void validateNameForUpdate(String currentName, String newName) {
        if (!currentName.equals(newName) &&
                sizeRepository.existsByName(newName)) {
            throw new ResourceAlreadyExistsException("El nombre ya esta registrado");
        }
    }

    private void validateEnabledForUpdate(boolean enabled) {
        if(enabled == false)
            throw new BusinessException("Aqui solo se puede habilitar no desabilitar");
        
    }
}
