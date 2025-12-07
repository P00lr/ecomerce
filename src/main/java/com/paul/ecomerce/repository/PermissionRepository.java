package com.paul.ecomerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paul.ecomerce.model.entity.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    boolean existsByName(String name);
}
