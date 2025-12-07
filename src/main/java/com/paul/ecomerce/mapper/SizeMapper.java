package com.paul.ecomerce.mapper;

import org.springframework.stereotype.Component;

import com.paul.ecomerce.dto.size.SizeRequestDto;
import com.paul.ecomerce.dto.size.SizeResponseDto;
import com.paul.ecomerce.model.entity.Size;

@Component
public class SizeMapper {

    public SizeResponseDto entityToSizeDto(Size size) {
        return new SizeResponseDto(
            size.getId(),
            size.getName(),
            size.isEnabled()
        );
    }

    public Size dtoToEntity(SizeRequestDto sizeDto) {
        Size size = new Size();
        size.setName(sizeDto.name());

        return size;
    }
}
