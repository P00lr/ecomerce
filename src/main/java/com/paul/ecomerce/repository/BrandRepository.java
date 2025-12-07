package com.paul.ecomerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paul.ecomerce.model.entity.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    boolean existsByName(String name);
    boolean existsById(Long id);
}
