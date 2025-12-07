package com.paul.ecomerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paul.ecomerce.model.entity.Color;

public interface ColorRepository extends JpaRepository<Color, Long> {
    boolean existsByName(String name);
}
