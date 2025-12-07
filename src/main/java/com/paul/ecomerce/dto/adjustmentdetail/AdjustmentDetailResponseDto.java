package com.paul.ecomerce.dto.adjustmentdetail;

public record AdjustmentDetailResponseDto(
    Long productVariantId,
    String productVariantName,
    Integer quantity,
    String sku,
    Integer stock
) {

}
