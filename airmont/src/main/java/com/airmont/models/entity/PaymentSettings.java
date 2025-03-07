package com.airmont.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PaymentSettings {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private boolean activePaymentByTransferBank;
	private boolean activePaymentByCash;
	private boolean activePaymentByMercadoPago;
    private double percentagePaymentMercadoPago;
    private double percentagePaymentTransferBank;
    private double percentagePaymentCash;
    
	public PaymentSettings() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public boolean isActivePaymentByTransferBank() {
		return activePaymentByTransferBank;
	}

	public void setActivePaymentByTransferBank(boolean activePaymentByTransferBank) {
		this.activePaymentByTransferBank = activePaymentByTransferBank;
	}

	public boolean getActivePaymentByCash() {
		return activePaymentByCash;
	}

	public void setActivePaymentByCash(boolean activePaymentByCash) {
		this.activePaymentByCash = activePaymentByCash;
	}

	public boolean getActivePaymentByMercadoPago() {
		return activePaymentByMercadoPago;
	}

	public void setActivePaymentByMercadoPago(boolean activePaymentByMercadoPago) {
		this.activePaymentByMercadoPago = activePaymentByMercadoPago;
	}

	public double getPercentagePaymentMercadoPago() {
		return percentagePaymentMercadoPago;
	}

	public void setPercentagePaymentMercadoPago(double percentagePaymentMercadoPago) {
		this.percentagePaymentMercadoPago = percentagePaymentMercadoPago;
	}

	public double getPercentagePaymentTransferBank() {
		return percentagePaymentTransferBank;
	}

	public void setPercentagePaymentTransferBank(double percentagePaymentTransferBank) {
		this.percentagePaymentTransferBank = percentagePaymentTransferBank;
	}

	public double getPercentagePaymentCash() {
		return percentagePaymentCash;
	}

	public void setPercentagePaymentCash(double percentagePaymentCash) {
		this.percentagePaymentCash = percentagePaymentCash;
	}

}
