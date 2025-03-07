package com.airmont.dto;

import java.util.List;

import com.airmont.models.entity.Category;
import com.airmont.models.entity.Subcategory;

public class CategoryDTO {
	
	private String name;
	private boolean active;
	private List <Subcategory> subcategories;
	public CategoryDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CategoryDTO(String name, boolean active, Category category) {
		super();
		this.name = name;
		this.active = active;
		this.subcategories = category.getSubcategories();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public List<Subcategory> getSubcategories() {
		return subcategories;
	}
	public void setSubcategories(List<Subcategory> subcategories) {
		this.subcategories = subcategories;
	}

}
