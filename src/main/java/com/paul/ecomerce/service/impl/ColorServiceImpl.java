package com.paul.ecomerce.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paul.ecomerce.dto.PageResponseDto;
import com.paul.ecomerce.dto.color.ColorRequestDto;
import com.paul.ecomerce.dto.color.ColorRequestUpdateDto;
import com.paul.ecomerce.dto.color.ColorResponseDto;
import com.paul.ecomerce.mapper.ColorMapper;
import com.paul.ecomerce.mapper.PaginationMapper;
import com.paul.ecomerce.model.entity.Color;
import com.paul.ecomerce.repository.ColorRepository;
import com.paul.ecomerce.service.ColorService;
import com.paul.ecomerce.service.validator.ColorValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService{

    private final ColorRepository colorRepository;
    private final PaginationMapper paginationMapper;
    private final ColorMapper colorMapper;
    private final ColorValidator colorValidator;

    @Override
    public PageResponseDto<ColorResponseDto> getAllColorsPage(Pageable pageable) {
        log.info("Verificando lista de colores");
        Page<Color> colorsPage = colorRepository.findAll(pageable);
        return paginationMapper.toPageResponseDto(colorsPage, colorMapper::entityToColorDto);
    }

    @Override
    public ColorResponseDto getColorById(Long id) {
        log.info("Verificando si existe el color con ID: " + id);
        Color color = colorValidator.getColorOrThrow(id);
        return colorMapper.entityToColorDto(color);
    }

    @Override
    @Transactional
    public ColorResponseDto createColor(ColorRequestDto colorDto) {
        log.info("Validando datos para crear el color");
        colorValidator.validateForCreate(colorDto);

        Color color = colorMapper.dtoToEntity(colorDto);
        colorRepository.save(color);

        return colorMapper.entityToColorDto(color);


    }

    @Override
    @Transactional
    public ColorResponseDto updateColor(Long id, ColorRequestUpdateDto colorDto) {
        log.info("Validando datos para actualizar el color");
        Color color = colorValidator.validateForUpdate(id, colorDto);
        color.updateFromDto(colorDto);

        colorRepository.save(color);

        return colorMapper.entityToColorDto(color);

    }

    @Override
    @Transactional
    public void deactivateColorById(Long id) {
        Color color = colorValidator.getColorOrThrow(id);
        colorValidator.validateDeactivation(color);

        color.setEnabled(false);

        colorRepository.save(color);

    }
    
}
