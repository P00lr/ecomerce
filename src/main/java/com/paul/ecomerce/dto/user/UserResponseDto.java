package com.paul.ecomerce.dto.user;

public record UserResponseDto(
    Long id,
    String name,
    String username,
    String email,
    boolean enabled
) {

}
