package com.paul.ecomerce.mapper;

import org.springframework.stereotype.Component;

import com.paul.ecomerce.dto.brand.BrandRequestDto;
import com.paul.ecomerce.dto.brand.BrandResponseDto;
import com.paul.ecomerce.model.entity.Brand;

@Component
public class BrandMapper {

    public BrandResponseDto entityToBrandDto(Brand brand) {
        return new BrandResponseDto(
            brand.getId(),
            brand.getName(),
            brand.isEnabled()
        );
    }

    public Brand dtoToEntity(BrandRequestDto brandDto) {
        Brand brand = new Brand();
        brand.setName(brandDto.name());

        return brand;
    }
}
