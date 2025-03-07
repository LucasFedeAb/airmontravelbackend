package com.airmont.models.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class SaleItem {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_item_venta")
	private Integer itemSaleId;
	@Column(name = "acordar_destino")
	private boolean agreeDestination;
	@Column(name = "categoria")
	private String category;
	@Column(name = "codigo_barra")
	private Integer code;
	@Column(name = "descripcion")
	private String description;
	@Column(name = "Destino")
	private String destination;
	@Column(name = "hora_de_inicio")
	private double hourInit;
	@Column(name = "hora_fin_tour_aprox")
	private double endTimeTour;
	@Column(name = "imagen", columnDefinition = "TEXT")
	private String image;
	@Column(name = "oferta")
	private boolean isPromotion;
	@Column(name = "nombre")
	private String name;
	@Column(name = "cantidad_pasajeros")
	private String personsQuantity;
	@Column(name = "cantidad_adultos")
	private Integer quantityAdults;
	@Column(name = "cantidad_menores")
	private Integer quantityMinors;
	@Column(name = "cantidad_niños")
	private Integer quantityChildrens;
	@Column(name = "cantidad_baby")
	private Integer quantityBabys;
	@Column(name = "id_producto_vendido")
	private Integer productId;
	@Column(name = "cantidad_autos_reservados")
	private int quantityCars;
	@Column(name = "fecha_reserva")
	private String reservedDate;
	@Column(name = "subcategoria")
	private String subcategory;
	@Column(name = "precio_venta")
	private BigDecimal salePrice;
	@Column(name = "subtotal")
	private BigDecimal subtotal;
	@ManyToOne
	@JoinColumn(name = "saleWeb_id")
	@JsonBackReference 		//!importante para serializacion y no generar bucle infinito
	private SaleWeb saleWeb;
	@Column(name = "tipo_tour")
	private String typeTour;
    
    @ManyToOne
    @JoinColumn(name = "temporarySale_id")
    @JsonBackReference 		//!importante para serializacion y no generar bucle infinito
    private TemporarySale temporarySale;

	public SaleItem() {
		super();
	}


	public Integer getItemSaleId() {
		return itemSaleId;
	}


	public void setItemSaleId(Integer itemSaleId) {
		this.itemSaleId = itemSaleId;
	}
	

	public boolean isAgreeDestination() {
		return agreeDestination;
	}


	public void setAgreeDestination(boolean agreeDestination) {
		this.agreeDestination = agreeDestination;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public Integer getCode() {
		return code;
	}


	public void setCode(Integer code) {
		this.code = code;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	
	public String getDestination() {
		return destination;
	}


	public void setDestination(String destination) {
		this.destination = destination;
	}


	public double getHourInit() {
		return hourInit;
	}


	public void setHourInit(double hourInit) {
		this.hourInit = hourInit;
	}

	
	public double getEndTimeTour() {
		return endTimeTour;
	}


	public void setEndTimeTour(double endTimeTour) {
		this.endTimeTour = endTimeTour;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public boolean isPromotion() {
		return isPromotion;
	}


	public void setPromotion(boolean isPromotion) {
		this.isPromotion = isPromotion;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPersonsQuantity() {
		return personsQuantity;
	}


	public void setPersonsQuantity(String personsQuantity) {
		this.personsQuantity = personsQuantity;
	}

	public Integer getQuantityAdults() {
		return quantityAdults;
	}


	public void setQuantityAdults(Integer quantityAdults) {
		this.quantityAdults = quantityAdults;
	}


	public Integer getQuantityMinors() {
		return quantityMinors;
	}


	public void setQuantityMinors(Integer quantityMinors) {
		this.quantityMinors = quantityMinors;
	}


	public Integer getQuantityChildrens() {
		return quantityChildrens;
	}


	public void setQuantityChildrens(Integer quantityChildrens) {
		this.quantityChildrens = quantityChildrens;
	}


	public Integer getQuantityBabys() {
		return quantityBabys;
	}


	public void setQuantityBabys(Integer quantityBabys) {
		this.quantityBabys = quantityBabys;
	}


	public Integer getProductId() {
		return productId;
	}


	public void setProductId(Integer productId) {
		this.productId = productId;
	}


	public int getQuantityCars() {
		return quantityCars;
	}


	public void setQuantityCars(int quantityCars) {
		this.quantityCars = quantityCars;
	}


	public String getReservedDate() {
		return reservedDate;
	}


	public void setReservedDate(String reservedDate) {
		this.reservedDate = reservedDate;
	}


	public String getSubcategory() {
		return subcategory;
	}


	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}


	public BigDecimal getSalePrice() {
		return salePrice;
	}


	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}


	public BigDecimal getSubtotal() {
		return subtotal;
	}


	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}


	public SaleWeb getSaleWeb() {
		return saleWeb;
	}


	public void setSaleWeb(SaleWeb saleWeb) {
		this.saleWeb = saleWeb;
	}


	public String getTypeTour() {
		return typeTour;
	}


	public void setTypeTour(String typeTour) {
		this.typeTour = typeTour;
	}
	
	
}
