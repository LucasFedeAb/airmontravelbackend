package com.airmont.models.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table (name = "Categorias")
public class Category {

		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;
		
	    @Column(unique = true, name = "Nombre")
	    private String name;
	    
	    @Column(name = "Activa")
	    private boolean active;
	    
	    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
	    @JsonManagedReference
	    private List<Subcategory> subcategories;

		public Category() {
			super();
			// TODO Auto-generated constructor stub
		}
		

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
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
