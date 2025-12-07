package com.paul.ecomerce.service;

import org.springframework.data.domain.Pageable;

import com.paul.ecomerce.dto.PageResponseDto;
import com.paul.ecomerce.dto.adjustment.AdjustmentRequestDto;
import com.paul.ecomerce.dto.adjustment.AdjustmentResponseDto;

public interface AdjustmentService {
    PageResponseDto<AdjustmentResponseDto> getAllAdjustmentPaged(Pageable pageable);
    AdjustmentResponseDto getAdjustmentById(Long id);
    AdjustmentResponseDto createAdjustment(AdjustmentRequestDto adjustmentDto);
    void deactivateAdjustmentById(Long id);
}
