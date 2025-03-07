package com.airmont.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airmont.models.entity.PaymentSettings;
import com.airmont.services.PaymentSettingsService;

@RestController
@RequestMapping("/api/payment-settings")
public class PaymentSettingsController {

    @Autowired
    private PaymentSettingsService paymentSettingsService;

    @GetMapping("/")
    public ResponseEntity<List<PaymentSettings>> getAll() {
        List<PaymentSettings> settings = paymentSettingsService.findAll();
        return new ResponseEntity<>(settings, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentSettings> getById(@PathVariable Long id) {
        Optional<PaymentSettings> setting = paymentSettingsService.findById(id);
        return setting.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody PaymentSettings paymentSettings) {
        // Verificar si ya existe una configuración
        List<PaymentSettings> existingSettings = paymentSettingsService.findAll();
        if (!existingSettings.isEmpty()) {
            return new ResponseEntity<>("Ya existe una configuración de pago. No se puede crear otra.", HttpStatus.BAD_REQUEST);
        }

        // Si no existe, guardar la nueva configuración
        PaymentSettings savedSetting = paymentSettingsService.save(paymentSettings);
        return new ResponseEntity<>(savedSetting, HttpStatus.CREATED);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<PaymentSettings> update(@PathVariable Long id, @RequestBody PaymentSettings paymentSettings) {
        Optional<PaymentSettings> existingSetting = paymentSettingsService.findById(id);
        if (existingSetting.isPresent()) {
        	paymentSettings.setId(id);
            PaymentSettings updatedSetting = paymentSettingsService.save(paymentSettings);
            return new ResponseEntity<>(updatedSetting, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//    	paymentSettingsService.deleteById(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
}