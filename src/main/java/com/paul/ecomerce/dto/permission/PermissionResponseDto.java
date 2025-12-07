package com.paul.ecomerce.dto.permission;

public record PermissionResponseDto(
    Long id,
    String name,
    boolean enabled
) {

}
