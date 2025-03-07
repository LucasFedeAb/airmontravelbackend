package com.airmont.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airmont.models.entity.TemporarySale;


@Repository
public interface TemporarySaleRepository extends JpaRepository <TemporarySale, Integer> {
	TemporarySale findByPreferenceId(String preferenceId);
}
