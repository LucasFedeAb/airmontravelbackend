package com.airmont.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.airmont.models.entity.Category;
import com.airmont.repositories.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    
    public Category getCategoryById(Integer id) {
		return categoryRepository.findById(id).orElse(null);
	}
    
    public Category getCategoryByName(String name) {
		return categoryRepository.findByName(name);
	}
    
    public List<Category> getCategoriesActives() {
        return categoryRepository.findByActiveTrue();
    }
    
    public List<Category> getActiveCategoriesWithProducts() {
        return categoryRepository.findCategoriesWithProductsOrEmptySubcategories();
//        return categoryRepository.findActiveCategoriesWithProducts();
    }

    public Category createCategory(Category category) {
    	
        return categoryRepository.save(category);
    }
    
    public Category updateCategoryById(Integer id, Category category) {
		try {
			Category existingCategory = categoryRepository.findById(id).orElse(null);
			if (existingCategory != null) {
				category.setId(id);
				return categoryRepository.save(category);
			}
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		return null;
	}
    
    public boolean deleteCategoryById(Integer id) {
		try {
			categoryRepository.deleteById(id);
			return true;
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
	}
}
