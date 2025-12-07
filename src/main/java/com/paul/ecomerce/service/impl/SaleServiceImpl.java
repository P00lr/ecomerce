package com.paul.ecomerce.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paul.ecomerce.dto.PageResponseDto;
import com.paul.ecomerce.dto.sale.SaleRequestDto;
import com.paul.ecomerce.dto.sale.SaleResponseDto;
import com.paul.ecomerce.dto.saledetail.SaleDetailRequestDto;
import com.paul.ecomerce.mapper.PaginationMapper;
import com.paul.ecomerce.mapper.SaleMapper;
import com.paul.ecomerce.model.entity.AppUser;
import com.paul.ecomerce.model.entity.ProductVariant;
import com.paul.ecomerce.model.entity.Sale;
import com.paul.ecomerce.repository.SaleRepository;
import com.paul.ecomerce.service.ProductVariantService;
import com.paul.ecomerce.service.SaleService;
import com.paul.ecomerce.service.validator.ProductVariantValidator;
import com.paul.ecomerce.service.validator.SaleValidator;
import com.paul.ecomerce.service.validator.UserValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final PaginationMapper paginationMapper;
    private final SaleMapper saleMapper;
    private final SaleValidator saleValidator;

    private final ProductVariantValidator productVariantValidator;
    private final ProductVariantService productVariantService;

    private final UserValidator userValidator;

    @Override
    public PageResponseDto<SaleResponseDto> getAllSales(Pageable pageable) {
        log.info("Obteniendo lista de ventas");
        Page<Sale> salePaged = saleRepository.findAll(pageable);
        return paginationMapper.toPageResponseDto(salePaged, saleMapper::toSaleResponseDto);
    }

    @Override
    public SaleResponseDto getSaleById(Long id) {
        log.info("Validando existencia del sale con id: {}", id);
        Sale sale = saleValidator.getSaleOrThrow(id);
        return saleMapper.toSaleResponseDto(sale);
    }

    @Transactional
    @Override
    public SaleResponseDto createSale(SaleRequestDto saleRequestDto) {
        log.info("Validando datos para crear la venta");
        saleValidator.validateForCreate(saleRequestDto);

        Sale sale = new Sale();

        AppUser user = userValidator.getUserOrThrow(saleRequestDto.userId());

        sale.setAppUser(user);

        for (SaleDetailRequestDto detailDto : saleRequestDto.saleDetails()) {

            ProductVariant productVariant = productVariantValidator
                    .geProductVariantOrThrow(detailDto.productVariantId());

                    sale.createDetail(productVariant, detailDto.quantity());

                    productVariantService.decreaseStock(productVariant, detailDto.quantity());
        }

        sale.updateTotals();
        saleRepository.save(sale);

        return saleMapper.toSaleResponseDto(sale);
    }

    @Override
    @Transactional
    public void deactivateSaleById(Long id) {
        log.info("Buscando si existe la venta con ID: " + id);
        Sale sale = saleValidator.getSaleOrThrow(id);
        sale.deactivate();
        saleRepository.save(sale);
    }

}
