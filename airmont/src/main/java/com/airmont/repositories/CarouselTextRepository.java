package com.airmont.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airmont.models.entity.CarouselText;

@Repository
public interface CarouselTextRepository extends JpaRepository<CarouselText, Long> {
}
