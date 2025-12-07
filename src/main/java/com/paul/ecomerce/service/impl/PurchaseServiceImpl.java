package com.paul.ecomerce.service.impl;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paul.ecomerce.dto.PageResponseDto;
import com.paul.ecomerce.dto.purchase.PurchaseRequestDto;
import com.paul.ecomerce.dto.purchase.PurchaseResponseDto;
import com.paul.ecomerce.dto.purchasedetail.PurchaseDetailRequestDto;
import com.paul.ecomerce.mapper.PaginationMapper;
import com.paul.ecomerce.mapper.PurchaseMapper;
import com.paul.ecomerce.model.entity.AppUser;
import com.paul.ecomerce.model.entity.ProductVariant;
import com.paul.ecomerce.model.entity.Purchase;
import com.paul.ecomerce.model.entity.Supplier;
import com.paul.ecomerce.repository.PurchaseRepository;
import com.paul.ecomerce.service.ProductVariantService;
import com.paul.ecomerce.service.PurchaseService;
import com.paul.ecomerce.service.validator.ProductVariantValidator;
import com.paul.ecomerce.service.validator.PurchaseValidator;
import com.paul.ecomerce.service.validator.SupplierValidator;
import com.paul.ecomerce.service.validator.UserValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseMapper purchaseMapper;

    private final PurchaseValidator purchaseValidator;
    private final PurchaseRepository purchaseRepository;

    private final SupplierValidator supplierValidator;

    private final ProductVariantValidator productVariantValidator;
    private final ProductVariantService productVariantService;

    private final UserValidator userValidator;

    private final PaginationMapper paginationMapper;

    @Override
    public PageResponseDto<PurchaseResponseDto> getAllPurchasesPaged(Pageable pageable) {
        log.info("Verificando lista de compras");
        Page<Purchase> purchasesPaged = purchaseRepository.findAll(pageable);
        return paginationMapper.toPageResponseDto(purchasesPaged, purchaseMapper::toPurchaseResponseDto);
    }

    @Override
    public PurchaseResponseDto getPurchaseById(Long id) {
        log.info("Verificando si existe la venta");
        Purchase purchase = purchaseValidator.getPurchaseOrThrow(id);
        return purchaseMapper.toPurchaseResponseDto(purchase);
    }


    @Override
    public PurchaseResponseDto createPurchase(PurchaseRequestDto purchaseDto) {
        log.info("Validando datos para crear la compra");
        purchaseValidator.validateForCreate(purchaseDto);

        Purchase purchase = new Purchase();

        Supplier supplier = supplierValidator.getSupplierOrThrow(purchaseDto.supplierId());
        purchase.setSupplier(supplier);

        AppUser user = userValidator.getUserOrThrow(purchaseDto.userId());
        purchase.setUser(user);

        for (PurchaseDetailRequestDto detailDto : purchaseDto.purchaseDetails()) {

            ProductVariant productVariant = productVariantValidator
                    .geProductVariantOrThrow(detailDto.productVariantId());

            purchase.createDetail(productVariant, detailDto.quantity(), detailDto.unitCost());
            productVariantService.increaseStock(productVariant, detailDto.quantity());
        }

        purchase.setComments(purchaseDto.comments());
        purchase.updateTotals();

        purchaseRepository.save(purchase);

        return purchaseMapper.toPurchaseResponseDto(purchase);

    }

    @Override
    @Transactional
    public void deactivatePurchaseById(Long id) {
        log.info("Validando si existe la compra con ID: " + id);
        Purchase purchase = purchaseValidator.getPurchaseOrThrow(id);
        purchase.deactivate();
        purchaseRepository.save(purchase);
    }
    
}
