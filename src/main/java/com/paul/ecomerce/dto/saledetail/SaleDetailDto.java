package com.paul.ecomerce.dto.saledetail;

import java.math.BigDecimal;

public record SaleDetailDto(
    Long productVariantId,
    String productName,
    String colorName,
    String sizeName,
    BigDecimal unitPrice,
    Integer quantity,
    BigDecimal subtotal

) {

}
