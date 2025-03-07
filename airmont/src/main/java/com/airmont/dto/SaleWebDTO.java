package com.airmont.dto;

import java.math.BigDecimal;
import java.util.List;

public class SaleWebDTO {
	
	private Integer id;
	private String dateSale;
	private String codeSale;
    private BigDecimal totalSaleAmount;
    private String codeDiscount;
    private String typeIdentificationClient;
    private Integer dniClient;
    private String firstNameClient;
    private String lastNameClient;
    private String emailClient;
    private Long phoneClient;
    private String countryClient;
    private String startingPoint;
    private String destinationPoints;
    private String importantObservations;
    private String orderStatus;
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
 
    
    private List<ItemSaleDTO> items;
    
    public SaleWebDTO() {
		super();
	}
    

	public SaleWebDTO(Integer id, String dateSale, String codeSale, BigDecimal totalSaleAmount, String codeDiscount,
			String typeIdentificationClient, Integer dniClient, String firstNameClient, String lastNameClient,
			String emailClient, Long phoneClient, String countryClient, String startingPoint, String destinationPoints,
			String importantObservations, String orderStatus, String paymentMethod, String paymentId,
			String paymentStatus, String paymentCardId, String paymentCardType, String paymentCardName,
			String paymentCardIdentification, int paymentCardExpirationYear, int paymentCardExpirationMonth,
			String paymentCardFirstNumbers, String paymentCardLastNumbers, int installments,
			BigDecimal netReceivedAmount, BigDecimal discountForPaymentMethod, BigDecimal amountToReserve,
			BigDecimal amountPendingToPay, double percentageToReserve, List<ItemSaleDTO> items) {
		super();
		this.id = id;
		this.dateSale = dateSale;
		this.codeSale = codeSale;
		this.totalSaleAmount = totalSaleAmount;
		this.codeDiscount = codeDiscount;
		this.typeIdentificationClient = typeIdentificationClient;
		this.dniClient = dniClient;
		this.firstNameClient = firstNameClient;
		this.lastNameClient = lastNameClient;
		this.emailClient = emailClient;
		this.phoneClient = phoneClient;
		this.countryClient = countryClient;
		this.startingPoint = startingPoint;
		this.destinationPoints = destinationPoints;
		this.importantObservations = importantObservations;
		this.orderStatus = orderStatus;
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



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDateSale() {
		return dateSale;
	}

	public void setDateSale(String dateSale) {
		this.dateSale = dateSale;
	}

	public String getCodeSale() {
		return codeSale;
	}

	public void setCodeSale(String codeSale) {
		this.codeSale = codeSale;
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


	public void setPaymentCardLastNumbers(String paymentCardLasttNumbers) {
		this.paymentCardLastNumbers = paymentCardLasttNumbers;
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

	public List<ItemSaleDTO> getItems() {
		return items;
	}

	public void setItems(List<ItemSaleDTO> items) {
		this.items = items;
	}

}
