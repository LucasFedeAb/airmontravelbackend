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

import com.airmont.models.entity.ImageHero;
import com.airmont.services.ImageHeroService;

@RestController
@RequestMapping("/imagenes-hero")
public class ImageHeroController {

    @Autowired
    private ImageHeroService imageHeroService;

    // Endpoint para obtener imágenes por categoría, ordenadas por número de orden
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ImageHero>> getImagesByCategory(@PathVariable String category) {
        List<ImageHero> images = imageHeroService.getImagesByCategory(category);
        return ResponseEntity.ok(images);
    }

    // Endpoint para obtener todas las imágenes
    @GetMapping("/")
    public ResponseEntity<List<ImageHero>> getAllImages() {
        List<ImageHero> images = imageHeroService.getAllImages();
        return ResponseEntity.ok(images);
    }

    // Endpoint para obtener todas las imágenes ordenadas por número de orden, sin importar la categoría
    @GetMapping("/order")
    public ResponseEntity<List<ImageHero>> getImagesOrderedByOrderNumber() {
        List<ImageHero> images = imageHeroService.getImagesOrderedByOrderNumber();
        return ResponseEntity.ok(images);
    }
    
    @PostMapping("/nueva-imagen")
    public ResponseEntity<ImageHero> createImageHero(@RequestBody ImageHero imageHero) {
        ImageHero createdImageHero = imageHeroService.createImageHero(imageHero);
        return ResponseEntity.ok(createdImageHero);
    }
    
    // Endpoint para actualizar una imagen existente
    @PutMapping(value="/{id}/actualizar", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ImageHero> updateImageHero(@PathVariable Long id, @RequestBody ImageHero updatedImageHero) {
        ImageHero updatedHero = imageHeroService.updateImageHero(id, updatedImageHero);
        if (updatedHero != null) {
			return new ResponseEntity<>(updatedImageHero, HttpStatus.OK); // Codigo 200
		} else {
			return new ResponseEntity<>(updatedImageHero, HttpStatus.NOT_FOUND); // Codigo 404
		}
    }
}
