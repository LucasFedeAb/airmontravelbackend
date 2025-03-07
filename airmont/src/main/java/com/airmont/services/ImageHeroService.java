package com.airmont.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airmont.models.entity.ImageHero;
import com.airmont.repositories.ImageHeroRepository;

@Service
public class ImageHeroService {

    @Autowired
    private ImageHeroRepository imageHeroRepository;

    // Método para obtener imágenes según la categoría, ordenadas por su número de orden
    public List<ImageHero> getImagesByCategory(String category) {
        return imageHeroRepository.findByCategoryOrderByOrderNumberAsc(category);
    }
    
    // Método para obtener todas las imágenes
    public List<ImageHero> getAllImages() {
        return imageHeroRepository.findAll();
    }

    // Método para obtener todas las imágenes ordenadas por número de orden
    public List<ImageHero> getImagesOrderedByOrderNumber() {
        return imageHeroRepository.findAllByOrderByOrderNumberAsc();
    }
    
    // Crear Imagen
    public ImageHero createImageHero(ImageHero imageHero) {
        return imageHeroRepository.save(imageHero);
    }
    
    //Actualizar Imagen
    
    public ImageHero updateImageHero(Long id, ImageHero updatedImageHero) {
        Optional<ImageHero> existingImageHeroOpt = imageHeroRepository.findById(id);

        if (existingImageHeroOpt.isPresent()) {
            ImageHero existingImageHero = existingImageHeroOpt.get();
            existingImageHero.setUrlWeb(updatedImageHero.getUrlWeb());
            existingImageHero.setUrlMobile(updatedImageHero.getUrlMobile());
            existingImageHero.setOrderNumber(updatedImageHero.getOrderNumber());
            existingImageHero.setCategory(updatedImageHero.getCategory());
            return imageHeroRepository.save(existingImageHero);
        } else {
            throw new RuntimeException("ImageHero not found with id: " + id);
        }
    }
}
