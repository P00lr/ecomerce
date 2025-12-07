package com.paul.ecomerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paul.ecomerce.model.entity.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

}
