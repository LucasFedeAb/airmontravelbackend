package com.airmont.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Descuentos_de_Pago")
public class PaymentDiscountStore {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double discountForTransfer;

    @Column(nullable = false)
    private Double discountForCash;
    
    @Column(nullable = false)
    private Double bonusAmount;
    
    @Column(nullable = false)
    private int installments;

	public PaymentDiscountStore() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PaymentDiscountStore(Long id, Double discountForTransfer, Double discountForCash, Double bonusAmount, int installments) {
		super();
		this.id = id;
		this.discountForTransfer = discountForTransfer;
		this.discountForCash = discountForCash;
		this.bonusAmount = bonusAmount;
		this.installments = installments;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getDiscountForTransfer() {
		return discountForTransfer;
	}

	public void setDiscountForTransfer(Double discountForTransfer) {
		this.discountForTransfer = discountForTransfer;
	}

	public Double getDiscountForCash() {
		return discountForCash;
	}

	public void setDiscountForCash(Double discountForCash) {
		this.discountForCash = discountForCash;
	}

	public Double getBonusAmount() {
		return bonusAmount;
	}

	public void setBonusAmount(Double bonusAmount) {
		this.bonusAmount = bonusAmount;
	}

	public int getInstallments() {
		return installments;
	}

	public void setInstallments(int installments) {
		this.installments = installments;
	}
	
}
