package com.paul.ecomerce.dto;

import java.util.List;

public record PageResponseDto<T>(
    List<T> content,
    long totalElements,
    int pageNumber,
    int pageSize,
    int totalPages,
    boolean hasNext,
    boolean hasPrevious,
    boolean isFirst,
    boolean isLast
) {

}
