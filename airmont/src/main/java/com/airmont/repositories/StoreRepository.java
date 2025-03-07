package com.airmont.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airmont.models.entity.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
}
