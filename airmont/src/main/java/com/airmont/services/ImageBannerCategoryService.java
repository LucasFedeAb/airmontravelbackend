package com.airmont.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airmont.models.entity.ImageBannerCategory;
import com.airmont.repositories.ImageBannerCategoryRepository;

@Service
public class ImageBannerCategoryService {

    @Autowired
    private ImageBannerCategoryRepository imageBannerCategoryRepository;

    // Método para obtener imágenes según la categoría, ordenadas por su número de orden
    public List<ImageBannerCategory> getImagesBannerByCategory(String category) {
        return imageBannerCategoryRepository.findByCategoryOrderByOrderNumberAsc(category);
    }
    
    // Método para obtener todas las imágenes
    public List<ImageBannerCategory> getAllImagesBanner() {
        return imageBannerCategoryRepository.findAll();
    }

    // Método para obtener todas las imágenes ordenadas por número de orden
    public List<ImageBannerCategory> getImagesBannerOrderedByOrderNumber() {
        return imageBannerCategoryRepository.findAllByOrderByOrderNumberAsc();
    }
    
    // Crear Imagen
    public ImageBannerCategory createImageBannerCategory(ImageBannerCategory imageBannerCategory) {
        return imageBannerCategoryRepository.save(imageBannerCategory);
    }
    
    //Actualizar Imagen
    
    public ImageBannerCategory updateImageBannerCategory(Long id, ImageBannerCategory updatedImageBannerCategory) {
        Optional<ImageBannerCategory> existingImageBannerCategoryOpt = imageBannerCategoryRepository.findById(id);

        if (existingImageBannerCategoryOpt.isPresent()) {
        	ImageBannerCategory existingImageBannerCategory = existingImageBannerCategoryOpt.get();
        	existingImageBannerCategory.setUrl(updatedImageBannerCategory.getUrl());
        	existingImageBannerCategory.setOrderNumber(updatedImageBannerCategory.getOrderNumber());
        	existingImageBannerCategory.setCategory(updatedImageBannerCategory.getCategory());
        	existingImageBannerCategory.setSubCategory(updatedImageBannerCategory.getSubCategory());
            return imageBannerCategoryRepository.save(existingImageBannerCategory);
        } else {
            throw new RuntimeException("ImageBannerCategory not found with id: " + id);
        }
    }
}
