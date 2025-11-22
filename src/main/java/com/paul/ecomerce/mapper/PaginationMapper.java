package com.paul.ecomerce.mapper;

import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.paul.ecomerce.dto.PageResponseDto;

@Component
public class PaginationMapper {

    public <T, R> PageResponseDto<R> toPageResponseDto(Page<T> page, Function<T, R> mapper) {
        List<R> content = page.getContent().stream().map(mapper).toList();

        return new PageResponseDto<>(
                content,
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getSize(),
                page.hasNext(),
                page.hasPrevious(),
                page.isFirst(),
                page.isLast()
            );
    }
}
