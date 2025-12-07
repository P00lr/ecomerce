package com.paul.ecomerce.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paul.ecomerce.dto.PageResponseDto;
import com.paul.ecomerce.dto.size.SizeRequestDto;
import com.paul.ecomerce.dto.size.SizeRequestUpdateDto;
import com.paul.ecomerce.dto.size.SizeResponseDto;
import com.paul.ecomerce.mapper.SizeMapper;
import com.paul.ecomerce.mapper.PaginationMapper;
import com.paul.ecomerce.model.entity.Size;
import com.paul.ecomerce.repository.SizeRepository;
import com.paul.ecomerce.service.SizeService;
import com.paul.ecomerce.service.validator.SizeValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SizeServiceImpl implements SizeService{

    private final SizeRepository sizeRepository;
    private final PaginationMapper paginationMapper;
    private final SizeMapper sizeMapper;
    private final SizeValidator sizeValidator;

    @Override
    public PageResponseDto<SizeResponseDto> getAllSizesPage(Pageable pageable) {
        log.info("Verificando lista de tallas");
        Page<Size> sizesPage = sizeRepository.findAll(pageable);
        return paginationMapper.toPageResponseDto(sizesPage, sizeMapper::entityToSizeDto);
    }

    @Override
    public SizeResponseDto getSizeById(Long id) {
        log.info("Verificando si existe la talla con ID: " + id);
        Size size = sizeValidator.getSizeOrThrow(id);
        return sizeMapper.entityToSizeDto(size);
    }

    @Override
    @Transactional
    public SizeResponseDto createSize(SizeRequestDto sizeDto) {
        log.info("Validando datos para crear la talla");
        sizeValidator.validateForCreate(sizeDto);

        Size size = sizeMapper.dtoToEntity(sizeDto);
        sizeRepository.save(size);

        return sizeMapper.entityToSizeDto(size);


    }

    @Override
    @Transactional
    public SizeResponseDto updateSize(Long id, SizeRequestUpdateDto sizeDto) {
        log.info("Validando datos para actualizar la talla");
        Size size = sizeValidator.validateForUpdate(id, sizeDto);
        size.updateFromDto(sizeDto);

        sizeRepository.save(size);

        return sizeMapper.entityToSizeDto(size);

    }

    @Override
    @Transactional
    public void deactivateSizeById(Long id) {
        Size size = sizeValidator.getSizeOrThrow(id);
        sizeValidator.validateDeactivation(size);

        size.setEnabled(false);

        sizeRepository.save(size);

    }
    
}
