package com.paul.ecomerce.service.validator;

import org.springframework.stereotype.Component;

import com.paul.ecomerce.dto.category.CategoryRequestDto;
import com.paul.ecomerce.dto.category.CategoryRequestUpdateDto;
import com.paul.ecomerce.exception.custom.BusinessException;
import com.paul.ecomerce.exception.custom.ResourceAlreadyExistsException;
import com.paul.ecomerce.exception.custom.ResourceNotFoundException;
import com.paul.ecomerce.model.entity.Category;
import com.paul.ecomerce.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CategoryValidator {
    private final CategoryRepository categoryRepository;

    public Category getCategoryOrThrow(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro la categoria con ID: " + id));
    }

    public void validateExists(Long id) {
        if(categoryRepository.existsById(id))
            throw new ResourceNotFoundException("No se encontro la categoria con ID: " + id);
    }

    public void validateForCreate(CategoryRequestDto categoryDto) {
        validateName(categoryDto.name());
    }

    public Category validateForUpdate(Long id, CategoryRequestUpdateDto categoryDto) {
        Category category = getCategoryOrThrow(id);
        validateNameForUpdate(category.getName(), categoryDto.name());
        validateEnabledForUpdate(categoryDto.enabled());
        return category;
    }

    public void validateDeactivation(Category category) {
        if (!category.isEnabled())
            throw new BusinessException("Ya esta desactivada la categoria con ID: " + category.getId());
    }

    // METODOS AUXILIARES

    private void validateName(String name) {
        if (categoryRepository.existsByName(name))
            throw new ResourceAlreadyExistsException("El nombre: " + name + " ya existe en la lista categorias");
    }

    private void validateNameForUpdate(String currentName, String newName) {
        if (!currentName.equals(newName) &&
                categoryRepository.existsByName(newName)) {
            throw new ResourceAlreadyExistsException("El nombre ya esta registrada");
        }
    }

    private void validateEnabledForUpdate(boolean enabled) {
        if(enabled == false)
            throw new BusinessException("Aqui solo se puede habilitar no desabilitar");
        
    }
}
