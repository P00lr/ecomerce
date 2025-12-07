package com.paul.ecomerce.service;

import org.springframework.data.domain.Pageable;

import com.paul.ecomerce.dto.PageResponseDto;
import com.paul.ecomerce.dto.color.ColorRequestDto;
import com.paul.ecomerce.dto.color.ColorRequestUpdateDto;
import com.paul.ecomerce.dto.color.ColorResponseDto;

public interface ColorService {
    PageResponseDto<ColorResponseDto> getAllColorsPage(Pageable pageable);
    ColorResponseDto getColorById(Long id);
    ColorResponseDto createColor(ColorRequestDto colorDto);
    ColorResponseDto updateColor(Long id, ColorRequestUpdateDto colorDto);
    void deactivateColorById(Long id);
}
