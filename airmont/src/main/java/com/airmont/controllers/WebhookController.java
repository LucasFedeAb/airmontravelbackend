package com.airmont.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airmont.models.entity.PaymentResponse;
import com.airmont.models.entity.TemporarySale;
import com.airmont.services.MercadoPagoService;
import com.airmont.services.TemporarySaleService;
import com.mercadopago.resources.merchantorder.MerchantOrder;
import com.mercadopago.resources.payment.Payment;

@RestController
@RequestMapping("/webhook")
public class WebhookController {
    
    @Autowired
    private MercadoPagoService mercadoPagoService;
    
    @Autowired
	TemporarySaleService temporarySaleService;
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    @Value("${mercadopago.access_token}")
    private String accessToken;
 

@PostMapping("/payment_details")
public ResponseEntity<String> recibirPaymentDetails(@RequestBody Map<String, Object> payload) {
    System.out.println("Webhook recibido: " + payload);
    String topic = (String) payload.get("topic");

    // Verificar si el "topic" es de un pago
    if ("payment".equals(topic)) {
        Object dataObject = payload.get("data");
        if (dataObject instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> data = (Map<String, Object>) dataObject;
            String paymentId = data.get("id").toString();
            System.out.println("Payment ID desde data: " + paymentId);
            
            try {
                // Obtener detalles del pago
                Payment paymentDetails = mercadoPagoService.obtenerDetallesPago(paymentId);
                if (paymentDetails != null) {
                    String status = paymentDetails.getStatus();
                    System.out.println("Estado del pago: " + paymentDetails.getStatus());
                    System.out.println("Monto del pago: " + paymentDetails.getTransactionAmount());
                    
                    // Obtener merchant_order_id del pago
                    String merchantOrderId = paymentDetails.getOrder().getId().toString();

                    // Obtener detalles de la orden del comerciante (merchant_order)
                    MerchantOrder merchantOrder = mercadoPagoService.obtenerDetallesOrden(merchantOrderId);
                    String preferenceId = merchantOrder.getPreferenceId();
                    System.out.println("Preference ID: " + preferenceId);

                    // Verificar si el pago fue aprobado
                    if ("approved".equals(status)) {
                        TemporarySale temporarySale = temporarySaleService.getTemporarySaleByPreferenceId(preferenceId);
                        if (temporarySale != null) {
                            temporarySale.setTotalSaleAmount(paymentDetails.getTransactionAmount());
                            temporarySale.setPaymentId(paymentId);
                            temporarySale.setPaymentStatus(status);
                            temporarySale.setOrderStatus("Creada-pendiente");
                            temporarySale.setPaymentCardId(paymentDetails.getPaymentMethodId());
                    		temporarySale.setPaymentCardType(paymentDetails.getPaymentTypeId());
                    		temporarySale.setPaymentCardName(paymentDetails.getCard().getCardholder().getName());
                            temporarySale.setPaymentCardIdentification(paymentDetails.getCard().getCardholder().getIdentification().getNumber());
                            temporarySale.setPaymentCardExpirationYear(paymentDetails.getCard().getExpirationYear());
                            temporarySale.setPaymentCardExpirationMonth(paymentDetails.getCard().getExpirationMonth());
                            temporarySale.setPaymentCardFirstNumbers(paymentDetails.getCard().getFirstSixDigits());
                            temporarySale.setPaymentCardLastNumbers(paymentDetails.getCard().getLastFourDigits());
                            temporarySale.setInstallments(paymentDetails.getInstallments());
                            temporarySale.setNetReceivedAmount(paymentDetails.getTransactionDetails().getNetReceivedAmount());
                            
                            temporarySaleService.createDefinitiveSaleWeb(temporarySale);
                            System.out.println("Venta creada con éxito.");
                            
                            // Notificar al frontend
                            messagingTemplate.convertAndSend("/topic/payment-status-mp", "approved");
                        } else {
                            System.out.println("No se encontró ninguna venta temporal para el Preference ID: " + preferenceId);
                        }
                    } else {
                    	// Crear y guardar el PaymentResponse
                        PaymentResponse paymentResponse = new PaymentResponse();
                        paymentResponse.setPreferenceId(preferenceId);
                        paymentResponse.setPaymentId(paymentId);
                        paymentResponse.setStatus(status);
                        //paymentResponseRepository.save(paymentResponse);
                        
                        messagingTemplate.convertAndSend("/topic/payment-status-mp", paymentResponse);
                        System.out.println("Pago rechazado.");
                    }
                } else {
                    System.out.println("No se obtuvieron detalles para el pago ID: " + paymentId);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error al obtener detalles del pago: " + e.getMessage());
            }
        } 
        
        // Manejo del caso cuando el payload contiene 'resource'
        else if (payload.containsKey("resource")) {
            String resourceUrl = (String) payload.get("resource");
            System.out.println("Recurso del pago: " + resourceUrl);
            
            // Verificar si es una URL completa o un paymentId directo
            if (resourceUrl.startsWith("https://")) {
                // Caso de URL completa
                if (resourceUrl.contains("/collections/notifications/")) {
                    String[] parts = resourceUrl.split("/");
                    String paymentId = parts[parts.length - 1];
                    System.out.println("Payment ID desde resource (URL): " + paymentId);
                    // Repetimos el mismo proceso para obtener los detalles del pago
                    try {
                        Payment paymentDetails = mercadoPagoService.obtenerDetallesPago(paymentId);
                        if (paymentDetails != null) {
                            String status = paymentDetails.getStatus();
                            System.out.println("Estado del pago: " + status);
                            String merchantOrderId = paymentDetails.getOrder().getId().toString();
                            MerchantOrder merchantOrder = mercadoPagoService.obtenerDetallesOrden(merchantOrderId);
                            String preferenceId = merchantOrder.getPreferenceId();
                            
                            
                            // Mismo flujo de creación de venta
                            if ("approved".equals(status)) {

                                TemporarySale temporarySale = temporarySaleService.getTemporarySaleByPreferenceId(preferenceId);
                                if (temporarySale != null) {
                                    temporarySale.setTotalSaleAmount(paymentDetails.getTransactionAmount());
                                    temporarySale.setPaymentId(paymentId);
                                    temporarySale.setPaymentStatus(status);
                                    temporarySale.setOrderStatus("Creada-pendiente");
                                    temporarySale.setPaymentCardId(paymentDetails.getPaymentMethodId());
                            		temporarySale.setPaymentCardType(paymentDetails.getPaymentTypeId());
                            		temporarySale.setPaymentCardName(paymentDetails.getCard().getCardholder().getName());
                                    temporarySale.setPaymentCardIdentification(paymentDetails.getCard().getCardholder().getIdentification().getNumber());
                                    temporarySale.setPaymentCardExpirationYear(paymentDetails.getCard().getExpirationYear());
                                    temporarySale.setPaymentCardExpirationMonth(paymentDetails.getCard().getExpirationMonth());
                                    temporarySale.setPaymentCardFirstNumbers(paymentDetails.getCard().getFirstSixDigits());
                                    temporarySale.setPaymentCardLastNumbers(paymentDetails.getCard().getLastFourDigits());
                                    temporarySale.setInstallments(paymentDetails.getInstallments());
                                    temporarySale.setNetReceivedAmount(paymentDetails.getTransactionDetails().getNetReceivedAmount());
                                    
                                    temporarySaleService.createDefinitiveSaleWeb(temporarySale);
                                    System.out.println("Venta creada con éxito.");

                                    // Notificar al frontend
                                    messagingTemplate.convertAndSend("/topic/payment-status-mp", "approved");
                                } else {
                                    System.out.println("No se encontró ninguna venta temporal para el Preference ID: " + preferenceId);
                                }
                            } else {
                            	// Crear y guardar el PaymentResponse
                                PaymentResponse paymentResponse = new PaymentResponse();
                                paymentResponse.setPreferenceId(preferenceId);
                                paymentResponse.setPaymentId(paymentId);
                                paymentResponse.setStatus(paymentDetails.getStatus());
                            	messagingTemplate.convertAndSend("/topic/payment-status-mp", paymentResponse);
                            	System.out.println("paymentResponse:" + paymentResponse);
                                System.out.println("Pago rechazado.");
                            }
                        } else {
                            System.out.println("No se obtuvieron detalles del pago para ID: " + paymentId);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Error al obtener detalles del pago: " + e.getMessage());
                    }
                } else {
                    System.out.println("La URL recibida no contiene /collections/notifications/");
                }
            } else {
                // Caso de ID de pago directo
                String paymentId = resourceUrl;
                System.out.println("Payment ID desde resource (ID directo): " + paymentId);
                
                // Repetimos el mismo proceso para obtener los detalles del pago
                try {
                    Payment paymentDetails = mercadoPagoService.obtenerDetallesPago(paymentId);
                    if (paymentDetails != null) {
                        String status = paymentDetails.getStatus();
                        System.out.println("Estado del pago: " + status);
                        String merchantOrderId = paymentDetails.getOrder().getId().toString();
                        MerchantOrder merchantOrder = mercadoPagoService.obtenerDetallesOrden(merchantOrderId);
                        String preferenceId = merchantOrder.getPreferenceId();
                      
                        // Mismo flujo de creación de venta
                        if ("approved".equals(status)) {

                            TemporarySale temporarySale = temporarySaleService.getTemporarySaleByPreferenceId(preferenceId);
                            if (temporarySale != null) {
                                temporarySale.setTotalSaleAmount(paymentDetails.getTransactionAmount());
                                temporarySale.setPaymentId(paymentId);
                                temporarySale.setPaymentStatus(status);
                                temporarySale.setOrderStatus("Creada-pendiente");
                                temporarySale.setPaymentCardId(paymentDetails.getPaymentMethodId());
                        		temporarySale.setPaymentCardType(paymentDetails.getPaymentTypeId());
                        		temporarySale.setPaymentCardName(paymentDetails.getCard().getCardholder().getName());
                                temporarySale.setPaymentCardIdentification(paymentDetails.getCard().getCardholder().getIdentification().getNumber());
                                temporarySale.setPaymentCardExpirationYear(paymentDetails.getCard().getExpirationYear());
                                temporarySale.setPaymentCardExpirationMonth(paymentDetails.getCard().getExpirationMonth());
                                temporarySale.setPaymentCardFirstNumbers(paymentDetails.getCard().getFirstSixDigits());
                                temporarySale.setPaymentCardLastNumbers(paymentDetails.getCard().getLastFourDigits());
                                temporarySale.setInstallments(paymentDetails.getInstallments());
                                temporarySale.setNetReceivedAmount(paymentDetails.getTransactionDetails().getNetReceivedAmount());
                                
                                temporarySaleService.createDefinitiveSaleWeb(temporarySale);
                                System.out.println("Venta creada con éxito.");

                                // Notificar al frontend
                                messagingTemplate.convertAndSend("/topic/payment-status-mp", "approved");
                            } else {
                                System.out.println("No se encontró ninguna venta temporal para el Preference ID: " + preferenceId);
                            }
                        } else {
                        	// Crear y guardar el PaymentResponse
                            PaymentResponse paymentResponse = new PaymentResponse();
                            paymentResponse.setPreferenceId(preferenceId);
                            paymentResponse.setPaymentId(paymentId);
                            paymentResponse.setStatus(paymentDetails.getStatus());
                            
                        	messagingTemplate.convertAndSend("/topic/payment-status-mp", paymentResponse);
                            System.out.println("Pago rechazado.");
                        }
                    } else {
                        System.out.println("No se obtuvieron detalles del pago para ID: " + paymentId);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error al obtener detalles del pago: " + e.getMessage());
                }
            }
        }
    } 
    
    // Caso de notificación de orden del comerciante
    else if ("merchant_order".equals(topic)) {
        System.out.println("Notificación de orden de comerciante recibida");
    }

    return ResponseEntity.ok("Webhook recibido con éxito");
	}

}



