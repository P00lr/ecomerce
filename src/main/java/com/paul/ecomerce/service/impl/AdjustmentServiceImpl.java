package com.paul.ecomerce.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paul.ecomerce.dto.PageResponseDto;
import com.paul.ecomerce.dto.adjustment.AdjustmentRequestDto;
import com.paul.ecomerce.dto.adjustment.AdjustmentResponseDto;
import com.paul.ecomerce.dto.adjustmentdetail.AdjustmentDetailDto;
import com.paul.ecomerce.mapper.AdjustmentMapper;
import com.paul.ecomerce.mapper.PaginationMapper;
import com.paul.ecomerce.model.entity.Adjustment;
import com.paul.ecomerce.model.entity.AppUser;
import com.paul.ecomerce.model.entity.ProductVariant;
import com.paul.ecomerce.model.enums.AdjustmentType;
import com.paul.ecomerce.repository.AdjustmentRepository;
import com.paul.ecomerce.service.AdjustmentService;
import com.paul.ecomerce.service.ProductVariantService;
import com.paul.ecomerce.service.validator.AdjustmentValidator;
import com.paul.ecomerce.service.validator.ProductVariantValidator;
import com.paul.ecomerce.service.validator.UserValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdjustmentServiceImpl implements AdjustmentService {

    private final UserValidator userValidator;

    private final ProductVariantValidator productVariantValidator;
    private final ProductVariantService productVariantService;

    private final AdjustmentValidator adjustmentValidator;
    private final AdjustmentMapper adjustmentMapper;
    private final AdjustmentRepository adjustmentRepository;

    private final PaginationMapper paginationMapper;

    @Override
    public PageResponseDto<AdjustmentResponseDto> getAllAdjustmentPaged(Pageable pageable) {
        log.info("Verficando registro de ajustes");
        Page<Adjustment> adjustmentPaged = adjustmentRepository.findAll(pageable);
        return paginationMapper.toPageResponseDto(adjustmentPaged, adjustmentMapper::toAdjustmentResponseDto);
    }

    @Override
    public AdjustmentResponseDto getAdjustmentById(Long id) {
        log.info("Verificando existencia del ajuste con ID: " + id);
        Adjustment adjustment = adjustmentValidator.getAdjustmentOrThrow(id);
        return adjustmentMapper.toAdjustmentResponseDto(adjustment);
    }

    @Override
    @Transactional
    public AdjustmentResponseDto createAdjustment(AdjustmentRequestDto adjustmentDto) {
        log.info("Validando datos para crear el ajuste");
        adjustmentValidator.validateForCreate(adjustmentDto);

        Adjustment adjustment = new Adjustment();

        AppUser user = userValidator.getUserOrThrow(adjustmentDto.userId());
        adjustment.setUser(user);
        adjustment.setDescription(adjustmentDto.description());
        adjustment.setType(adjustmentDto.type());

        for (AdjustmentDetailDto detail : adjustmentDto.adjustmentDetails()) {
            ProductVariant productVariant = productVariantValidator.geProductVariantOrThrow(detail.productVariantId());

            adjustProductVariantStock(adjustmentDto.type(), productVariant, detail.quantity());

            adjustment.createDetail(productVariant, detail.quantity());
        }

        adjustmentRepository.save(adjustment);

        return adjustmentMapper.toAdjustmentResponseDto(adjustment);
    }

    @Transactional
    @Override
    public void deactivateAdjustmentById(Long id) {
        log.info("Verificando existencia de ajuste con ID: " + id);
        Adjustment adjustment = adjustmentValidator.getAdjustmentOrThrow(id);
        adjustment.deactivate();
        adjustmentRepository.save(adjustment);
    }

    // METODOS AUXILIARES
    private void adjustProductVariantStock(AdjustmentType type, ProductVariant productVariant, Integer quantity) {
        if (type == AdjustmentType.IN)
            productVariantService.increaseStock(productVariant, quantity);
        else if(type == AdjustmentType.OUT)
            productVariantService.decreaseStock(productVariant, quantity);
    }
}
