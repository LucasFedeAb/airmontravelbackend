package com.airmont.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.airmont.models.entity.ProductAvailability;

@Repository
public interface ProductAvailabilityRepository extends JpaRepository<ProductAvailability, Long> {

    List<ProductAvailability> findByProductIdAndIsReservedFalse(Long productId);
    ProductAvailability findByProductIdAndAvailableDateAndIsReservedFalse(Long productId, LocalDate availableDate);
    @Query("SELECT pa FROM ProductAvailability pa WHERE pa.product.id = :productId AND pa.availableDate = :date")
    ProductAvailability findAvailabilityByProductIdAndDate(@Param("productId") Integer productId, @Param("date") String date);
}