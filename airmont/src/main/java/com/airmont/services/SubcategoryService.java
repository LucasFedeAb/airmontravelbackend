package com.airmont.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.airmont.models.entity.Subcategory;
import com.airmont.repositories.SubcategoryRepository;

@Service
public class SubcategoryService {

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    public List<Subcategory> getAllSubcategories() {
        return subcategoryRepository.findAll();
    }
    
    public Subcategory getSubcategoryById(Integer id) {
		return subcategoryRepository.findById(id).orElse(null);
	}
    
    public List<Subcategory> getSubcategoriesActives() {
        return subcategoryRepository.findByActiveTrue();
    }

    public Subcategory createSubcategory(Subcategory subcategory) {
        return subcategoryRepository.save(subcategory);
    }

    public boolean deleteSubcategoryById(Integer id) {
		try {
			subcategoryRepository.deleteById(id);
			return true;
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
	}
}
