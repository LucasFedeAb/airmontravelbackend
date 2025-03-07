package com.airmont.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ItemSaleDTO {
	
	private String category; //
    private Integer code; //
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private String date; //
    private double hourInit; //
    private double endTime;
    private String image; //
    private boolean isPromotion; //
    private String name; //
    private boolean agreeDestination;
    private String personsQuantity; //
    private Integer quantityAdults;
    private Integer quantityMinors;//
    private Integer quantityChildrens;//
    private Integer quantityBabys;
    private BigDecimal price;//
    private BigDecimal subtotal;
    private Integer productId; //
    private int quantity; //
    private String subcategory; //
    private String typeTour;
//    private ProductDTO product;
    
	public ItemSaleDTO() {
		super();
	}
	
//	public ItemSaleDTO( int quantity, String size, String color, ProductDTO product) {
//		super();
//		this.quantity = quantity;
//		this.size = size;
//		this.color = color;
//		this.product = product;
//	}
	
	public ItemSaleDTO(String category, Integer code, String date, double hourInit, double endTime, String image,
		boolean isPromotion, String name, boolean agreeDestination, String personsQuantity, Integer quantityAdults, 
		Integer quantityMinors, Integer quantityChildrens, Integer quantityBabys, BigDecimal price, BigDecimal subtotal, 
		Integer productId, int quantity, String subcategory, String typeTour) {
	super();
	this.category = category;
	this.code = code;
	this.date = date;
	this.hourInit = hourInit;
	this.endTime = endTime;
	this.image = image;
	this.isPromotion = isPromotion;
	this.name = name;
	this.agreeDestination = agreeDestination;
	this.personsQuantity = personsQuantity;
	this.quantityAdults = quantityAdults;
	this.quantityMinors = quantityMinors;
	this.quantityChildrens = quantityChildrens;
	this.quantityBabys = quantityBabys;
	this.price = price;
	this.subtotal = subtotal;
	this.productId = productId;
	this.quantity = quantity;
	this.subcategory = subcategory;
	this.typeTour = typeTour;
	}
	
	
	//Getter y Setters
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

	public double getHourInit() {
		return hourInit;
	}

	public void setHourInit(double hourInit) {
		this.hourInit = hourInit;
	}
	
	
	public double getEndTime() {
		return endTime;
	}

	public void setEndTime(double endTime) {
		this.endTime = endTime;
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
	
	public boolean isAgreeDestination() {
		return agreeDestination;
	}

	public void setAgreeDestination(boolean agreeDestination) {
		this.agreeDestination = agreeDestination;
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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}

	public String getTypeTour() {
		return typeTour;
	}

	public void setTypeTour(String typeTour) {
		this.typeTour = typeTour;
	}


	
//	public ProductDTO getProduct() {
//		return product;
//	}
//	public void setProduct(ProductDTO product) {
//		this.product = product;
//	}	
	
}
