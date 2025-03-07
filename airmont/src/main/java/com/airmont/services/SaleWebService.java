package com.airmont.services;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.airmont.dto.ItemSaleDTO;
import com.airmont.dto.SaleWebDTO;
import com.airmont.models.entity.DiscountCode;
import com.airmont.models.entity.PaymentDiscountStore;
import com.airmont.models.entity.PaymentSettings;
import com.airmont.models.entity.Product;
import com.airmont.models.entity.ReservationCalendar;
import com.airmont.models.entity.SaleItem;
import com.airmont.models.entity.SaleWeb;
import com.airmont.repositories.ProductRepository;
import com.airmont.repositories.ReservationCalendarRepository;
import com.airmont.repositories.SaleWebRepository;
//import com.ventas.models.entity.ProductOption;

import jakarta.transaction.Transactional;

@Service
public class SaleWebService {
    
    @Autowired
    private SaleWebRepository saleWebRepository;
    @Autowired
    private ProductRepository productRepository;
//    @Autowired
//    private ProductService productService;
    @Autowired
    private DateTimeConverterService timeAPIService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private DiscountService discountService;
    @Autowired
    private PaymentSettingsService paymentSettingsService;
    
    @Autowired
    private ReservationCalendarRepository calendarRepository;
    @Autowired
    private ReservationCalendarService reservationCalendarService;
//    @Autowired
//    private SaleItemRepository saleItemRepository;

    //Crear Venta web
    @Transactional
    public SaleWebDTO createSaleWeb(SaleWebDTO saleWebDTO) {
        SaleWeb saleWeb = new SaleWeb();
        
        // Generar y asignar el código de venta único
        saleWeb.setCodeSale(generateUniqueSaleCode());
        
        // Obtener fecha Venta a través de la Api
		ZonedDateTime timeApi = timeAPIService.getCurrentDateTime();
		String formattedDate = timeAPIService.formatterDateTime(timeApi);
		saleWeb.setDateSale(formattedDate);
        
//        double totalSaleAmount = 0.0;
		BigDecimal totalSaleAmount = BigDecimal.ZERO;
        
        saleWeb.setCodeDiscount(saleWebDTO.getCodeDiscount());
        // Establecer los datos del cliente
        saleWeb.setTypeIdentificationClient(saleWebDTO.getTypeIdentificationClient());
        saleWeb.setDniClient(saleWebDTO.getDniClient());
        saleWeb.setFirstNameClient(saleWebDTO.getFirstNameClient());
        saleWeb.setLastNameClient(saleWebDTO.getLastNameClient());
        saleWeb.setEmailClient(saleWebDTO.getEmailClient());
        saleWeb.setPhoneClient(saleWebDTO.getPhoneClient());
        saleWeb.setCountryClient(saleWebDTO.getCountryClient());
        //Datos de Reserva
        saleWeb.setStartingPoint(saleWebDTO.getStartingPoint());
        saleWeb.setDestinationPoints(saleWebDTO.getDestinationPoints());
        saleWeb.setImportantObservations(saleWebDTO.getImportantObservations());
        saleWeb.setOrderStatus(saleWebDTO.getOrderStatus());
        //Datos de pago
        saleWeb.setPaymentMethod(saleWebDTO.getPaymentMethod());
        saleWeb.setPaymentId(saleWebDTO.getPaymentId());
        saleWeb.setPaymentStatus(saleWebDTO.getPaymentStatus());
        saleWeb.setPaymentCardId(saleWebDTO.getPaymentCardId());
        saleWeb.setPaymentCardType(saleWebDTO.getPaymentCardType());
        saleWeb.setPaymentCardName(saleWebDTO.getPaymentCardName());
        saleWeb.setPaymentCardIdentification(saleWebDTO.getPaymentCardIdentification());
        saleWeb.setPaymentCardExpirationYear(saleWebDTO.getPaymentCardExpirationYear());
        saleWeb.setPaymentCardExpirationMonth(saleWebDTO.getPaymentCardExpirationMonth());
        saleWeb.setPaymentCardFirstNumbers(saleWebDTO.getPaymentCardFirstNumbers());
        saleWeb.setPaymentCardLastNumbers(saleWebDTO.getPaymentCardLastNumbers());
        saleWeb.setInstallments(saleWebDTO.getInstallments());
        saleWeb.setNetReceivedAmount(saleWebDTO.getNetReceivedAmount());
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Formato personalizado

        // Fecha y hora actuales
        LocalDateTime now = LocalDateTime.now();
//        LocalDate nextDay = now.toLocalDate().plusDays(1); // Día siguiente
   
        List<ItemSaleDTO> itemsDTO = saleWebDTO.getItems();
        for (ItemSaleDTO itemDTO : itemsDTO) {
        	
        	Product product = productRepository.findById(itemDTO.getProductId())
                  .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        	
        	System.out.println("Producto encontrado: " + product.getCode());
        	
        	// Convertir la fecha del JSON a LocalDate
            LocalDate reservedDate;
            try {
                reservedDate = LocalDate.parse(itemDTO.getDate(), formatter);
            } catch (DateTimeParseException e) {
                throw new RuntimeException("Formato de fecha inválido: " + itemDTO.getDate() 
                        + ". El formato esperado es dd/MM/yyyy.");
            }

            LocalTime startTime = convertDecimalToLocalTime(itemDTO.getHourInit());
            LocalTime endTime = convertDecimalToLocalTime(product.getEndTime());
            
         // Validar que el rango horario sea válido
            if (startTime.equals(endTime)) {
                throw new RuntimeException("El rango horario no es válido: la hora de inicio y fin no pueden ser iguales.");
            }

            LocalDateTime reservedStartDateTime = LocalDateTime.of(reservedDate, startTime);

            // Validar antelación mínima de 24 horas
            if (reservedStartDateTime.isBefore(now.plusHours(23))) {
                throw new RuntimeException("La fecha y hora seleccionada (" 
                    + reservedStartDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) 
                    + ") debe tener una antelación mínima de 24 horas respecto a la fecha actual.");
            }
            
         // Verificar si la fecha existe en el calendario
            ReservationCalendar calendarDate = calendarRepository.findByDate(reservedDate);
            if (calendarDate == null) {
                throw new RuntimeException("La fecha " + reservedDate + " no está registrada en el calendario.");
            }
            


            // Validar que la fecha esté disponible
            if (!calendarDate.isAvailable()) {
                throw new RuntimeException("La fecha seleccionada (" + itemDTO.getDate() + ") no está disponible.");
            }
            
         // Detectar si el rango cruza la medianoche
            boolean crossesMidnight = endTime.isBefore(startTime);
         // Validar que el rango horario no se solape con los existentes
            if (crossesMidnight) {
                // Caso: rango cruza la medianoche
                LocalTime midnight = LocalTime.MAX; // Representa 23:59:59

                // Validar la primera parte del rango (hoy)
//                boolean isFirstPartAvailable = calendarDate.getReservedTimeRanges().stream()
//                		.noneMatch(range -> range.overlapsWith(startTime, midnight) || 
//                				startTime.isBefore(range.getEndTime().plusHours(1))); // Validar diferencia de 1 hora
                boolean isFirstPartAvailable = calendarDate.getReservedTimeRanges().stream()
                		.noneMatch(range -> range.overlapsWith(startTime, midnight));
                if (!isFirstPartAvailable) {
                    throw new RuntimeException("El rango horario de " + startTime + " a " + midnight 
                        + " en la fecha " + reservedDate + " ya está reservado.");
                }

                // Validar la segunda parte del rango (día siguiente)
                LocalDate nextDay = reservedDate.plusDays(1);
                ReservationCalendar nextDayCalendar = calendarRepository.findByDate(nextDay);
                if (nextDayCalendar == null) {
                    throw new RuntimeException("El calendario para la fecha siguiente " + nextDay + " no está registrado.");
                }
////                boolean isSecondPartAvailable = nextDayCalendar.getReservedTimeRanges().stream()
////                        .noneMatch(range -> range.overlapsWith(LocalTime.MIN, endTime) || 
////                                LocalTime.MIN.isBefore(range.getEndTime().plusHours(1))); // Validar diferencia de 1 hora
////                if (!isSecondPartAvailable) {
////                    throw new RuntimeException("El rango horario de " + LocalTime.MIN + " a " + endTime 
////                        + " en la fecha " + nextDay + " ya está reservado o no cumple con la diferencia mínima de 1 hora.");
////                }

                // Reservar ambas partes
                calendarDate.addTimeRange(startTime, midnight, saleWebDTO.getDniClient());
                calendarDate.addCodeSaleToReason(saleWeb.getCodeSale());
                calendarRepository.save(calendarDate);

                nextDayCalendar.addTimeRange(LocalTime.MIN, endTime, saleWebDTO.getDniClient());
                nextDayCalendar.addCodeSaleToReason(saleWeb.getCodeSale());
                calendarRepository.save(nextDayCalendar);
                
             // Actualizar disponibilidad para ambos días
                reservationCalendarService.updateAvailabilityBasedOnOccupancy(reservedDate);
////                reservationCalendarService.updateAvailabilityBasedOnOccupancy(nextDay);
            } else {
                // Caso: rango dentro del mismo día
//                boolean isRangeAvailable = calendarDate.getReservedTimeRanges().stream()
//                        .noneMatch(range -> range.overlapsWith(startTime, endTime) || 
//                                startTime.isBefore(range.getEndTime().plusHours(1))); // Validar diferencia de 1 hora
            	
            	
//                boolean isRangeAvailable = calendarDate.getReservedTimeRanges().stream()
//                		.noneMatch(range -> range.overlapsWith(startTime, endTime));
//               
//                if (!isRangeAvailable) {
//                    throw new RuntimeException("El rango horario de " + startTime + " a " + endTime 
//                        + " en la fecha " + reservedDate + " ya está reservado o no cumple con la diferencia mínima de 1 hora. :(((((");
//                }
            	
            	
            	boolean isSameClient = calendarDate.getReservedTimeRanges().stream()
            		    .anyMatch(range -> range.getDniClient() != null && range.getDniClient().equals(saleWebDTO.getDniClient()));

            		if (!isSameClient) {
            		    boolean isRangeAvailable = calendarDate.getReservedTimeRanges().stream()
            		        .noneMatch(range -> range.overlapsWith(startTime, endTime));

            		    if (!isRangeAvailable) {
            		        throw new RuntimeException("El rango horario de " + startTime + " a " + endTime 
            		            + " en la fecha " + reservedDate + " ya está reservado o no cumple con la diferencia mínima de 1 hora.");
            		    }
            		}

                // Reservar el rango
                calendarDate.addTimeRange(startTime, endTime, saleWebDTO.getDniClient());
                calendarDate.addCodeSaleToReason(saleWeb.getCodeSale());
                calendarRepository.save(calendarDate);
                
             // Actualizar la disponibilidad del día
                reservationCalendarService.updateAvailabilityBasedOnOccupancy(reservedDate);
            }
            

       
            // Crear ItemVenta
            SaleItem itemSale = new SaleItem();
            itemSale.setReservedDate(itemDTO.getDate());
            itemSale.setHourInit(itemDTO.getHourInit());
            itemSale.setQuantityCars(itemDTO.getQuantity());
            itemSale.setPersonsQuantity(itemDTO.getPersonsQuantity());
            itemSale.setQuantityAdults(itemDTO.getQuantityAdults());
            itemSale.setQuantityMinors(itemDTO.getQuantityMinors());
            itemSale.setQuantityChildrens(itemDTO.getQuantityChildrens());
            itemSale.setQuantityBabys(itemDTO.getQuantityBabys());
            itemSale.setProductId(product.getId());
            itemSale.setCode(product.getCode());
            itemSale.setCategory(product.getCategory());
            itemSale.setSubcategory(product.getSubcategory());
            itemSale.setTypeTour(product.getTourType());
            itemSale.setName(product.getName());
            itemSale.setAgreeDestination(product.isAgreeDestination());
            itemSale.setDestination(product.getDestination());
            itemSale.setDescription("Venta reservada");
            itemSale.setEndTimeTour(product.getEndTime());
            itemSale.setPromotion(product.isPromotion());
            itemSale.setImage(product.getImageMain());
            itemSale.setSalePrice(product.getPrice());
            itemSale.setSubtotal(product.getPrice().multiply(new BigDecimal(itemSale.getQuantityCars())));

            // Asociar ItemVenta con VentaWeb
            itemSale.setSaleWeb(saleWeb);
            saleWeb.getItems().add(itemSale);

            totalSaleAmount = totalSaleAmount.add(itemSale.getSalePrice().multiply(new BigDecimal(itemSale.getQuantityCars())));
        }
        
        
     // Obtener monto total de venta
        saleWeb.setTotalPrice(totalSaleAmount);

        BigDecimal amountNetReceived = totalSaleAmount;
        
     // Aplicar descuento por código de descuento si está presente y es válido
        BigDecimal discountPercentage = BigDecimal.ZERO;
        if (saleWebDTO.getCodeDiscount() != null && !saleWebDTO.getCodeDiscount().isEmpty()) {
            DiscountCode discountCode = discountService.validateDiscountCode(saleWebDTO.getCodeDiscount());
            discountPercentage = BigDecimal.valueOf(discountCode.getPercentage()); // Obtener el porcentaje de descuento
        }
        
        if (discountPercentage.compareTo(BigDecimal.ZERO) > 0) {
            // Convertir totalSaleAmount a BigDecimal
            BigDecimal totalSaleAmountBD = totalSaleAmount;
            
            // Calcular el descuento
            BigDecimal discountAmount = totalSaleAmountBD.multiply(discountPercentage.divide(BigDecimal.valueOf(100)));
            
            // Calcular el monto neto recibido
            amountNetReceived = totalSaleAmountBD.subtract(discountAmount);
        } else {
            // Si no hay descuento, el monto neto recibido es el total
            amountNetReceived = totalSaleAmount;
//            amountNetReceived = BigDecimal.valueOf(totalSaleAmount);
        }
        
      //Obtener configuraciones de terminales de pago desde el servicio de configuracion
        PaymentSettings paymentSettings = paymentSettingsService.getPaymentSettings();
        
        // Aplicar el descuento por transferencia bancaria si aplica
        if (saleWebDTO.getPaymentMethod().equals("transferencia_bancaria")) {
        	   // Obtener los descuentos desde el servicio
            PaymentDiscountStore paymentDiscounts = discountService.getPaymentDiscounts();
            
            // Obtener el descuento por transferencia bancaria
            BigDecimal discountForTransfer = BigDecimal.valueOf(paymentDiscounts.getDiscountForTransfer());
            
            saleWeb.setDiscountForPaymentMethod(discountForTransfer);
            // Reducir el monto por el descuento obtenido de la base de datos
            amountNetReceived = amountNetReceived.subtract(amountNetReceived.multiply(discountForTransfer.divide(BigDecimal.valueOf(100))));
            
            //Calcular el porcentaje inicial a transferir para Reserva
            BigDecimal amountToReserveCalculated = BigDecimal.valueOf(paymentSettings.getPercentagePaymentTransferBank()/100);
            //Asignar el resultado a la venta
            saleWeb.setAmountToReserve(amountNetReceived.multiply(amountToReserveCalculated));
            saleWeb.setAmountPendingToPay(amountNetReceived.subtract(saleWeb.getAmountToReserve()));
            saleWeb.setPercentageToReserve(paymentSettings.getPercentagePaymentTransferBank());
            System.out.println("paymentMethod transferencia: " + amountNetReceived);
            System.out.println("AmountToReserve: " + saleWeb.getAmountToReserve());
            System.out.println("AmountPendingToPay: " + saleWeb.getAmountPendingToPay());
        }
        
        // Aplicar el descuento por pago en efectivo si aplica
        if (saleWebDTO.getPaymentMethod().equals("efectivo")) {
        	   // Obtener los descuentos desde el servicio
            PaymentDiscountStore paymentDiscounts = discountService.getPaymentDiscounts();
            
            // Obtener el descuento por transferencia bancaria
            BigDecimal discountForCash = BigDecimal.valueOf(paymentDiscounts.getDiscountForCash());
            
            saleWeb.setDiscountForPaymentMethod(discountForCash);
            
            // Reducir el monto por el descuento obtenido de la base de datos
            amountNetReceived = amountNetReceived.subtract(amountNetReceived.multiply(discountForCash.divide(BigDecimal.valueOf(100))));
          
          //Calcular el porcentaje inicial a transferir para Reserva
            BigDecimal amountToReserveCalculated = BigDecimal.valueOf(paymentSettings.getPercentagePaymentCash()/100);
            //Asignar el resultado a la venta
            saleWeb.setAmountToReserve(amountNetReceived.multiply(amountToReserveCalculated));
            saleWeb.setAmountPendingToPay(amountNetReceived.subtract(saleWeb.getAmountToReserve()));
            saleWeb.setPercentageToReserve(paymentSettings.getPercentagePaymentCash());
            System.out.println("paymentMethod efectivo: " + amountNetReceived);
            System.out.println("AmountToReserve: " + saleWeb.getAmountToReserve());
            System.out.println("AmountPendingToPay: " + saleWeb.getAmountPendingToPay());
        }
        
     // Asignar el monto neto recibido a la venta
        saleWeb.setNetReceivedAmount(amountNetReceived);
        System.out.println("NetReceivedAmount: " + amountNetReceived);
        
        if (saleWebDTO.getPaymentMethod().equals("mercado_pago") && 
        		discountPercentage.compareTo(BigDecimal.ZERO) > 0) {
        	saleWeb.setTotalPrice(amountNetReceived);
        	saleWeb.setNetReceivedAmount(saleWebDTO.getNetReceivedAmount());
        	System.out.println("PaymentMethod mercado pago + descuento mayor a 0: ");
        	System.out.println("TotalPrice: " + saleWeb.getTotalPrice());
        	System.out.println("NetReceivedAmount: " + saleWeb.getNetReceivedAmount());
        	
        	//Calcular el porcentaje inicial a transferir para Reserva
            BigDecimal amountToReserveCalculated = BigDecimal.valueOf(paymentSettings.getPercentagePaymentMercadoPago()/100);
            //Asignar el resultado a la venta
            saleWeb.setAmountToReserve(amountNetReceived.multiply(amountToReserveCalculated));
            saleWeb.setAmountPendingToPay(amountNetReceived.subtract(saleWeb.getAmountToReserve()));
            saleWeb.setPercentageToReserve(paymentSettings.getPercentagePaymentMercadoPago());
            System.out.println("paymentMethod mercado_pago: " + amountNetReceived);
            System.out.println("AmountToReserve: " + saleWeb.getAmountToReserve());
            System.out.println("AmountPendingToPay: " + saleWeb.getAmountPendingToPay());
        }
        
        
        if (saleWebDTO.getPaymentMethod().equals("mercado_pago") && discountPercentage == BigDecimal.ZERO){
        	saleWeb.setNetReceivedAmount(saleWebDTO.getNetReceivedAmount());
        	saleWeb.setDiscountForPaymentMethod(BigDecimal.ZERO);
        	System.out.println("PaymentMethod mercado_pago + descuento = 0: " + saleWebDTO.getNetReceivedAmount());
        	
        	//Calcular el porcentaje inicial a transferir para Reserva
            BigDecimal amountToReserveCalculated = BigDecimal.valueOf(paymentSettings.getPercentagePaymentMercadoPago()/100);
            //Asignar el resultado a la venta
            saleWeb.setAmountToReserve(amountNetReceived.multiply(amountToReserveCalculated));
            saleWeb.setAmountPendingToPay(amountNetReceived.subtract(saleWeb.getAmountToReserve()));
            saleWeb.setPercentageToReserve(paymentSettings.getPercentagePaymentMercadoPago());
            System.out.println("paymentMethod mercado_pago: " + amountNetReceived);
            System.out.println("AmountToReserve: " + saleWeb.getAmountToReserve());
            System.out.println("AmountPendingToPay: " + saleWeb.getAmountPendingToPay());
        }
  
        System.out.println("NetReceivedAmount Last: " + saleWeb.getNetReceivedAmount());
        
        
        // Guardar la venta en la base de datos
        SaleWeb savedSaleWeb = saleWebRepository.save(saleWeb);
        
        // Verificar si el estado del pago es aprobado o el método de pago es "transferencia_bancaria"
        if ("approved".equals(savedSaleWeb.getPaymentStatus()) || 
            "transferencia_bancaria".equals(savedSaleWeb.getPaymentMethod()) || 
            "efectivo".equals(savedSaleWeb.getPaymentMethod())) {
            
            // Convertir la entidad guardada a DTO
            SaleWebDTO saleWebResponseDTO = convertToDTO(savedSaleWeb);
            
            // Notificar al frontend que el pago ha sido aprobado o se ha realizado por transferencia bancaria
            messagingTemplate.convertAndSend("/topic/payment-status", saleWebResponseDTO);
        }
        
        // Convertir la entidad guardada en un DTO y devolverla	
        return convertToDTO(savedSaleWeb);
    }
    
 // Método para convertir SaleWeb a SaleWebDTO
    private SaleWebDTO convertToDTO(SaleWeb saleWeb) {
        SaleWebDTO saleWebDTO = new SaleWebDTO();
        saleWebDTO.setId(saleWeb.getId());
        saleWebDTO.setDateSale(saleWeb.getDateSale());
        saleWebDTO.setCodeSale(saleWeb.getCodeSale());
        saleWebDTO.setTotalSaleAmount(saleWeb.getTotalPrice());
        saleWebDTO.setCodeDiscount(saleWeb.getCodeDiscount());
        saleWebDTO.setTypeIdentificationClient(saleWeb.getTypeIdentificationClient());
        saleWebDTO.setDniClient(saleWeb.getDniClient());
        saleWebDTO.setFirstNameClient(saleWeb.getFirstNameClient());
        saleWebDTO.setLastNameClient(saleWeb.getLastNameClient());
        saleWebDTO.setEmailClient(saleWeb.getEmailClient());
        saleWebDTO.setPhoneClient(saleWeb.getPhoneClient());
        saleWebDTO.setCountryClient(saleWeb.getCountryClient());
        saleWebDTO.setOrderStatus(saleWeb.getOrderStatus());
        saleWebDTO.setStartingPoint(saleWeb.getStartingPoint());
        saleWebDTO.setDestinationPoints(saleWeb.getDestinationPoints());
        saleWebDTO.setImportantObservations(saleWeb.getImportantObservations());
        
        // Datos de pago
        saleWebDTO.setPaymentMethod(saleWeb.getPaymentMethod());
        saleWebDTO.setPaymentId(saleWeb.getPaymentId());
        saleWebDTO.setPaymentStatus(saleWeb.getPaymentStatus());
        saleWebDTO.setPaymentCardId(saleWeb.getPaymentCardId());
        saleWebDTO.setPaymentCardType(saleWeb.getPaymentCardType());
        saleWebDTO.setPaymentCardName(saleWeb.getPaymentCardName());
        saleWebDTO.setPaymentCardIdentification(saleWeb.getPaymentCardIdentification());
        saleWebDTO.setPaymentCardExpirationYear(saleWeb.getPaymentCardExpirationYear());
        saleWebDTO.setPaymentCardExpirationMonth(saleWeb.getPaymentCardExpirationMonth());
        saleWebDTO.setPaymentCardFirstNumbers(saleWeb.getPaymentCardFirstNumbers());
        saleWebDTO.setPaymentCardLastNumbers(saleWeb.getPaymentCardLastNumbers());
        saleWebDTO.setInstallments(saleWeb.getInstallments());
        saleWebDTO.setNetReceivedAmount(saleWeb.getNetReceivedAmount());
        saleWebDTO.setAmountToReserve(saleWeb.getAmountToReserve());
        saleWebDTO.setAmountPendingToPay(saleWeb.getAmountPendingToPay());
        saleWebDTO.setPercentageToReserve(saleWeb.getPercentageToReserve());
        
        
//        // Convertir y asignar los items de venta
        List<ItemSaleDTO> itemsDTO = saleWeb.getItems().stream()
            .map(item -> new ItemSaleDTO(item.getCategory(), item.getCode(), item.getReservedDate(), item.getHourInit(), item.getEndTimeTour(), item.getImage(), item.isPromotion(), item.getName(), 
            		item.isAgreeDestination(), item.getPersonsQuantity(), item.getQuantityAdults(), item.getQuantityMinors(), item.getQuantityChildrens(), 
            		item.getQuantityBabys(), item.getSalePrice(), item.getSubtotal(), item.getProductId(), item.getQuantityCars(), item.getSubcategory(), item.getTypeTour()))
            .collect(Collectors.toList());
        
        saleWebDTO.setItems(itemsDTO);
//        
        return saleWebDTO;
        
    }
  
    
    // Método para generar codigo unico de venta
    private String generateUniqueSaleCode() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String datePart = sdf.format(new Date());
        Random random = new Random();
        int randomPart = random.nextInt(900000) + 100000; // Genera un número de 6 dígitos
        return "SALE-" + datePart + "-" + randomPart;
    }
    
       
    // Obtener todas las ventas web
    public List<SaleWeb> getAllSalesWeb (){
    	return saleWebRepository.findAll();
    }
    
    // Obtener lista de ventas por DNI de cliente
    public List<SaleWeb> getSalesWebByClientDni(Integer dniClient) {
        return saleWebRepository.findByDniClient(dniClient);
    }
        
    // Obtener venta Web por id
    public SaleWeb getSaleWebById(Integer id) {
			return saleWebRepository.findById(id).orElse(null);
	}
    
    // Obtener venta web por codigo de venta
    public SaleWeb getSaleWebByCodeSale(String codeSale) {
			return saleWebRepository.findByCodeSale(codeSale);
	}
    
    // Obtener venta web por codigo pago
    public SaleWeb getSaleWebByPaymentId(String paymentId) {
			return saleWebRepository.findByPaymentId(paymentId);
	}

    // Método auxiliar para convertir decimales (ej: 11.3) a LocalTime
    private LocalTime convertDecimalToLocalTime(double hourDecimal) {
        // Separar la parte entera (horas) y la parte decimal (minutos en base 100)
        int hour = (int) Math.floor(hourDecimal); // Parte entera (hora)
        double fractionalPart = hourDecimal - hour; // Parte fraccionaria

        // Convertir la parte fraccionaria a minutos en base 100 (manteniendo precisión exacta)
        int minute = (int) (fractionalPart * 100);

        // Validar que los minutos estén dentro del rango válido (0-59)
        if (minute < 0 || minute >= 60) {
            throw new RuntimeException("El valor proporcionado en el JSON no representa un horario válido: " + hourDecimal);
        }

        // Crear el LocalTime con la hora y los minutos proporcionados
        return LocalTime.of(hour, minute);
    }
    
    
    @Transactional
    public void deleteSale(Integer id) {
        // Buscar la venta por ID
        SaleWeb sale = saleWebRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La venta con ID " + id + " no existe."));

        // Formato de fecha esperado en los SaleItem
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Procesar cada Item de la venta
        sale.getItems().forEach(item -> {
            // Convertir la fecha a LocalDate
            LocalDate reservedDate = LocalDate.parse(item.getReservedDate(), formatter);

            // Convertir las horas de inicio y fin del SaleItem a LocalTime
            LocalTime startTime = convertDecimalToLocalTime(item.getHourInit());
            LocalTime endTime = convertDecimalToLocalTime(item.getEndTimeTour());

            // Caso 1: El rango NO cruza la medianoche
            if (!endTime.isBefore(startTime)) {
                // Eliminar rango horario en el mismo día
                handleReservationRemoval(reservedDate, startTime, endTime, sale.getCodeSale());
            } else {
                // Caso 2: El rango CRUZA la medianoche
                // Dividir el rango en dos partes:
                // - Día actual: desde startTime hasta 23:59:59
//                LocalTime midnight = LocalTime.MAX; // Representa 23:59
            	LocalTime midnight = LocalTime.MIN;
                handleReservationRemoval(reservedDate, startTime, midnight, sale.getCodeSale());

                // - Día siguiente: desde 00:00 hasta endTime
                LocalDate nextDay = reservedDate.plusDays(1);
                LocalTime startOfDay = LocalTime.MIN; // Representa 00:00
                handleReservationRemoval(nextDay, startOfDay, endTime, sale.getCodeSale());
            }
            
         // Actualizar la disponibilidad para los días afectados
            reservationCalendarService.updateAvailabilityBasedOnOccupancy(reservedDate);

            // Si el rango cruza la medianoche, también actualizar el siguiente día
            if (endTime.isBefore(startTime)) {
                LocalDate nextDay = reservedDate.plusDays(1);
                reservationCalendarService.updateAvailabilityBasedOnOccupancy(nextDay);
            }
        });

        // Eliminar la venta
        saleWebRepository.delete(sale);
    }

    /**
     * Maneja la eliminación de un rango horario específico en un día específico.
     */
    private void handleReservationRemoval(LocalDate date, LocalTime startTime, LocalTime endTime, String codeSale) {
        // Obtener el calendario para la fecha correspondiente
        ReservationCalendar calendarDate = calendarRepository.findByDate(date);
        if (calendarDate != null) {
            // Eliminar el rango horario reservado del calendario
            calendarDate.getReservedTimeRanges().removeIf(range ->
                    range.getStartTime().equals(startTime) && range.getEndTime().equals(endTime)
            );

            // Eliminar el código de venta del motivo
            calendarDate.removeCodeSaleFromReason(codeSale);

            // Si no hay más rangos horarios reservados, marcar la fecha como disponible
            if (calendarDate.getReservedTimeRanges().isEmpty()) {
                calendarDate.setAvailable(true);
            }

            // Guardar los cambios en el calendario
            calendarRepository.save(calendarDate);
        }
    }


    
    @Transactional
    public void deleteSaleWebByCodeSale(String codeSale) {
        // Buscar la venta por Código de Venta
        SaleWeb sale = saleWebRepository.findByCodeSale(codeSale);
                
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
     // Obtener todas las fechas asociadas a los SaleItem de la venta
        List<LocalDate> reservedDates = sale.getItems().stream()
                .map(item -> LocalDate.parse(item.getReservedDate(), formatter)) // Convertir String a LocalDate con formato
                .collect(Collectors.toList());

        // Eliminar la venta
        saleWebRepository.delete(sale);

        // Actualizar el calendario para marcar las fechas como disponibles
        reservedDates.forEach(date -> {
            ReservationCalendar calendarDate = calendarRepository.findByDate(date);
            if (calendarDate != null) {
                calendarDate.setAvailable(true);
                calendarDate.setReason(null); // Limpia el motivo de la reserva
                calendarRepository.save(calendarDate);
            }
        });
    }
    
    //DASHBOARD
    
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public SalesSummary getSalesSummaryForPreviousYear() {
        int previousYear = LocalDateTime.now().getYear() - 1;
        LocalDateTime startDate = LocalDateTime.of(previousYear, 1, 1, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(previousYear, 12, 31, 23, 59, 59);

        return calculateSalesSummary(startDate, endDate);
    }

    public SalesSummary getSalesSummaryForCurrentYear() {
        int currentYear = LocalDateTime.now().getYear();
        LocalDateTime startDate = LocalDateTime.of(currentYear, 1, 1, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.now();

        return calculateSalesSummary(startDate, endDate);
    }

    public SalesSummary getSalesSummaryForLastMonth() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = now.minusMonths(1).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endDate = startDate.withDayOfMonth(startDate.toLocalDate().lengthOfMonth()).withHour(23).withMinute(59).withSecond(59);

        return calculateSalesSummary(startDate, endDate);
    }
    
    
    public SalesSummary getReservationsSummaryForCurrentMonth() {
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.withDayOfMonth(1);
        LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth());
        
        LocalDateTime currentDay = LocalDateTime.now();
        LocalDateTime startDateTime = currentDay.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endDateTime = startDateTime.withDayOfMonth(startDateTime.toLocalDate().lengthOfMonth()).withHour(23).withMinute(59).withSecond(59);

        List<SaleWeb> sales = saleWebRepository.findReservationsByCurrentMonth(startDateTime, endDateTime);
        int totalSales = sales.size();

        List<SaleWeb> reservations = saleWebRepository.findAll();

        BigDecimal totalNetReceived = BigDecimal.ZERO;
        int totalReservations = 0;

        for (SaleWeb sale : reservations) {
            for (SaleItem item : sale.getItems()) {
                LocalDate reservedDate = LocalDate.parse(item.getReservedDate(), dateFormatter);
                if (!reservedDate.isBefore(startDate) && !reservedDate.isAfter(endDate)) {

                	BigDecimal discount = sale.getDiscountForPaymentMethod().divide(new BigDecimal(100));          	
                    totalNetReceived = totalNetReceived.add(item.getSubtotal().subtract(item.getSubtotal().multiply(discount)));
                    totalReservations++;
                }
            }
        }

        return new SalesSummary(totalSales, totalNetReceived, totalReservations);
    }
    
    
    public Map<String, SalesSummary> getReservationsSummaryForSixMonths() {
        Map<String, SalesSummary> summaries = new HashMap<>();

        LocalDate now = LocalDate.now();

        // Last three months
        for (int i = 3; i > 0; i--) {
            LocalDate month = now.minusMonths(i);
            summaries.put(month.getMonth() + " " + month.getYear(), calculateReservationsSummaryForMonth(month));
        }

        // Current month
        summaries.put(now.getMonth() + " " + now.getYear(), calculateReservationsSummaryForMonth(now));

        // Next three months
        for (int i = 1; i <= 3; i++) {
            LocalDate month = now.plusMonths(i);
            summaries.put(month.getMonth() + " " + month.getYear(), calculateReservationsSummaryForMonth(month));
        }

        return summaries;
    }
    
    private SalesSummary calculateReservationsSummaryForMonth(LocalDate month) {
        LocalDate startDate = month.withDayOfMonth(1);
        LocalDate endDate = month.withDayOfMonth(month.lengthOfMonth());
        int totalSales = 0;
        
        List<SaleWeb> reservations = saleWebRepository.findAll();
        
        BigDecimal totalNetReceived = BigDecimal.ZERO;
        int totalReservations = 0;

        for (SaleWeb sale : reservations) {
            for (SaleItem item : sale.getItems()) {
                LocalDate reservedDate = LocalDate.parse(item.getReservedDate(), dateFormatter);
                if (!reservedDate.isBefore(startDate) && !reservedDate.isAfter(endDate)) {
                	BigDecimal discount = sale.getDiscountForPaymentMethod().divide(new BigDecimal(100));
                	totalNetReceived = totalNetReceived.add(item.getSubtotal().subtract(item.getSubtotal().multiply(discount)));
                    totalReservations++;
                }
            }
        }

        return new SalesSummary(totalSales, totalNetReceived, totalReservations);
    }
    
    public Map<String, List<String>> getPendingReservationsForCurrentAndNextThreeMonths() {
        Map<String, List<String>> pendingReservations = new HashMap<>();
        LocalDate now = LocalDate.now();
        for (int i = 0; i <= 3; i++) {
            LocalDate month = now.plusMonths(i);
            System.out.println("month: " + month);
            LocalDate startDate = (i == 0) ? now : month.withDayOfMonth(1);
            LocalDate endDate = month.withDayOfMonth(month.lengthOfMonth());
            
            System.out.println("startDate: " + startDate + " endDate:" + endDate);

            List<SaleWeb> reservations = saleWebRepository.findAll();
            List<String> pendingCodes = new ArrayList<>();
//            List<String> pendingCodes = pendingReservations.computeIfAbsent(month.getMonth() + " " + month.getYear(), k -> new ArrayList<>());

            for (SaleWeb sale : reservations) {
                for (SaleItem item : sale.getItems()) {
                    LocalDate reservedDate = LocalDate.parse(item.getReservedDate(), dateFormatter);
                    System.out.println("reservedDate: " + reservedDate);
                    System.out.println("reservedDate.isBefore: " + !reservedDate.isBefore(startDate));
                    System.out.println("reservedDate.isAfter: " + !reservedDate.isAfter(endDate));
                    if (!reservedDate.isBefore(startDate) && !reservedDate.isAfter(endDate)) {
                        pendingCodes.add(sale.getCodeSale());
                    }
                }
            }
            pendingReservations.put(month.getMonth() + " " + month.getYear(), pendingCodes);

        }

        return pendingReservations;
    }
    
    public Map<String, List<Map<String, String>>> getPendingReservationsForCurrentMonthAndNextThreeMonths() {
    	Map<String, List<Map<String, String>>> pendingReservations = new HashMap<>();
        LocalDate now = LocalDate.now();

        for (int i = 0; i <= 3; i++) {
            LocalDate month = now.plusMonths(i);
            LocalDate startDate = (i == 0) ? now : month.withDayOfMonth(1);
            LocalDate endDate = month.withDayOfMonth(month.lengthOfMonth());

            List<SaleWeb> reservations = saleWebRepository.findAll();
            

         // Reservas pendientes ordenadas por fecha de reserva
            List<Map<String, String>> pendingDetails = reservations.stream()
                    .flatMap(sale -> sale.getItems().stream()
                            .filter(item -> {
                            	LocalDate reservedDate = LocalDate.parse(item.getReservedDate(), dateFormatter);
                            	return !reservedDate.isBefore(startDate) && !reservedDate.isAfter(endDate);
                            })
                            .map(item -> {
                                Map<String, String> detail = new HashMap<>();
                                detail.put("code", sale.getCodeSale());
                                detail.put("reservedDate", item.getReservedDate());
                                return detail;
                            })
                    )
                    .sorted((detail1, detail2) -> {
                        LocalDate date1 = LocalDate.parse(detail1.get("reservedDate"), dateFormatter);
                        LocalDate date2 = LocalDate.parse(detail2.get("reservedDate"), dateFormatter);
                        return date1.compareTo(date2);
                    })
                    .collect(Collectors.toList());
            pendingReservations.put(month.getMonth() + " " + month.getYear(), pendingDetails);
        }

        return pendingReservations;
    }

    public List<SaleWeb> getReservationsForCurrentAndNextTwoMonths() {
        LocalDateTime startDate = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endDate = startDate.plusMonths(3).minusDays(1).withHour(23).withMinute(59).withSecond(59);

        return saleWebRepository.findByReservedDateBetween(startDate, endDate);
    }

    private SalesSummary calculateSalesSummary(LocalDateTime startDate, LocalDateTime endDate) {
        List<SaleWeb> sales = saleWebRepository.findByDateSaleBetween(startDate, endDate);

        int totalSales = sales.size();
        BigDecimal totalAmount = sales.stream()
            .map(SaleWeb::getNetReceivedAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
       
                
        int totalItems = sales.stream()
                .flatMap(sale -> sale.getItems().stream())
                .mapToInt(item -> 1) // Count each item as 1
                .sum();
        
        return new SalesSummary(totalSales, totalAmount, totalItems);
    }

    // DTO Class for Sales Summary
    public static class SalesSummary {
        private final int totalSales;
        private final BigDecimal totalAmount;
        private final int totalItems;

        public SalesSummary(int totalSales, BigDecimal totalAmount, int totalItems) {
            this.totalSales = totalSales;
            this.totalAmount = totalAmount;
            this.totalItems = totalItems;
        }

        public int getTotalSales() {
            return totalSales;
        }

        public BigDecimal getTotalAmount() {
            return totalAmount;
        }
        
        public int getTotalItems() {
        	return totalItems;
        }
    }
    
}
