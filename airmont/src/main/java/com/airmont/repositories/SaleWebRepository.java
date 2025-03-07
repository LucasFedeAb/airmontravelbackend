package com.airmont.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.airmont.models.entity.SaleWeb;

@Repository
public interface SaleWebRepository extends JpaRepository <SaleWeb, Integer> {
	List<SaleWeb> findByDniClient(Integer dniClient);
	SaleWeb findByCodeSale(String codeSale);
	SaleWeb findByPaymentId(String codeSale);
	void deleteByCodeSale(String codeSale);
	
	@Query("SELECT s FROM SaleWeb s WHERE STR_TO_DATE(s.dateSale, '%d-%m-%y %H:%i:%s') BETWEEN ?1 AND ?2")
    List<SaleWeb> findByDateSaleBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT s FROM SaleWeb s JOIN s.items i WHERE STR_TO_DATE(i.reservedDate, '%d/%m/%Y') BETWEEN ?1 AND ?2")
    List<SaleWeb> findByReservedDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("SELECT s FROM SaleWeb s JOIN s.items i WHERE STR_TO_DATE(i.reservedDate, '%d/%m/%Y') BETWEEN ?1 AND ?2")
    List<SaleWeb> findReservationsByCurrentMonth(LocalDateTime startDate, LocalDateTime endDate);
}