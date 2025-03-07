package com.airmont.dto;

import java.util.List;

public class CategoryWithProductsDTO {
	private String categoryName;
    private List<String> subcategories;

    public CategoryWithProductsDTO(String categoryName, List<String> subcategories) {
        this.categoryName = categoryName;
        this.subcategories = subcategories;
    }

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<String> getSubcategories() {
		return subcategories;
	}

	public void setSubcategories(List<String> subcategories) {
		this.subcategories = subcategories;
	}
    
}
