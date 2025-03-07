package com.airmont.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class PaymentResponse {
	
	@Id
	private String preferenceId;
    private String paymentId;
    private String status;
    
    
	public String getPreferenceId() {
		return preferenceId;
	}
	public void setPreferenceId(String preferenceId) {
		this.preferenceId = preferenceId;
	}
	
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
   
}

