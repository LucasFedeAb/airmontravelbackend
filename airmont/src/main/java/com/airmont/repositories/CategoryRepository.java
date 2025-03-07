package com.airmont.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.airmont.models.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	List<Category> findByActiveTrue();
	Category findByName(String name);
	
	@Query("SELECT DISTINCT c FROM Category c " +
		       "LEFT JOIN c.subcategories s " +
		       "LEFT JOIN Product p ON p.category = c.name AND p.subcategory = s.name " +
		       "WHERE c.active = true " +
		       "AND (s IS NULL OR s.active = true)")
		List<Category> findCategoriesWithProductsOrEmptySubcategories();
}