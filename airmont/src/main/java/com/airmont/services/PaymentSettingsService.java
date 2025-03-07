package com.airmont.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airmont.models.entity.PaymentSettings;
import com.airmont.repositories.PaymentSettingsRepository;

@Service
public class PaymentSettingsService {

    @Autowired
    private PaymentSettingsRepository paymentSettingsRepository;

    public List<PaymentSettings> findAll() {
        return paymentSettingsRepository.findAll();
    }

    public Optional<PaymentSettings> findById(Long id) {
        return paymentSettingsRepository.findById(id);
    }
    
 // Obtener descuentos por método de pago
    public PaymentSettings getPaymentSettings() {
        return paymentSettingsRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Terminales de Pago no configurados"));
    }

    public PaymentSettings save(PaymentSettings mercadoPagoSettings) {
        return paymentSettingsRepository.save(mercadoPagoSettings);
    }

    public void deleteById(Long id) {
    	paymentSettingsRepository.deleteById(id);
    }
}
