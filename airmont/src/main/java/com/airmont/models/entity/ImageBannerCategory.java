package com.airmont.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ImageBannerCategory {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Column(name = "url", columnDefinition = "TEXT")
    private String url;
	@Column(name = "orden", unique= true)
    private Integer orderNumber;
	@Column(name = "categoria")
    private String category;
	@Column(name = "subcategoria")
	private String subCategory;
	
	public ImageBannerCategory() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public ImageBannerCategory(Long id, String url, Integer orderNumber, String category, String subCategory) {
		super();
		this.id = id;
		this.url = url;
		this.orderNumber = orderNumber;
		this.category = category;
		this.subCategory = subCategory;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

}