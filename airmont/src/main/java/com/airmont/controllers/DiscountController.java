package com.airmont.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airmont.models.entity.DiscountCode;
import com.airmont.models.entity.PaymentDiscountStore;
import com.airmont.services.DiscountService;

@RestController
@RequestMapping("/descuentos")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    // Obtener descuentos por método de pago
    @GetMapping("/payment")
    public ResponseEntity<PaymentDiscountStore> getPaymentDiscounts() {
        PaymentDiscountStore discounts = discountService.getPaymentDiscounts();
        return ResponseEntity.ok(discounts);
    }
    
    
 // Crear descuentos por método de pago
    @PostMapping("/payment/crear")
    public ResponseEntity<PaymentDiscountStore> createPaymentDiscounts(@RequestBody PaymentDiscountStore discount) {
        PaymentDiscountStore newDiscount = discountService.createPaymentDiscounts(discount);
        return ResponseEntity.ok(newDiscount);
    }

    // Actualizar descuentos por método de pago
    @PutMapping("/payment/actualizar")
    public ResponseEntity<PaymentDiscountStore> updatePaymentDiscounts(@RequestBody PaymentDiscountStore discount) {
        PaymentDiscountStore updatedDiscount = discountService.updatePaymentDiscounts(discount);
        return ResponseEntity.ok(updatedDiscount);
    }
    
    
    // Obtener descuentos por método de pago
    @GetMapping("/")
    public ResponseEntity<List<DiscountCode>> getAllCodesDiscounts() {
    	List<DiscountCode> codesDiscounts = discountService.getAllCodesDiscounts();
    	return ResponseEntity.ok(codesDiscounts);
    }

    // Validar código de descuento
    @PostMapping("/validate")
    public ResponseEntity<DiscountCode> validateDiscountCodeInClient(@RequestBody Map<String, String> codeMap) {
        DiscountCode discount = discountService.validateDiscountCodeInClient(codeMap.get("code"));
        return ResponseEntity.ok(discount);
    }

    // Agregar código de descuento
    @PostMapping("/code/crear")
    public ResponseEntity<DiscountCode> addDiscountCode(@RequestBody DiscountCode discountCode) {
        DiscountCode newCode = discountService.addDiscountCode(discountCode);
        return ResponseEntity.ok(newCode);
    }

    // Editar código de descuento
    @PutMapping("/code/{id}/actualizar")
    public ResponseEntity<DiscountCode> updateDiscountCode(@PathVariable Long id, @RequestBody DiscountCode discountCode) {
        DiscountCode updatedCode = discountService.updateDiscountCode(id, discountCode);
        return ResponseEntity.ok(updatedCode);
    }
    
 // Endpoint para obtener un código de descuento por ID
    @GetMapping("/code/{id}")
    public ResponseEntity<DiscountCode> getDiscountCodeById(@PathVariable Long id) {
        DiscountCode discountCode = discountService.getDiscountCodeById(id);
        return ResponseEntity.ok(discountCode);
    }

    // Endpoint para eliminar un código de descuento por ID
    @DeleteMapping("/code/{id}/eliminar")
    public ResponseEntity<Void> deleteDiscountCodeById(@PathVariable Long id) {
        discountService.deleteDiscountCodeById(id);
        return ResponseEntity.noContent().build(); // 204 No Content para respuestas exitosas sin cuerpo
    }
}
