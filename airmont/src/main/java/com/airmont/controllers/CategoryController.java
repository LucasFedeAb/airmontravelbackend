package com.airmont.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airmont.models.entity.Category;
import com.airmont.services.CategoryService;

@RestController
@RequestMapping("/categorias")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;

	 	
	 	//Obtener todas las categorias
	    @GetMapping (value = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
		public ResponseEntity<List<Category>> getAllCategories() {
			try {
				List<Category> categories = categoryService.getAllCategories();
				return new ResponseEntity<>(categories, HttpStatus.OK); // Codigo 200

			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Error 500
			}

	    }
	    
	  //Obtener todas las categorias activas
	    @GetMapping (value = "/activas", produces = { MediaType.APPLICATION_JSON_VALUE })
		public ResponseEntity<List<Category>> getCategoriesActives() {
			try {
				List<Category> categoriesActives = categoryService.getCategoriesActives();
				return new ResponseEntity<>(categoriesActives, HttpStatus.OK); // Codigo 200

			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Error 500
			}

		}
	    
	    @GetMapping(value = "/activas-con-productos", produces = { MediaType.APPLICATION_JSON_VALUE })
	    public ResponseEntity<List<Category>> getActiveCategoriesWithProducts() {
	        try {
	            List<Category> categoriesWithProducts = categoryService.getActiveCategoriesWithProducts();
	            return new ResponseEntity<>(categoriesWithProducts, HttpStatus.OK); // Código 200
	        } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Error 500
	        }
	    }
	    
//	    @GetMapping("/activas-con-stock")
//	    public ResponseEntity<List<CategoryWithProductsDTO>> getActiveCategoriesWithStock() {
//	        List<CategoryWithProductsDTO> categoriesWithStock = productService.getActiveCategoriesWithSubcategoriesInStock();
//	        return new ResponseEntity<>(categoriesWithStock, HttpStatus.OK);
//	    }

	    //Obtener categoria segun id
	    @GetMapping(value = "/categoria/{name}", produces = { MediaType.APPLICATION_JSON_VALUE })
		public ResponseEntity<Category> getCategoryById(@PathVariable("id") String name) {
			try {
				Category category = categoryService.getCategoryByName(name);
				if (category != null) {
					return new ResponseEntity<>(category, HttpStatus.OK); // Codigo 200
				} else {
					return new ResponseEntity<>(category, HttpStatus.NOT_FOUND); // Codigo 404
				}
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Error 500
			}

		}
	    
	    //Crear nueva Categoria
	    @PostMapping(value = "/crear", consumes = { MediaType.APPLICATION_JSON_VALUE })
	    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
	    	categoryService.createCategory(category);
			return new ResponseEntity<>(category, HttpStatus.CREATED); // Codigo 201
		}
	    
	 // Actualizar cliente
	    @PutMapping(value="/{id}/actualizar", consumes = { MediaType.APPLICATION_JSON_VALUE })
	    public ResponseEntity<Category> updateCategoryById(@PathVariable("id") Integer id, @RequestBody Category category) {
	    	Category categoryUpdated = categoryService.updateCategoryById(id, category);
	    	
				if (categoryUpdated != null) {
					return new ResponseEntity<>(category, HttpStatus.OK); // Codigo 200
				} else {
					return new ResponseEntity<>(category, HttpStatus.NOT_FOUND); // Codigo 404
				}
	    }

	 // Eliminar categoria segun id
	    @DeleteMapping(value = "/{id}/eliminar")
		public ResponseEntity<Void> deleteCategoryById(@PathVariable("id") Integer id) {
			boolean deletedCategory = categoryService.deleteCategoryById(id);
			if (deletedCategory) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Codigo 204
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Error 404
			}
		}

}
