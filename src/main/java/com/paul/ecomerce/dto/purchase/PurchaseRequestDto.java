package com.paul.ecomerce.dto.purchase;

import java.util.Set;

import com.paul.ecomerce.dto.purchasedetail.PurchaseDetailRequestDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PurchaseRequestDto(
    @NotNull
    Long supplierId,

    @NotNull
    Long userId,

    @NotEmpty
    Set<PurchaseDetailRequestDto> purchaseDetails,
    String comments
) {

}