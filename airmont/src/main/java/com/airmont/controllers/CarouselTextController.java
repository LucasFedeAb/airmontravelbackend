package com.airmont.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airmont.models.entity.CarouselText;
import com.airmont.services.CarouselTextService;

@RestController
@RequestMapping("/carousel-texts")
public class CarouselTextController {

    @Autowired
    private CarouselTextService carouselTextService;

    // Obtener todos los textos del carrusel (HTTP 200 OK)
    @GetMapping("/")
    public ResponseEntity<List<CarouselText>> getAllTexts() {
        List<CarouselText> texts = carouselTextService.getAllTexts();
        return ResponseEntity.ok(texts); // 200 OK
    }

    // Obtener un texto por ID (HTTP 200 OK o 404 Not Found)
    @GetMapping("/{id}")
    public ResponseEntity<CarouselText> getTextById(@PathVariable Long id) {
        Optional<CarouselText> text = carouselTextService.getTextById(id);
        if (text.isPresent()) {
            return ResponseEntity.ok(text.get()); // 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        }
    }

    // Crear un nuevo texto del carrusel (HTTP 201 Created o 500 Internal Server Error)
    @PostMapping("/crear")
    public ResponseEntity<CarouselText> createText(@RequestBody CarouselText carouselText) {
        try {
            CarouselText createdText = carouselTextService.createText(carouselText);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdText); // 201 Created
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }

    // Actualizar un texto existente (HTTP 200 OK o 404 Not Found o 500 Internal Server Error)
    @PutMapping("/{id}/actualizar")
    public ResponseEntity<CarouselText> updateText(@PathVariable Long id, @RequestBody CarouselText carouselText) {
        try {
            CarouselText updatedText = carouselTextService.updateText(id, carouselText);
            return ResponseEntity.ok(updatedText); // 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }

    // Eliminar un texto por ID (HTTP 204 No Content o 404 Not Found)
    @DeleteMapping("/{id}/eliminar")
    public ResponseEntity<Void> deleteText(@PathVariable Long id) {
        try {
            carouselTextService.deleteText(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }
}
