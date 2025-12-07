package com.paul.ecomerce.dto.supplier;

public record SupplierResponseDto(
    Long id,
    String name,
    String email,
    String phone,
    String address,
    boolean enabled
) {

}
