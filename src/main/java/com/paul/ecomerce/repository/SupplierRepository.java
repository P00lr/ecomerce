package com.paul.ecomerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paul.ecomerce.model.entity.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    boolean existsByName(String name);
    boolean existsByAddress(String address);
}
