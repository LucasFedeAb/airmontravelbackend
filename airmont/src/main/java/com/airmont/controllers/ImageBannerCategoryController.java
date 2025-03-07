package com.airmont.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airmont.models.entity.ImageBannerCategory;
import com.airmont.services.ImageBannerCategoryService;

@RestController
@RequestMapping("/imagenes-banner")
public class ImageBannerCategoryController {

    @Autowired
    private ImageBannerCategoryService imageBannerCategoryService;

    // Endpoint para obtener imágenes por categoría, ordenadas por número de orden
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ImageBannerCategory>> getImagesBannerByCategory(@PathVariable String category) {
        List<ImageBannerCategory> images = imageBannerCategoryService.getImagesBannerByCategory(category);
        return ResponseEntity.ok(images);
    }

    // Endpoint para obtener todas las imágenes
    @GetMapping("/")
    public ResponseEntity<List<ImageBannerCategory>> getAllImagesBanner() {
        List<ImageBannerCategory> images = imageBannerCategoryService.getAllImagesBanner();
        return ResponseEntity.ok(images);
    }

    // Endpoint para obtener todas las imágenes ordenadas por número de orden, sin importar la categoría
    @GetMapping("/order")
    public ResponseEntity<List<ImageBannerCategory>> getImagesBannerOrderedByOrderNumber() {
        List<ImageBannerCategory> images = imageBannerCategoryService.getImagesBannerOrderedByOrderNumber();
        return ResponseEntity.ok(images);
    }
    
    @PostMapping("/nueva-imagen")
    public ResponseEntity<ImageBannerCategory> createImageBannerCategory(@RequestBody ImageBannerCategory imageBannerCategory) {
    	ImageBannerCategory createdImageBannerCategory = imageBannerCategoryService.createImageBannerCategory(imageBannerCategory);
        return ResponseEntity.ok(createdImageBannerCategory);
    }
    
    // Endpoint para actualizar una imagen existente
    @PutMapping(value="/{id}/actualizar", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ImageBannerCategory> updateImageBannerCategory(@PathVariable Long id, @RequestBody ImageBannerCategory updatedImageBannerCategory) {
    	ImageBannerCategory updatedImageBanner = imageBannerCategoryService.updateImageBannerCategory(id, updatedImageBannerCategory);
        if (updatedImageBanner != null) {
			return new ResponseEntity<>(updatedImageBannerCategory, HttpStatus.OK); // Codigo 200
		} else {
			return new ResponseEntity<>(updatedImageBannerCategory, HttpStatus.NOT_FOUND); // Codigo 404
		}
    }
}