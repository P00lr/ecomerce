package com.paul.ecomerce.dto.adjustment;

import java.time.LocalDateTime;
import java.util.List;

import com.paul.ecomerce.dto.adjustmentdetail.AdjustmentDetailResponseDto;
import com.paul.ecomerce.model.enums.AdjustmentType;

public record AdjustmentResponseDto(
    Long userId,
    String userName,
    AdjustmentType type,
    String description,
    LocalDateTime date,
    List<AdjustmentDetailResponseDto> adjustmentDetails
) {

}
