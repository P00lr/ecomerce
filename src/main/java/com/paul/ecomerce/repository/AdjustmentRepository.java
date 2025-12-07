package com.paul.ecomerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paul.ecomerce.model.entity.Adjustment;

public interface AdjustmentRepository extends JpaRepository<Adjustment, Long> {

}
