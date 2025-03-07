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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airmont.models.entity.Subcategory;
import com.airmont.services.SubcategoryService;

@RestController
@RequestMapping("/subcategorias")
public class SubcategoryController {
	
	@Autowired
	private SubcategoryService subcategoryService;
	 	
	 	//Obtener todas las subcategorias
	    @GetMapping (value = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
		public ResponseEntity<List<Subcategory>> getAllSubCategories() {
			try {
				List<Subcategory> subcategories = subcategoryService.getAllSubcategories();
				return new ResponseEntity<>(subcategories, HttpStatus.OK); // Codigo 200

			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Error 500
			}

		}
	    
	    //Obtener todas las subcategorias activas
	    @GetMapping (value = "/activas", produces = { MediaType.APPLICATION_JSON_VALUE })
		public ResponseEntity<List<Subcategory>> getSubcategoriesActives() {
			try {
				List<Subcategory> subcategoriesActives = subcategoryService.getSubcategoriesActives();
				return new ResponseEntity<>(subcategoriesActives, HttpStatus.OK); // Codigo 200

			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Error 500
			}
		}

	    //Obtener subcategoria segun id
	    @GetMapping(value = "/subcategoria/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
		public ResponseEntity<Subcategory> getSubcategoryById(@PathVariable("id") Integer id) {
			try {
				Subcategory subcategory = subcategoryService.getSubcategoryById(id);
				if (subcategory != null) {
					return new ResponseEntity<>(subcategory, HttpStatus.OK); // Codigo 200
				} else {
					return new ResponseEntity<>(subcategory, HttpStatus.NOT_FOUND); // Codigo 404
				}
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Error 500
			}

		}
	    
	    //Crear nueva Subcategoria
	    @PostMapping(value = "/crear", consumes = { MediaType.APPLICATION_JSON_VALUE })
	    public ResponseEntity<Subcategory> createSubcategory(@RequestBody Subcategory subcategory) {
	    	subcategoryService.createSubcategory(subcategory);
			return new ResponseEntity<>(subcategory, HttpStatus.CREATED); // Codigo 201
		}

	 // Eliminar Subcategoria segun id
	    @DeleteMapping(value = "/{id}/eliminar")
		public ResponseEntity<Void> deleteCategoryById(@PathVariable("id") Integer id) {
			boolean deletedSubcategory = subcategoryService.deleteSubcategoryById(id);
			if (deletedSubcategory) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Codigo 204
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Error 404
			}
		}

}
