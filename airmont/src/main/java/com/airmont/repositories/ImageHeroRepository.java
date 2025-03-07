package com.airmont.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airmont.models.entity.ImageHero;

@Repository
public interface ImageHeroRepository extends JpaRepository<ImageHero, Long> {
    
    // Método para obtener las imágenes por categoría, ordenadas por el número de orden
    List<ImageHero> findByCategoryOrderByOrderNumberAsc(String category);

    // Método para obtener todas las imágenes ordenadas por el número de orden
    List<ImageHero> findAllByOrderByOrderNumberAsc();
}
