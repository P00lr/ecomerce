package com.paul.ecomerce.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paul.ecomerce.dto.PageResponseDto;
import com.paul.ecomerce.dto.brand.BrandRequestDto;
import com.paul.ecomerce.dto.brand.BrandRequestUpdateDto;
import com.paul.ecomerce.dto.brand.BrandResponseDto;
import com.paul.ecomerce.mapper.BrandMapper;
import com.paul.ecomerce.mapper.PaginationMapper;
import com.paul.ecomerce.model.entity.Brand;
import com.paul.ecomerce.repository.BrandRepository;
import com.paul.ecomerce.service.BrandService;
import com.paul.ecomerce.service.validator.BrandValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService{

    private final BrandRepository brandRepository;
    private final PaginationMapper paginationMapper;
    private final BrandMapper brandMapper;
    private final BrandValidator brandValidator;

    @Override
    public PageResponseDto<BrandResponseDto> getAllBrandsPage(Pageable pageable) {
        log.info("Verificando lista de marcas");
        Page<Brand> brandsPage = brandRepository.findAll(pageable);
        return paginationMapper.toPageResponseDto(brandsPage, brandMapper::entityToBrandDto);
    }

    @Override
    public BrandResponseDto getBrandById(Long id) {
        log.info("Verificando si existe la marca con ID: " + id);
        Brand brand = brandValidator.getBrandOrThrow(id);
        return brandMapper.entityToBrandDto(brand);
    }

    @Override
    @Transactional
    public BrandResponseDto createBrand(BrandRequestDto brandDto) {
        log.info("Validando datos para crear la marca");
        brandValidator.validateForCreate(brandDto);

        Brand brand = brandMapper.dtoToEntity(brandDto);
        brandRepository.save(brand);

        return brandMapper.entityToBrandDto(brand);


    }

    @Override
    @Transactional
    public BrandResponseDto updateBrand(Long id, BrandRequestUpdateDto brandDto) {
        log.info("Validando datos para actualizar la marca");
        Brand brand = brandValidator.validateForUpdate(id, brandDto);
        brand.updateFromDto(brandDto);

        brandRepository.save(brand);

        return brandMapper.entityToBrandDto(brand);

    }

    @Override
    @Transactional
    public void deactivateBrandById(Long id) {
        Brand brand = brandValidator.getBrandOrThrow(id);
        brandValidator.validateDeactivation(brand);

        brand.setEnabled(false);

        brandRepository.save(brand);

    }
    
}
