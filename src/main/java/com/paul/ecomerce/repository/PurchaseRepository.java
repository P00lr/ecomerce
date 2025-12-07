package com.paul.ecomerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paul.ecomerce.model.entity.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long>{

}
