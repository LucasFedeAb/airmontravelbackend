package com.airmont.models.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "venta_temporal")
public class TemporarySale {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String preferenceId;  // Se asocia con Mercado Pago
    private BigDecimal totalSaleAmount;
    
    // Datos del cliente
    private String codeDiscount;
    private String typeIdentificationClient;
    private Integer dniClient;
    private String firstNameClient;
    private String lastNameClient;
    private String emailClient;
    private Long phoneClient;
    private String countryClient;
    
    // Datos de Tour
    private String orderStatus;
    private String startingPoint;
    private String destinationPoints;
    private String importantObservations;
    
    //Datos de pago
    private String paymentMethod;
    private String paymentId;
    private String paymentStatus;
    private String paymentCardId;
    private String paymentCardType;
    private String paymentCardName;
    private String paymentCardIdentification;
    private int paymentCardExpirationYear;
    private int paymentCardExpirationMonth;
    private String paymentCardFirstNumbers;
    private String paymentCardLastNumbers;
    private int installments;
    private BigDecimal netReceivedAmount;
    private BigDecimal discountForPaymentMethod;
    private BigDecimal amountToReserve;
    private BigDecimal amountPendingToPay;
    private double percentageToReserve;

    // Relación con los ítems de la venta temporal
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "temporarySale_id")
    private List<SaleItem> items;

    public TemporarySale() {
    }

    // Constructor con los campos necesarios

	public TemporarySale(String preferenceId, BigDecimal totalSaleAmount, String codeDiscount,
			String typeIdentificationClient, Integer dniClient, String firstNameClient, String lastNameClient,
			String emailClient, Long phoneClient, String countryClient, String orderStatus, String startingPoint,
			String destinationPoints, String importantObservations, String paymentMethod, String paymentId,
			String paymentStatus, String paymentCardId, String paymentCardType, String paymentCardName,
			String paymentCardIdentification, int paymentCardExpirationYear, int paymentCardExpirationMonth,
			String paymentCardFirstNumbers, String paymentCardLastNumbers, int installments,
			BigDecimal netReceivedAmount, BigDecimal discountForPaymentMethod, BigDecimal amountToReserve,
			BigDecimal amountPendingToPay, double percentageToReserve, List<SaleItem> items) {
		super();
		this.preferenceId = preferenceId;
		this.totalSaleAmount = totalSaleAmount;
		this.codeDiscount = codeDiscount;
		this.typeIdentificationClient = typeIdentificationClient;
		this.dniClient = dniClient;
		this.firstNameClient = firstNameClient;
		this.lastNameClient = lastNameClient;
		this.emailClient = emailClient;
		this.phoneClient = phoneClient;
		this.countryClient = countryClient;
		this.orderStatus = orderStatus;
		this.startingPoint = startingPoint;
		this.destinationPoints = destinationPoints;
		this.importantObservations = importantObservations;
		this.paymentMethod = paymentMethod;
		this.paymentId = paymentId;
		this.paymentStatus = paymentStatus;
		this.paymentCardId = paymentCardId;
		this.paymentCardType = paymentCardType;
		this.paymentCardName = paymentCardName;
		this.paymentCardIdentification = paymentCardIdentification;
		this.paymentCardExpirationYear = paymentCardExpirationYear;
		this.paymentCardExpirationMonth = paymentCardExpirationMonth;
		this.paymentCardFirstNumbers = paymentCardFirstNumbers;
		this.paymentCardLastNumbers = paymentCardLastNumbers;
		this.installments = installments;
		this.netReceivedAmount = netReceivedAmount;
		this.discountForPaymentMethod = discountForPaymentMethod;
		this.amountToReserve = amountToReserve;
		this.amountPendingToPay = amountPendingToPay;
		this.percentageToReserve = percentageToReserve;
		this.items = items;
	}
	
	// Getters y Setters
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPreferenceId() {
		return preferenceId;
	}

	public void setPreferenceId(String preferenceId) {
		this.preferenceId = preferenceId;
	}

	public BigDecimal getTotalSaleAmount() {
		return totalSaleAmount;
	}

	public void setTotalSaleAmount(BigDecimal totalSaleAmount) {
		this.totalSaleAmount = totalSaleAmount;
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

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
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
