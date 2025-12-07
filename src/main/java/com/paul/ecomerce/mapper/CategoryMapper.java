package com.paul.ecomerce.mapper;

import org.springframework.stereotype.Component;

import com.paul.ecomerce.dto.category.CategoryRequestDto;
import com.paul.ecomerce.dto.category.CategoryResponseDto;
import com.paul.ecomerce.model.entity.Category;

@Component
public class CategoryMapper {

    public CategoryResponseDto entityToCategoryDto(Category category) {
        return new CategoryResponseDto(
            category.getId(),
            category.getName(),
            category.isEnabled()
        );
    }

    public Category dtoToEntity(CategoryRequestDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.name());

        return category;
    }
}
