package com.paul.ecomerce.mapper;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.paul.ecomerce.dto.adjustment.AdjustmentResponseDto;
import com.paul.ecomerce.dto.adjustmentdetail.AdjustmentDetailResponseDto;
import com.paul.ecomerce.model.entity.Adjustment;
import com.paul.ecomerce.model.entity.AdjustmentDetail;

@Component
public class AdjustmentMapper {

    public AdjustmentResponseDto toAdjustmentResponseDto(Adjustment adjustment) {
        return new AdjustmentResponseDto(
                adjustment.getUser().getId(),
                adjustment.getUser().getName(),
                adjustment.getType(),
                adjustment.getDescription(),
                adjustment.getDate(),
                toAdjustmentDetailResponseDtos(adjustment.getAdjustmentDetails()));
    }

    public AdjustmentDetailResponseDto toAdjustmentDetailResponseDto(AdjustmentDetail adjustmentDetail) {
        return new AdjustmentDetailResponseDto(adjustmentDetail.getProductVariant().getId(),
                adjustmentDetail.getProductVariant().getProduct().getName(),
                adjustmentDetail.getQuantity(),
                adjustmentDetail.getProductVariant().getSku(),
                adjustmentDetail.getProductVariant().getStock());
    }

    public List<AdjustmentDetailResponseDto> toAdjustmentDetailResponseDtos(Set<AdjustmentDetail> adjustmentDetails) {
        return adjustmentDetails.stream()
                .map(this::toAdjustmentDetailResponseDto)
                .toList();
    }
}
