package com.airmont.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airmont.models.entity.PaymentDiscountStore;

@Repository
public interface PaymentDiscountStoreRepository extends JpaRepository<PaymentDiscountStore, Long> {
	
}
