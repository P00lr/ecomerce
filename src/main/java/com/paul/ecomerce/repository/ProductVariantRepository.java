package com.paul.ecomerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paul.ecomerce.model.entity.ProductVariant;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long>{
    boolean existsBySku(String sku);
}
