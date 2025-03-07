package com.airmont.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.airmont.dto.ProductDTO;
import com.airmont.dto.SaleWebDTO;
import com.airmont.models.entity.DiscountCode;
import com.airmont.models.entity.MercadoPago;
import com.airmont.models.entity.PaymentSettings;
import com.airmont.repositories.MercadoPagoRepository;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.merchantorder.MerchantOrderClient;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferencePayerRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.merchantorder.MerchantOrder;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;

import io.github.cdimascio.dotenv.Dotenv;

@Service
public class MercadoPagoService {

    @Autowired
    private MercadoPagoRepository mercadoPagoRepository;
    @Autowired
    private ProductService productService;    
    @Autowired
    private DiscountService discountService;
    @Autowired
    private PaymentSettingsService paymentSettingsService;
    
//    @Value("${mercadopago.access_token}")
//    private String accessToken;
//
//  @Value("${mercadopago.notification_url:default-value}")
//  private String notificationUrl;
    
    @Value("${mercadopago.back_url.success}")
    private String backUrlSuccess;

    @Value("${mercadopago.back_url.pending}")
    private String backUrlPending;

    @Value("${mercadopago.back_url.failure}")
    private String backUrlFailure;

 // Carga las variables del archivo .env
    Dotenv dotenv = Dotenv.load();
    String accessToken = dotenv.get("MERCADO_PAGO_ACCESS_TOKEN");
    String notificationUrl = dotenv.get("MERCADO_PAGO_NOTIFICATION_URL");
    
    public Preference crearPago(MercadoPago mercadoPago) throws Exception {
        // Inicializar Mercado Pago con la clave de acceso
        MercadoPagoConfig.setAccessToken(accessToken);

        // Crear un nuevo cliente de preferencias
        PreferenceClient preferenceClient = new PreferenceClient();

        // Crear el item del pago
        PreferenceItemRequest item = PreferenceItemRequest.builder()
                .title(mercadoPago.getTitle())
                .quantity(mercadoPago.getQuantity())
                .unitPrice(mercadoPago.getPrice())
                .currencyId("ARS")
                .categoryId(mercadoPago.getCategory())
                .description(mercadoPago.getDescription())
                .build();

        // Crear los datos del pagador
        PreferencePayerRequest payer = PreferencePayerRequest.builder()
                .email(mercadoPago.getEmail())
                .build();
        
        // URLs de retorno
        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
        		.success(backUrlSuccess)
        	    .pending(backUrlPending)
        	    .failure(backUrlFailure)
				.build();

        // Crear la solicitud de preferencia
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(List.of(item))
                .payer(payer)
                .backUrls(backUrls)
                .autoReturn("approved")
                .notificationUrl(notificationUrl)
                .build();
        
        Preference preference = preferenceClient.create(preferenceRequest);
        
     // Configurar la preferencia en el pago
      mercadoPago.setPreferenceId(preference.getId());
      mercadoPago.setStatus("CREATED");
        
//		return preference.getId();
		
        // Guardar la preferencia
        return preference;
    }
    
    public Preference crearPagoEnMercadoPago(List<MercadoPago> mercadoPagoItems) throws Exception {
        // Inicializar Mercado Pago con la clave de acceso
        MercadoPagoConfig.setAccessToken(accessToken);

        // Crear un nuevo cliente de preferencias
        PreferenceClient preferenceClient = new PreferenceClient();

     // Mapea los objetos MercadoPago a PreferenceItemRequest
        List<PreferenceItemRequest> items = mercadoPagoItems.stream().map(item -> 
            PreferenceItemRequest.builder()
                .title(item.getTitle())
                .quantity(item.getQuantity())
                .unitPrice(item.getPrice())
                .currencyId("BRL")
                .categoryId(item.getCategory())
                .description(item.getDescription())
                .build()
        ).collect(Collectors.toList());

     // Crear los datos del pagador (suponiendo que es el mismo para todos los ítems)
        MercadoPago mercadoPago = mercadoPagoItems.get(0); // Tomar los datos del primer ítem
        PreferencePayerRequest payer = PreferencePayerRequest.builder()
                .email(mercadoPago.getEmail())
                .build();
        
        // URLs de retorno
        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
        		.success(backUrlSuccess)
        	    .pending(backUrlPending)
        	    .failure(backUrlFailure)
				.build();

        // Crear la solicitud de preferencia
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
        		.items(items)
                .payer(payer)
                .backUrls(backUrls)
                .autoReturn("approved")
                .notificationUrl(notificationUrl)
                .build();
        
        Preference preference = preferenceClient.create(preferenceRequest);
        
     // Configurar la preferencia en el pago
      mercadoPago.setPreferenceId(preference.getId());
      mercadoPago.setStatus("CREATED");
        
//		return preference.getId();
		
        // Guardar la preferencia
        return preference;
    }
    
    public Payment obtenerDetallesPago(String paymentId) throws Exception {
        // Inicializar Mercado Pago con la clave de acceso
        MercadoPagoConfig.setAccessToken(accessToken);

        // Crear el cliente de pagos
        PaymentClient paymentClient = new PaymentClient();

        // Obtener el pago por ID
        Payment payment = paymentClient.get(Long.parseLong(paymentId));
        
        return payment; // Aquí tendrás los detalles completos del pago
    }
    
    public MerchantOrder obtenerDetallesOrden(String merchantOrderId) throws MPApiException, MPException {
        // Configurar el token de acceso
        MercadoPagoConfig.setAccessToken(accessToken);

        // Crear cliente de órdenes de comerciante
        MerchantOrderClient merchantOrderClient = new MerchantOrderClient();

        // Convertir el merchantOrderId de String a Long
        Long merchantOrderIdLong = Long.parseLong(merchantOrderId);

        // Obtener la orden de comerciante por su ID
        MerchantOrder merchantOrder = merchantOrderClient.get(merchantOrderIdLong);

        // Devolver la orden de comerciante
        return merchantOrder;
    }
    public Preference obtenerDetallesPreferencia(String preferenceId) throws MPApiException, MPException {
    	// Configurar el token de acceso
    	MercadoPagoConfig.setAccessToken(accessToken);
    	
    	PreferenceClient client = new PreferenceClient();

    	Preference preference = client.get(preferenceId);
    
    	
    	// Devolver la preferencia
    	return preference;
    }
    
    
    public Preference crearPagoyVenta(SaleWebDTO saleWebDTO) throws Exception {
        // Inicializar Mercado Pago con la clave de acceso
        MercadoPagoConfig.setAccessToken(accessToken);

        // Crear un nuevo cliente de preferencias
        PreferenceClient preferenceClient = new PreferenceClient();
        System.out.println("Dentro de Proceso de preferencia de mercado pago");

        // Inicializar la variable para el porcentaje de descuento
        double discountPercentage = 0.0;
        System.out.println("getCodeDiscountPreferenceMP: " + saleWebDTO.getCodeDiscount());
        // Validar el código de descuento si está presente en el SaleWebDTO
        if (saleWebDTO.getCodeDiscount() != null && !saleWebDTO.getCodeDiscount().isEmpty()) {
            try {
                // Llamar al servicio para validar el código de descuento
                DiscountCode discountCode = discountService.validateDiscountCodeInClient(saleWebDTO.getCodeDiscount());
                
                // Si es válido, obtener el porcentaje de descuento
                discountPercentage = discountCode.getPercentage() / 100.0;
            } catch (RuntimeException e) {
                // Manejar el error si el código no es válido o ha expirado
                throw new Exception("El código de descuento no es válido: " + e.getMessage());
            }
        }
        

        // Calcular el monto total sin descuento
        // Usar AtomicReference para que sea mutable dentro de la lambda
        AtomicReference<BigDecimal> totalAmount = new AtomicReference<>(BigDecimal.ZERO);


        // Crear la lista de items manteniendo el unitPrice original
        List<PreferenceItemRequest> items = saleWebDTO.getItems().stream().map(item -> {
            // Obtener el producto por su ID
            ProductDTO product = productService.getProductDTOById(item.getProductId());
            if (product == null) {
                throw new RuntimeException("El producto DTO con ID " + item.getProductId() + " no existe");
            }
            
            if (!product.isActive()) {
            	throw new RuntimeException("El producto DTO con ID " + item.getProductId() + " no se encuentra activo:");
            }
            
            //Obtener el porcentaje a cobrar para reserva
            PaymentSettings paymentSettings = paymentSettingsService.getPaymentSettings();
            //Asignar el porcentaje obtenido desde la db
            BigDecimal percentagePaymentToReserve = BigDecimal.valueOf(paymentSettings.getPercentagePaymentMercadoPago()/100);         

            // Calcular el total del producto sin aplicar descuento
            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            totalAmount.updateAndGet(v -> v.add(itemTotal));

            // Mapear el producto a un PreferenceItemRequest con el precio original
            return PreferenceItemRequest.builder()
                    .id("Id:" + product.getId() + "/Cod:" + product.getCode())
                    .title(product.getName())  // Título del producto
                    .quantity(item.getQuantity())  // Cantidad de autos que viene de SaleWebDTO
                    .categoryId(product.getCategory() + "/" + product.getSubcategory())  // Categoría del producto
                    .description(item.getDate() + " | " + item.getHourInit() + "Hs. | " + item.getPersonsQuantity() + " personas | " + item.getQuantity() + " auto/s.")  // Detalles del ítem (día y hora
                    .unitPrice(product.getPrice().multiply(percentagePaymentToReserve))  // Precio unitario original
                    .currencyId("ARS")  // Moneda
                    .pictureUrl(product.getImageMain())
                    .build();
        }).collect(Collectors.toList());

        // Si el descuento es válido, calcular el descuento total
        BigDecimal discountAmount = BigDecimal.ZERO;
        //obtener el valor total con totalAmount.get()
        BigDecimal total = totalAmount.get();
        if (discountPercentage > 0) {
            discountAmount = total.multiply(BigDecimal.valueOf(discountPercentage));
            
            // Crear un ítem de descuento negativo
            PreferenceItemRequest discountItem = PreferenceItemRequest.builder()
                    .id("DISCOUNT")
                    .title("Descuento aplicado")
                    .quantity(1)
                    .unitPrice(discountAmount.negate())  // Precio negativo del descuento
                    .currencyId("ARS")
                    .build();

            // Agregar el ítem de descuento a la lista de items
            items.add(discountItem);
        }
        
        //Sumar el Costo de envío
//        if (saleWebDTO.getCostShipping() > 0 && saleWebDTO.isStorePickup() == false) {
//            
//            // Crear un ítem de costo envío
//            PreferenceItemRequest costShippingItem = PreferenceItemRequest.builder()
//                    .id("ShippingCost")
//                    .title("Costo de Envío")
//                    .quantity(1)
//                    .unitPrice(BigDecimal.valueOf(saleWebDTO.getCostShipping()))
//                    .currencyId("ARS")
//                    .build();
//
//            // Agregar el ítem de envio a la lista de items
//            items.add(costShippingItem);
//        }

        // Crear los datos del pagador usando la información del cliente
        PreferencePayerRequest payer = PreferencePayerRequest.builder()
                .email(saleWebDTO.getEmailClient())
                .name(saleWebDTO.getFirstNameClient() + " " + saleWebDTO.getLastNameClient())
                .surname(saleWebDTO.getLastNameClient())
                .identification(IdentificationRequest.builder()
                        .type(saleWebDTO.getTypeIdentificationClient())
                        .number(saleWebDTO.getDniClient().toString())
                        .build())
                .build();

        // URLs de retorno
        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
        		.success(backUrlSuccess)
        	    .pending(backUrlPending)
        	    .failure(backUrlFailure)
                .build();

        // Crear la solicitud de preferencia
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items)  // Lista de ítems, incluyendo el descuento si aplica
                .payer(payer)  // Datos del pagador
                .backUrls(backUrls)
                .notificationUrl(notificationUrl)
                .build();

        // Crear la preferencia
        Preference preference = preferenceClient.create(preferenceRequest);

        // Retornar la preferencia creada
        return preference;
    }

    public Optional<MercadoPago> obtenerPago(Long id) {
        return mercadoPagoRepository.findById(id);
    }
    
    public List<MercadoPago> getAllPayments() {
        return mercadoPagoRepository.findAll();
    }
    
  
}


