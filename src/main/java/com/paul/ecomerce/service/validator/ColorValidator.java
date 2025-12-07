package com.paul.ecomerce.service.validator;

import org.springframework.stereotype.Component;

import com.paul.ecomerce.dto.color.ColorRequestDto;
import com.paul.ecomerce.dto.color.ColorRequestUpdateDto;
import com.paul.ecomerce.exception.custom.BusinessException;
import com.paul.ecomerce.exception.custom.ResourceAlreadyExistsException;
import com.paul.ecomerce.exception.custom.ResourceNotFoundException;
import com.paul.ecomerce.model.entity.Color;
import com.paul.ecomerce.repository.ColorRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ColorValidator {
    private final ColorRepository colorRepository;

    public Color getColorOrThrow(Long id) {
        return colorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro el color con ID: " + id));
    }

    public void validateForCreate(ColorRequestDto colorDto) {
        validateName(colorDto.name());
    }

    public Color validateForUpdate(Long id, ColorRequestUpdateDto colorDto) {
        Color color = getColorOrThrow(id);
        validateNameForUpdate(color.getName(), colorDto.name());
        validateEnabledForUpdate(colorDto.enabled());
        return color;
    }

    public void validateDeactivation(Color color) {
        if (!color.isEnabled())
            throw new BusinessException("Ya esta desactivado el color con ID: " + color.getId());
    }

    // METODOS AUXILIARES

    private void validateName(String name) {
        if (colorRepository.existsByName(name))
            throw new ResourceAlreadyExistsException("El nombre: " + name + " ya existe en los colores");
    }

    private void validateNameForUpdate(String currentName, String newName) {
        if (!currentName.equals(newName) &&
                colorRepository.existsByName(newName)) {
            throw new ResourceAlreadyExistsException("El nombre ya esta registrado");
        }
    }

    private void validateEnabledForUpdate(boolean enabled) {
        if(enabled == false)
            throw new BusinessException("Aqui solo se puede habilitar no desabilitar");
        
    }
}
