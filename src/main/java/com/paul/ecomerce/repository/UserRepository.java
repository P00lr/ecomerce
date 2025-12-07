package com.paul.ecomerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paul.ecomerce.model.entity.AppUser;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<AppUser> findByUsername(String username);
}
