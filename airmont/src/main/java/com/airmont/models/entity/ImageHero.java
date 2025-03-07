package com.airmont.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ImageHero {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Column(name = "url_web", columnDefinition = "TEXT")
    private String urlWeb; 
	@Column(name = "url_mobile", columnDefinition = "TEXT")
    private String urlMobile;
	@Column(name = "orden", unique= true)
    private Integer orderNumber;
	@Column(name = "categoria")
    private String category;
	
	public ImageHero() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ImageHero(Long id, String urlWeb, String urlMobile, Integer orderNumber, String category) {
		super();
		this.id = id;
		this.urlWeb = urlWeb;
		this.urlMobile = urlMobile;
		this.orderNumber = orderNumber;
		this.category = category;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrlWeb() {
		return urlWeb;
	}

	public void setUrlWeb(String urlWeb) {
		this.urlWeb = urlWeb;
	}

	public String getUrlMobile() {
		return urlMobile;
	}

	public void setUrlMobile(String urlMobile) {
		this.urlMobile = urlMobile;
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
}
