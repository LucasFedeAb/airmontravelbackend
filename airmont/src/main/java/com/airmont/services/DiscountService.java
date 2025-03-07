package com.airmont.services;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airmont.models.entity.DiscountCode;
import com.airmont.models.entity.PaymentDiscountStore;
import com.airmont.repositories.DiscountCodeRepository;
import com.airmont.repositories.PaymentDiscountStoreRepository;

@Service
public class DiscountService {

    @Autowired
    private PaymentDiscountStoreRepository paymentDiscountRepository;

    @Autowired
    private DiscountCodeRepository discountCodeRepository;
    

    // Obtener descuentos por método de pago
    public PaymentDiscountStore getPaymentDiscounts() {
        return paymentDiscountRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Descuentos no configurados"));
    }
    
    //Crear descuentos por método de pago
    public PaymentDiscountStore createPaymentDiscounts(PaymentDiscountStore discount) {
        return paymentDiscountRepository.save(discount);
    }

    // Actualizar descuentos por método de pago
    public PaymentDiscountStore updatePaymentDiscounts(PaymentDiscountStore discount) {
    	PaymentDiscountStore existingDiscount = paymentDiscountRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Descuentos no configurados"));
        existingDiscount.setDiscountForTransfer(discount.getDiscountForTransfer());
        existingDiscount.setDiscountForCash(discount.getDiscountForCash());
        existingDiscount.setBonusAmount(discount.getBonusAmount());
        existingDiscount.setInstallments(discount.getInstallments());
        return paymentDiscountRepository.save(existingDiscount);
    }
    
 // Obtener código de descuento por ID
    public DiscountCode getDiscountCodeById(Long id) {
        return discountCodeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Código de descuento no encontrado"));
    }
    
    public DiscountCode validateDiscountCodeInClient(String code) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires")); // Ajustar a la zona horaria local
        return discountCodeRepository.findByCode(code)
                .filter(discount -> {
                    // Obtener la fecha de expiración y ajustarla a las 23:59:59 del día
                    LocalDateTime expiryDateTime = discount.getExpiryDate().atTime(LocalTime.MAX); // Esto ajusta a las 23:59:59
                    // Verificar que el código no esté vencido y que haya cantidad disponible
                    return (expiryDateTime.isAfter(now) || expiryDateTime.isEqual(now)) && discount.getQuantity() > 0;
                })
                .orElseThrow(() -> new RuntimeException("Código de descuento inválido, agotado o expirado"));
    }
    
    public DiscountCode validateDiscountCode(String code) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires"));
        
        DiscountCode discountCode = discountCodeRepository.findByCode(code)
                .filter(discount -> {
                    LocalDateTime expiryDateTime = discount.getExpiryDate().atTime(LocalTime.MAX);
                    return (expiryDateTime.isAfter(now) || expiryDateTime.isEqual(now)) && discount.getQuantity() > 0;
                })
                .orElseThrow(() -> new RuntimeException("Código de descuento inválido, agotado o expirado"));
        
        // Decrementar la cantidad y guardar los cambios
        discountCode.setQuantity(discountCode.getQuantity() - 1);
        discountCodeRepository.save(discountCode);
        
        return discountCode;
    }
    
    // Agregar nuevo código de descuento
    public DiscountCode addDiscountCode(DiscountCode discountCode) {
        return discountCodeRepository.save(discountCode);
    }
    
    // Obtener todos los codigos de descuento
    public List<DiscountCode> getAllCodesDiscounts() {
		return discountCodeRepository.findAll();
	}
	
    // Editar código de descuento existente
    public DiscountCode updateDiscountCode(Long id, DiscountCode discountCode) {
        DiscountCode existingCode = discountCodeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Código de descuento no encontrado"));
        existingCode.setCode(discountCode.getCode());
        existingCode.setPercentage(discountCode.getPercentage());
        existingCode.setExpiryDate(discountCode.getExpiryDate());
        existingCode.setQuantity(discountCode.getQuantity());
        return discountCodeRepository.save(existingCode);
    }
    
 // Eliminar código de descuento por ID
    public void deleteDiscountCodeById(Long id) {
        DiscountCode existingCode = discountCodeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Código de descuento no encontrado"));
        discountCodeRepository.delete(existingCode);
    }
}
