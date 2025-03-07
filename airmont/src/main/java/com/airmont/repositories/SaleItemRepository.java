package com.airmont.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.airmont.models.entity.SaleItem;
import com.airmont.models.entity.SaleWeb;

@Repository
public interface SaleItemRepository extends JpaRepository<SaleItem, Integer> {
    @Query("SELECT COUNT(si) > 0 FROM SaleItem si WHERE si.productId = :productId AND si.reservedDate = :reservedDate")
    boolean existsByProductIdAndReservedDate(@Param("productId") Integer productId, 
                                             @Param("reservedDate") String reservedDate);
    
    @Query("SELECT COUNT(si) > 0 FROM SaleItem si WHERE si.reservedDate = :reservedDate")
    boolean existsByReservedDate(@Param("reservedDate") String reservedDate);
    
    @Query("SELECT s FROM SaleWeb s JOIN s.items i WHERE STR_TO_DATE(i.reservedDate, '%d/%m/%Y') BETWEEN ?1 AND ?2")
    List<SaleWeb> findReservationsByCurrentMonth(LocalDateTime startDate, LocalDateTime endDate);
}

