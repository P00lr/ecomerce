package com.paul.ecomerce.dto.purchase;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.paul.ecomerce.dto.purchasedetail.PurchaseDetailResponseDto;

public record PurchaseResponseDto(
    Long id,
    String userName,
    String supplierName,
    Integer totalQuantity,
    BigDecimal totalAmount,
    LocalDateTime date,
    boolean enabled,
    List<PurchaseDetailResponseDto> purchaseDetails,
    String comments
) {

}
