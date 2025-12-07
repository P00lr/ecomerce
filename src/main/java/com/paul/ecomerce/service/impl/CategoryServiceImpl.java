package com.paul.ecomerce.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paul.ecomerce.dto.PageResponseDto;
import com.paul.ecomerce.dto.category.CategoryRequestDto;
import com.paul.ecomerce.dto.category.CategoryRequestUpdateDto;
import com.paul.ecomerce.dto.category.CategoryResponseDto;
import com.paul.ecomerce.mapper.CategoryMapper;
import com.paul.ecomerce.mapper.PaginationMapper;
import com.paul.ecomerce.model.entity.Category;
import com.paul.ecomerce.repository.CategoryRepository;
import com.paul.ecomerce.service.CategoryService;
import com.paul.ecomerce.service.validator.CategoryValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final PaginationMapper paginationMapper;
    private final CategoryMapper categoryMapper;
    private final CategoryValidator categoryValidator;

    @Override
    public PageResponseDto<CategoryResponseDto> getAllCategoriesPage(Pageable pageable) {
        log.info("Verificando lista de categorias");
        Page<Category> categoriesPage = categoryRepository.findAll(pageable);
        return paginationMapper.toPageResponseDto(categoriesPage, categoryMapper::entityToCategoryDto);
    }

    @Override
    public CategoryResponseDto getCategoryById(Long id) {
        log.info("Verificando si existe la categoria con ID: " + id);
        Category category = categoryValidator.getCategoryOrThrow(id);
        return categoryMapper.entityToCategoryDto(category);
    }

    @Override
    @Transactional
    public CategoryResponseDto createCategory(CategoryRequestDto categoryDto) {
        log.info("Validando datos para crear la categoria");
        categoryValidator.validateForCreate(categoryDto);

        Category category = categoryMapper.dtoToEntity(categoryDto);
        categoryRepository.save(category);

        return categoryMapper.entityToCategoryDto(category);


    }

    @Override
    @Transactional
    public CategoryResponseDto updateCategory(Long id, CategoryRequestUpdateDto categoryDto) {
        log.info("Validando datos para actualizar la categoria");
        Category category = categoryValidator.validateForUpdate(id, categoryDto);
        category.updateFromDto(categoryDto);

        categoryRepository.save(category);

        return categoryMapper.entityToCategoryDto(category);

    }

    @Override
    @Transactional
    public void deactivateCategoryById(Long id) {
        Category category = categoryValidator.getCategoryOrThrow(id);
        categoryValidator.validateDeactivation(category);

        category.setEnabled(false);

        categoryRepository.save(category);

    }
    
}
