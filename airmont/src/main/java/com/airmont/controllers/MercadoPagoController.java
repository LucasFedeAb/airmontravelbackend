package com.airmont.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airmont.dto.ItemSaleDTO;
import com.airmont.dto.SaleWebDTO;
import com.airmont.models.entity.MercadoPago;
import com.airmont.models.entity.SaleItem;
import com.airmont.models.entity.TemporarySale;
import com.airmont.repositories.TemporarySaleRepository;
import com.airmont.services.MercadoPagoService;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;

@RestController
@RequestMapping("/api/pagos")
public class MercadoPagoController {

    @Autowired
    private MercadoPagoService mercadoPagoService;
    
    @Autowired
	TemporarySaleRepository temporarySaleRepository;
    
    @PostMapping("/crear")
    public ResponseEntity<?> crearPago(@RequestBody MercadoPago mercadoPago) {
        try {
            // Solo generar la preferencia sin guardar en la base de datos
//            Preference preference = mercadoPagoService.crearPago(mercadoPago);
//            return ResponseEntity.ok(Map.of("preferenceId", preference.getId()));
//        	String preferenceId = mercadoPagoService.crearPago(mercadoPago);
        	Preference preference = mercadoPagoService.crearPago(mercadoPago);
//        	String preferenceId = preference.getId();
        	return ResponseEntity.ok(Map.of("preferenceId", preference));  // Devolver un JSON con el preferenceId
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al crear el pago: " + e.getMessage());
        }
    }
    @PostMapping("/crear-pago")
    public ResponseEntity<?> crearPagoyVenta(@RequestBody SaleWebDTO saleWebDTO) {
    	System.out.println("Llamada a endpoint controller mp");
    	try {
    		// Solo generar la preferencia sin guardar en la base de datos
//            Preference preference = mercadoPagoService.crearPago(mercadoPago);
//            return ResponseEntity.ok(Map.of("preferenceId", preference.getId()));
//        	String preferenceId = mercadoPagoService.crearPago(mercadoPago);
    		System.out.println("Entro en error en controller mp inicial");
    		Preference preference = mercadoPagoService.crearPagoyVenta(saleWebDTO);
   		
    		TemporarySale temporarySale = new TemporarySale();
    		temporarySale.setPreferenceId(preference.getId());
    		temporarySale.setTotalSaleAmount(saleWebDTO.getTotalSaleAmount());
    		temporarySale.setCodeDiscount(saleWebDTO.getCodeDiscount());
    		temporarySale.setTypeIdentificationClient(saleWebDTO.getTypeIdentificationClient());
    		temporarySale.setDniClient(saleWebDTO.getDniClient());
    		temporarySale.setFirstNameClient(saleWebDTO.getFirstNameClient());
    		temporarySale.setLastNameClient(saleWebDTO.getLastNameClient());
    		temporarySale.setEmailClient(saleWebDTO.getEmailClient());
    		temporarySale.setLastNameClient(saleWebDTO.getLastNameClient());
    		temporarySale.setPhoneClient(saleWebDTO.getPhoneClient());
    		temporarySale.setCountryClient(saleWebDTO.getCountryClient());
    		// Datos de tour
    		temporarySale.setOrderStatus(saleWebDTO.getOrderStatus());
    		temporarySale.setStartingPoint(saleWebDTO.getStartingPoint());
    		temporarySale.setDestinationPoints(saleWebDTO.getDestinationPoints());
    		temporarySale.setImportantObservations(saleWebDTO.getImportantObservations());

    		temporarySale.setPaymentMethod(saleWebDTO.getPaymentMethod());
    		temporarySale.setPaymentId(saleWebDTO.getPaymentId());
    		temporarySale.setPaymentStatus(saleWebDTO.getPaymentStatus());
    		temporarySale.setPaymentCardId(saleWebDTO.getPaymentCardId());
    		temporarySale.setPaymentCardType(saleWebDTO.getPaymentCardType());
    		temporarySale.setPaymentCardName(saleWebDTO.getPaymentCardName());
            temporarySale.setPaymentCardIdentification(saleWebDTO.getPaymentCardIdentification());
            temporarySale.setPaymentCardExpirationYear(saleWebDTO.getPaymentCardExpirationYear());
            temporarySale.setPaymentCardExpirationMonth(saleWebDTO.getPaymentCardExpirationMonth());
            temporarySale.setPaymentCardFirstNumbers(saleWebDTO.getPaymentCardFirstNumbers());
            temporarySale.setPaymentCardLastNumbers(saleWebDTO.getPaymentCardLastNumbers());
            temporarySale.setInstallments(saleWebDTO.getInstallments());
            temporarySale.setNetReceivedAmount(saleWebDTO.getNetReceivedAmount());
            temporarySale.setAmountToReserve(saleWebDTO.getAmountToReserve());
            temporarySale.setAmountPendingToPay(saleWebDTO.getAmountPendingToPay());
            temporarySale.setPercentageToReserve(saleWebDTO.getPercentageToReserve());
    		
    		// Convertir ItemSaleDTO a SaleItem
            List<SaleItem> saleItems = convertToSaleItem(saleWebDTO.getItems());
            temporarySale.setItems(saleItems);
    		
    		//Crear venta temporal
    		temporarySaleRepository.save(temporarySale);
    		System.out.println("Entro en proceso de temporary sale en controller mp");
    		
    		return ResponseEntity.ok(Map.of("preference", preference));  // Devolver un JSON con el preference
    	} catch (Exception e) {
    		System.out.println("Entro en error en controller mp");
    		return ResponseEntity.status(500).body("Error al crear el pago: " + e.getMessage());
    	}
    }
    
    private List<SaleItem> convertToSaleItem(List<ItemSaleDTO> itemSaleDTOs) {
        return itemSaleDTOs.stream().map(itemSaleDTO -> {
            SaleItem saleItem = new SaleItem();
            saleItem.setProductId(itemSaleDTO.getProductId());
            saleItem.setCode(itemSaleDTO.getCode());
            saleItem.setQuantityCars(itemSaleDTO.getQuantity());
            saleItem.setCategory(itemSaleDTO.getCategory());
            saleItem.setSubcategory(itemSaleDTO.getSubcategory());
            saleItem.setName(itemSaleDTO.getName());
            saleItem.setReservedDate(itemSaleDTO.getDate());
            saleItem.setHourInit(itemSaleDTO.getHourInit());
            
            saleItem.setEndTimeTour(itemSaleDTO.getEndTime());
            saleItem.setImage(itemSaleDTO.getImage());
            saleItem.setPromotion(itemSaleDTO.isPromotion());
            
            saleItem.setPersonsQuantity(itemSaleDTO.getPersonsQuantity());
            saleItem.setQuantityAdults(itemSaleDTO.getQuantityAdults());
            saleItem.setQuantityMinors(itemSaleDTO.getQuantityMinors());
            saleItem.setQuantityChildrens(itemSaleDTO.getQuantityChildrens());
            saleItem.setQuantityBabys(itemSaleDTO.getQuantityBabys());
            saleItem.setTypeTour(itemSaleDTO.getTypeTour());

            saleItem.setDescription("description");
            saleItem.setSalePrice(itemSaleDTO.getPrice());
            saleItem.setSubtotal(itemSaleDTO.getSubtotal());
            
            return saleItem;
        }).collect(Collectors.toList());
    }
    
    //Obtener todos los pagos
    @GetMapping (value = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<MercadoPago>> getAllCatepayments() {
		try {
			List<MercadoPago> payments = mercadoPagoService.getAllPayments();
			return new ResponseEntity<>(payments, HttpStatus.OK); // Codigo 200

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Error 500
		}
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPago(@PathVariable Long id) {
        Optional<MercadoPago> pago = mercadoPagoService.obtenerPago(id);
        return pago.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
  
    // Endpoint para obtener los detalles del pago por paymentId
    @GetMapping("/detalles/{paymentId}")
    public ResponseEntity<?> obtenerDetallesPago(@PathVariable String paymentId) {
        try {
            // Llamar al servicio para obtener los detalles del pago
            Payment paymentDetails = mercadoPagoService.obtenerDetallesPago(paymentId);

            // Retornar los detalles del pago al frontend
            return ResponseEntity.ok(paymentDetails);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al obtener los detalles del pago: " + e.getMessage());
        }
    }
}


