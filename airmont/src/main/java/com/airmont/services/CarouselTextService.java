package com.airmont.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airmont.models.entity.CarouselText;
import com.airmont.repositories.CarouselTextRepository;

@Service
public class CarouselTextService {

    @Autowired
    private CarouselTextRepository carouselTextRepository;

    public List<CarouselText> getAllTexts() {
        return carouselTextRepository.findAll();
    }

    public Optional<CarouselText> getTextById(Long id) {
        return carouselTextRepository.findById(id);
    }

    public CarouselText createText(CarouselText text) {
        return carouselTextRepository.save(text);
    }

    public CarouselText updateText(Long id, CarouselText updatedText) {
        return carouselTextRepository.findById(id)
                .map(existingText -> {
                    existingText.setText(updatedText.getText());
                    return carouselTextRepository.save(existingText);
                })
                .orElseThrow(() -> new RuntimeException("Text not found with id " + id));
    }

    public void deleteText(Long id) {
        carouselTextRepository.deleteById(id);
    }
}