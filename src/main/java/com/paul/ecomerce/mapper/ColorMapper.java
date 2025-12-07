package com.paul.ecomerce.mapper;

import org.springframework.stereotype.Component;

import com.paul.ecomerce.dto.color.ColorRequestDto;
import com.paul.ecomerce.dto.color.ColorResponseDto;
import com.paul.ecomerce.model.entity.Color;

@Component
public class ColorMapper {

    public ColorResponseDto entityToColorDto(Color color) {
        return new ColorResponseDto(
            color.getId(),
            color.getName(),
            color.isEnabled()
        );
    }

    public Color dtoToEntity(ColorRequestDto colorDto) {
        Color color = new Color();
        color.setName(colorDto.name());

        return color;
    }
}
