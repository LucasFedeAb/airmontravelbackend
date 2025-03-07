package com.airmont.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airmont.models.entity.PaymentSettings;

@Repository
public interface PaymentSettingsRepository extends JpaRepository <PaymentSettings, Long>{

}
