package com.paul.ecomerce.dto.purchasedetail;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record PurchaseDetailRequestDto(
    @NotNull
    Long productVariantId,

    @Positive
    Integer quantity,

    @PositiveOrZero
    BigDecimal unitCost
) {

}
