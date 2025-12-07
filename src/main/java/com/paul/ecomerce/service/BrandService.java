package com.paul.ecomerce.service;

import org.springframework.data.domain.Pageable;

import com.paul.ecomerce.dto.PageResponseDto;
import com.paul.ecomerce.dto.brand.BrandRequestDto;
import com.paul.ecomerce.dto.brand.BrandRequestUpdateDto;
import com.paul.ecomerce.dto.brand.BrandResponseDto;

public interface BrandService {
    PageResponseDto<BrandResponseDto> getAllBrandsPage(Pageable pageable);
    BrandResponseDto getBrandById(Long id);
    BrandResponseDto createBrand(BrandRequestDto brandDto);
    BrandResponseDto updateBrand(Long id, BrandRequestUpdateDto brandDto);
    void deactivateBrandById(Long id);
}
