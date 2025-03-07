package com.airmont.repositories;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.airmont.models.entity.Product;
import com.airmont.models.entity.ProductAvailability;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	Product findByCode (Integer code);
    boolean existsByCode(Integer code);
    Page<Product> findByCategory(String category, Pageable pageable);
    Page<Product> findByIsPromotion(Boolean isPromotion, Pageable pageable);
    Page<Product> findByNewEntry(Boolean newEntry, Pageable pageable);
    Page<Product> findByFeatured(Boolean isFeatured, Pageable pageable);
    Page<Product> findByCategoryAndSubcategory(String category, String subcategory, Pageable pageable);
    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.category) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.subcategory) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.tourType) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.location) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.origin) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.destination) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.days) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.searchTag) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.season) LIKE LOWER(CONCAT('%', :keyword, '%'))")
     Page<Product> searchProducts(@Param("keyword") String keyword, Pageable pageable);
    
    @Query("SELECT pa FROM ProductAvailability pa WHERE pa.product.id = :productId AND pa.availableDate = :date")
    ProductAvailability findAvailabilityByProductIdAndDate(@Param("productId") Integer productId, @Param("date") LocalDate date);

}

