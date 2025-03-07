package com.airmont.models.entity;

import com.airmont.models.abstractClass.Person;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "clientes")
public class Client extends Person {
		
		@Id
		@Column(unique = true)
		private Integer id;
	  	@Column(name = "Telefono")
	  	private Long phone;
	  	@Column(name = "Direccion")
		private String address;
	  	@Column(name = "Código Postal")
		private String postalCode;
	  	
		
	  	// Constructor
	  	public Client () {
	    	super();
	    }
	  	
		public Client (String created, String modified, Integer dni, String firstName, String lastName, String email, String city, Long phone, String address, String postalCode) {
	    	super(created, modified, dni, firstName, lastName, email, city);
	    	this.id = dni;
	        this.phone = phone;
	        this.address = address;
	        this.postalCode = postalCode;
	    }
	    
		// Getters y Setters
	    public Integer getId() {
			return id;
		}


		public void setId(Integer id) {
			this.id = id;
		}


		public Long getPhone() {
			return phone;
		}


		public void setPhone(Long phone) {
			this.phone = phone;
		}


		public String getAddress() {
			return address;
		}


		public void setAddress(String address) {
			this.address = address;
		}

		public String getPostalCode() {
			return postalCode;
		}

		public void setPostalCode(String postalCode) {
			this.postalCode = postalCode;
		}
		
}
