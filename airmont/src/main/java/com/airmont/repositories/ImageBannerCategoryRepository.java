package com.airmont.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airmont.models.entity.ImageBannerCategory;

@Repository
public interface ImageBannerCategoryRepository extends JpaRepository<ImageBannerCategory, Long> {
    
    // Método para obtener las imágenes por categoría, ordenadas por el número de orden
    List<ImageBannerCategory> findByCategoryOrderByOrderNumberAsc(String category);

    // Método para obtener todas las imágenes ordenadas por el número de orden
    List<ImageBannerCategory> findAllByOrderByOrderNumberAsc();
}
