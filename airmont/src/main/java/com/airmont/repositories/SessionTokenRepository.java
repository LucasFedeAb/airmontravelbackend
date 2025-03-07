package com.airmont.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airmont.models.entity.AdminUser;
import com.airmont.models.entity.SessionToken;

public interface SessionTokenRepository extends JpaRepository<SessionToken, Long> {
    Optional<SessionToken> findByToken(String token);
    void deleteByAdminUser(AdminUser adminUser);
    Optional<SessionToken> findFirstByOrderByCreatedAtDesc();
}
