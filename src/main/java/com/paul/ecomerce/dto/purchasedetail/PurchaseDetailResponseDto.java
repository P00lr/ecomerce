package com.paul.ecomerce.dto.purchasedetail;

import java.math.BigDecimal;

public record PurchaseDetailResponseDto(
    Long id,
    String productName,
    BigDecimal unitPrice,
    Integer quantity,
    BigDecimal subtotal,
    Integer stock,
    String sku,
    boolean enabled
) {

}
