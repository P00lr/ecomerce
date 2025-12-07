package com.paul.ecomerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paul.ecomerce.model.entity.Size;

public interface SizeRepository extends JpaRepository<Size, Long> {
    boolean existsByName(String name);
}
