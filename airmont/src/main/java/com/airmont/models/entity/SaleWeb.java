package com.airmont.models.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "ventas_web")
public class SaleWeb {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																		
    
    @Column(name = "Código")
    private String codeSale;
    
    @Column(name = "Fecha_Venta")
    private String dateSale;
    
    @Column(name = "Total_x_Venta")
    private BigDecimal totalPrice;
    
    @Column(name = "Codigo_de_Descuento")
    private String codeDiscount;
    
    @Column(name = "Identificacion_Tipo")
    private String typeIdentificationClient;
    
    @Column(name = "DNI_Cliente")
    private Integer dniClient;
    
    @Column(name = "Nombre_Cliente")
    private String firstNameClient;
    
    @Column(name = "Apellido_Cliente")
    private String lastNameClient;
    
    @Column(name = "Email_Cliente")
    private String emailClient;
    
    @Column(name = "Teléfono_Cliente")
    private Long phoneClient;
    
    @Column(name = "Pais_Cliente")
    private String countryClient;	
    
    @Column(name = "punto_encuentro", columnDefinition = "TEXT")
    private String startingPoint;	
    @Column(name = "destinos_a_convenir", columnDefinition = "TEXT")
    private String destinationPoints;	
    @Column(name = "observaciones_importantes", columnDefinition = "TEXT")
    private String importantObservations;	
    @Column(name = "Estado_De_Pedido")
    private String orderStatus;
    
    @Column(name = "Método_de_Pago")
    private String paymentMethod;
    @Column(name = "Pago_Id")
    private String paymentId;
    @Column(name = "Pago_Estado")
    private String paymentStatus;
    @Column(name = "TarjetaDePago_Id")
    private String paymentCardId;
    @Column(name = "TarjetaDePago_tipo")
    private String paymentCardType;
    @Column(name = "TarjetaDePago_Nombre")
    private String paymentCardName;
    @Column(name = "TarjetaDePago_DNI")
    private String paymentCardIdentification;
    @Column(name = "TarjetaDePago_Año_Expira")
    private int paymentCardExpirationYear;
    @Column(name = "TarjetaDePago_Mes_Expira")
    private int paymentCardExpirationMonth;
    @Column(name = "TarjetaDePago_Primeros_Numeros")
    private String paymentCardFirstNumbers;
    @Column(name = "TarjetaDePago_Ultimos_Numeros")
    private String paymentCardLastNumbers;
    @Column(name = "Pago_Cuotas")
    private int installments;
    @Column(name = "Monto_Neto_Recibido")
    private BigDecimal netReceivedAmount;
    @Column(name = "descuento_segun_medio_pago")
    private BigDecimal discountForPaymentMethod;
    @Column(name = "monto_para_reserva")
    private BigDecimal amountToReserve;
    @Column(name = "monto_peendiente_de_pago")
    private BigDecimal amountPendingToPay;
    @Column(name = "porcentaje_para_reserva", nullable=true)
    private double percentageToReserve;
    
    
    @OneToMany(mappedBy = "saleWeb", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<SaleItem> items = new ArrayList<>();
    
    public SaleWeb() {
    	super();
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodeSale() {
		return codeSale;
	}

	public void setCodeSale(String codeSale) {
		this.codeSale = codeSale;
	}

	public String getDateSale() {
		return dateSale;
	}

	public void setDateSale(String dateSale) {
		this.dateSale = dateSale;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getCodeDiscount() {
		return codeDiscount;
	}

	public void setCodeDiscount(String codeDiscount) {
		this.codeDiscount = codeDiscount;
	}

	public String getTypeIdentificationClient() {
		return typeIdentificationClient;
	}

	public void setTypeIdentificationClient(String typeIdentificationClient) {
		this.typeIdentificationClient = typeIdentificationClient;
	}

	public Integer getDniClient() {
		return dniClient;
	}

	public void setDniClient(Integer dniClient) {
		this.dniClient = dniClient;
	}

	public String getFirstNameClient() {
		return firstNameClient;
	}

	public void setFirstNameClient(String firstNameClient) {
		this.firstNameClient = firstNameClient;
	}

	public String getLastNameClient() {
		return lastNameClient;
	}

	public void setLastNameClient(String lastNameClient) {
		this.lastNameClient = lastNameClient;
	}

	public String getEmailClient() {
		return emailClient;
	}

	public void setEmailClient(String emailClient) {
		this.emailClient = emailClient;
	}

	public Long getPhoneClient() {
		return phoneClient;
	}

	public void setPhoneClient(Long phoneClient) {
		this.phoneClient = phoneClient;
	}

	public String getCountryClient() {
		return countryClient;
	}

	public void setCountryClient(String countryClient) {
		this.countryClient = countryClient;
	}

	public String getStartingPoint() {
		return startingPoint;
	}

	public void setStartingPoint(String startingPoint) {
		this.startingPoint = startingPoint;
	}

	public String getDestinationPoints() {
		return destinationPoints;
	}

	public void setDestinationPoints(String destinationPoints) {
		this.destinationPoints = destinationPoints;
	}

	public String getImportantObservations() {
		return importantObservations;
	}

	public void setImportantObservations(String importantObservations) {
		this.importantObservations = importantObservations;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getPaymentCardId() {
		return paymentCardId;
	}

	public void setPaymentCardId(String paymentCardId) {
		this.paymentCardId = paymentCardId;
	}

	public String getPaymentCardType() {
		return paymentCardType;
	}

	public void setPaymentCardType(String paymentCardType) {
		this.paymentCardType = paymentCardType;
	}

	public String getPaymentCardName() {
		return paymentCardName;
	}

	public void setPaymentCardName(String paymentCardName) {
		this.paymentCardName = paymentCardName;
	}

	public String getPaymentCardIdentification() {
		return paymentCardIdentification;
	}

	public void setPaymentCardIdentification(String paymentCardIdentification) {
		this.paymentCardIdentification = paymentCardIdentification;
	}

	public int getPaymentCardExpirationYear() {
		return paymentCardExpirationYear;
	}

	public void setPaymentCardExpirationYear(int paymentCardExpirationYear) {
		this.paymentCardExpirationYear = paymentCardExpirationYear;
	}

	public int getPaymentCardExpirationMonth() {
		return paymentCardExpirationMonth;
	}

	public void setPaymentCardExpirationMonth(int paymentCardExpirationMonth) {
		this.paymentCardExpirationMonth = paymentCardExpirationMonth;
	}

	public String getPaymentCardFirstNumbers() {
		return paymentCardFirstNumbers;
	}

	public void setPaymentCardFirstNumbers(String paymentCardFirstNumbers) {
		this.paymentCardFirstNumbers = paymentCardFirstNumbers;
	}

	public String getPaymentCardLastNumbers() {
		return paymentCardLastNumbers;
	}

	public void setPaymentCardLastNumbers(String paymentCardLastNumbers) {
		this.paymentCardLastNumbers = paymentCardLastNumbers;
	}

	public int getInstallments() {
		return installments;
	}

	public void setInstallments(int installments) {
		this.installments = installments;
	}

	public BigDecimal getNetReceivedAmount() {
		return netReceivedAmount;
	}

	public void setNetReceivedAmount(BigDecimal netReceivedAmount) {
		this.netReceivedAmount = netReceivedAmount;
	}

	public BigDecimal getDiscountForPaymentMethod() {
		return discountForPaymentMethod;
	}

	public void setDiscountForPaymentMethod(BigDecimal discountForPaymentMethod) {
		this.discountForPaymentMethod = discountForPaymentMethod;
	}
	
	public BigDecimal getAmountToReserve() {
		return amountToReserve;
	}

	public void setAmountToReserve(BigDecimal amountToReserve) {
		this.amountToReserve = amountToReserve;
	}

	public BigDecimal getAmountPendingToPay() {
		return amountPendingToPay;
	}

	public void setAmountPendingToPay(BigDecimal amountPendingToPay) {
		this.amountPendingToPay = amountPendingToPay;
	}
	
	public double getPercentageToReserve() {
		return percentageToReserve;
	}

	public void setPercentageToReserve(double percentageToReserve) {
		this.percentageToReserve = percentageToReserve;
	}

	public List<SaleItem> getItems() {
		return items;
	}

	public void setItems(List<SaleItem> items) {
		this.items = items;
	}
    
}
