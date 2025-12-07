package com.paul.ecomerce.service;

import org.springframework.data.domain.Pageable;

import com.paul.ecomerce.dto.PageResponseDto;
import com.paul.ecomerce.dto.category.CategoryRequestDto;
import com.paul.ecomerce.dto.category.CategoryRequestUpdateDto;
import com.paul.ecomerce.dto.category.CategoryResponseDto;

public interface CategoryService {
    PageResponseDto<CategoryResponseDto> getAllCategoriesPage(Pageable pageable);
    CategoryResponseDto getCategoryById(Long id);
    CategoryResponseDto createCategory(CategoryRequestDto categoryDto);
    CategoryResponseDto updateCategory(Long id, CategoryRequestUpdateDto categoryDto);
    void deactivateCategoryById(Long id);
}
