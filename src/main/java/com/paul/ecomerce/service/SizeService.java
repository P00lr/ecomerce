package com.paul.ecomerce.service;

import org.springframework.data.domain.Pageable;

import com.paul.ecomerce.dto.PageResponseDto;
import com.paul.ecomerce.dto.size.SizeRequestDto;
import com.paul.ecomerce.dto.size.SizeRequestUpdateDto;
import com.paul.ecomerce.dto.size.SizeResponseDto;

public interface SizeService {
    PageResponseDto<SizeResponseDto> getAllSizesPage(Pageable pageable);
    SizeResponseDto getSizeById(Long id);
    SizeResponseDto createSize(SizeRequestDto sizeDto);
    SizeResponseDto updateSize(Long id, SizeRequestUpdateDto sizeDto);
    void deactivateSizeById(Long id);
}
