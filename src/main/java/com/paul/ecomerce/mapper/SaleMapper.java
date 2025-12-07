package com.paul.ecomerce.mapper;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.paul.ecomerce.dto.sale.SaleResponseDto;
import com.paul.ecomerce.dto.saledetail.SaleDetailDto;
import com.paul.ecomerce.model.entity.Sale;
import com.paul.ecomerce.model.entity.SaleDetail;

@Component
public class SaleMapper {

    public final SaleResponseDto toSaleResponseDto(final Sale sale) {
        return new SaleResponseDto(
                sale.getId(),
                sale.getAppUser().getName(),
                sale.getTotalQuantity(),
                sale.getTotalAmount(),
                sale.getDate(),
                sale.isEnabled(),
                toDetailDtos(sale.getSaleDetails()));
    }

    public final SaleDetailDto toDetailDto(final SaleDetail saleDetail) {
        return new SaleDetailDto(
                saleDetail.getProductVariant().getId(),
                saleDetail.getProductVariant().getProduct().getName(),
                saleDetail.getProductVariant().getColor().getName(),
                saleDetail.getProductVariant().getSize().getName(),
                saleDetail.getProductVariant().getPrice(),
                saleDetail.getQuantity(),
                saleDetail.getSubtotal());
    }

    public final List<SaleDetailDto> toDetailDtos(final Set<SaleDetail> saleDetails) {
        if (saleDetails == null)
            return Collections.emptyList();

        return saleDetails.stream()
                .map(this::toDetailDto)
                .toList();
    }

}
