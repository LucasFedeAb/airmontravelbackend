package com.airmont.dto;

import java.math.BigDecimal;

import com.airmont.models.entity.Product;

public class ProductDTO {
	private Integer id;
	private Integer code;
	private String category;
	private String subcategory;
	private String name;
	private String description;
	private String searchTag;
	private String season;
	private BigDecimal priceReal;
    private double percentageDiscount;
    private BigDecimal price;
    //private int stock;
	private boolean isPromotion;
	private boolean featured;
	private boolean isActive;
	private boolean newEntry;
	private String imageMain;

    // Constructor
    
    public ProductDTO() {
    }

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.code = product.getCode();
        this.category = product.getCategory();
        this.subcategory = product.getSubcategory();
        this.name = product.getName();
        this.description = product.getDescription();
        this.searchTag = product.getSearchTag();
        this.season = product.getSeason();
        this.priceReal = product.getPriceReal();
        this.percentageDiscount = product.getPercentageDiscount();
        this.price = product.getPrice();
//        this.stock = product.getStock();
        this.isPromotion = product.isPromotion();
        this.featured = product.isFeatured();
        this.isActive = product.isActive();
        this.newEntry = product.isNewEntry();
        this.imageMain = product.getImageMain();
        
     // Registro de producto creado
        System.out.println("Se creó un nuevo ProductoDTO a partir de Producto: " + product.getId());
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
//	public int getStock() {
//		return stock;
//	}
//
//	public void setStock(int stock) {
//		this.stock = stock;
//	}
	
	public String getSearchTag() {
		return searchTag;
	}

	public void setSearchTag(String searchTag) {
		this.searchTag = searchTag;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public BigDecimal getPriceReal() {
		return priceReal;
	}

	public void setPriceReal(BigDecimal priceReal) {
		this.priceReal = priceReal;
	}
	
	public double getPercentageDiscount() {
		return percentageDiscount;
	}

	public void setPercentageDiscount(double percentageDiscount) {
		this.percentageDiscount = percentageDiscount;
	}
	
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public boolean isPromotion() {
		return isPromotion;
	}

	public void setPromotion(boolean isPromotion) {
		this.isPromotion = isPromotion;
	}
	
	public boolean isFeatured() {
		return featured;
	}

	public void setFeatured(boolean featured) {
		this.featured = featured;
	}
	

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isNewEntry() {
		return newEntry;
	}

	public void setNewEntry(boolean newEntry) {
		this.newEntry = newEntry;
	}
	
	public String getImageMain() {
		return imageMain;
	}

	public void setImageMain(String imageMain) {
		this.imageMain = imageMain;
	}
 
}
