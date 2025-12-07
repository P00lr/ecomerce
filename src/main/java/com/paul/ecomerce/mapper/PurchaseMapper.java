package com.paul.ecomerce.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.paul.ecomerce.dto.purchase.PurchaseResponseDto;
import com.paul.ecomerce.dto.purchasedetail.PurchaseDetailResponseDto;
import com.paul.ecomerce.model.entity.Purchase;
import com.paul.ecomerce.model.entity.PurchaseDetail;

@Component
public class PurchaseMapper {


    public PurchaseDetailResponseDto toDetailDto(PurchaseDetail purchaseDetail) {
        return new PurchaseDetailResponseDto(
            purchaseDetail.getId(),
            purchaseDetail.getProductVariant().getProduct().getName(),
            purchaseDetail.getUnitPrice(),
            purchaseDetail.getQuantity(),
            purchaseDetail.getSubtotal(),
            purchaseDetail.getProductVariant().getStock(),
            purchaseDetail.getProductVariant().getSku(),
            purchaseDetail.getProductVariant().isEnabled()
        );
    }

    public List<PurchaseDetailResponseDto> toDetailsDto(Set<PurchaseDetail> purchaseDetails) {
        List<PurchaseDetailResponseDto> purchaseDetailResponseDtos = new ArrayList<>();

        for (PurchaseDetail purchaseDetail : purchaseDetails) {
            PurchaseDetailResponseDto purchaseDetailDTO = toDetailDto(purchaseDetail);
            purchaseDetailResponseDtos.add(purchaseDetailDTO);
        }

        return purchaseDetailResponseDtos;
    }

    public PurchaseResponseDto toPurchaseResponseDto(Purchase purchase) {
        return new PurchaseResponseDto(
            purchase.getId(), 
            purchase.getUser().getName(), 
            purchase.getSupplier().getName(), 
            purchase.getTotalQuantity(), 
            purchase.getTotalAmount(), 
            purchase.getDate(), 
            purchase.isEnabled(), 
            toDetailsDto(purchase.getPurchaseDetails()),
            purchase.getComments());
    }
}
