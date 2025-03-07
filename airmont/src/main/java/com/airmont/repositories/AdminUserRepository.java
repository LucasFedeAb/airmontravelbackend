package com.airmont.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airmont.models.entity.AdminUser;

public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
    Optional<AdminUser> findByUsername(String username);
    Optional<AdminUser> findByEmail(String email);
}
