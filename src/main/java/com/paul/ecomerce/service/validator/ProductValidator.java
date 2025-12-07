package com.paul.ecomerce.service.validator;

import org.springframework.stereotype.Component;

import com.paul.ecomerce.dto.product.ProductRequestDto;
import com.paul.ecomerce.exception.custom.ResourceAlreadyExistsException;
import com.paul.ecomerce.exception.custom.ResourceNotFoundException;
import com.paul.ecomerce.model.entity.Product;
import com.paul.ecomerce.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductValidator {

    private final ProductRepository productRepository;


    public Product getProductOrThrow(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No se encontro el producto con ID: " + id));
    }

    public void validateForCreate(ProductRequestDto productDto) {
        validateName(productDto.name());
    }

    private void validateName(String name) {
        if(productRepository.existsByName(name))
            throw new ResourceAlreadyExistsException("El nombre del producto ya existe");
    }

    public Product validateForUpdate(Long productId, ProductRequestDto productRequestDto) {
        Product product = getProductOrThrow(productId);
        validateNameUpdate(product.getName(), productRequestDto.name());
        return product;
    } 

    private void validateNameUpdate(String currentName, String newName) {
        if(!currentName.equals(newName) &&
            productRepository.existsByName(newName)
        )
        throw new ResourceAlreadyExistsException("El nombre ya existe");
    }
}
