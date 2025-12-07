package com.paul.ecomerce.dto.sale;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.paul.ecomerce.dto.saledetail.SaleDetailDto;

public record SaleResponseDto(
        Long id,
        String userName,
        Integer totalQuantity,
        BigDecimal totalAmount,
        LocalDateTime date,
        boolean enabed,
        List<SaleDetailDto> saleDetails) {

}
