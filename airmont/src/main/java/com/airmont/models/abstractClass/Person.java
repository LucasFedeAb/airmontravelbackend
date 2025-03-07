package com.airmont.models.abstractClass;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Person {
	
	@Column(name = "Creado:")
    private  String created;
	
	@Column(name = "Modificado:")
    private  String modified;
	
	@Column(name = "Dni", unique = true)
    private Integer dni;

	@Column(name = "Nombre")
    private String firstName;

    @Column(name = "Apellido")
    private String lastName;

    @Column(name = "Email")
    private String email;
    
    @Column(name = "Ciudad")
	private String city;
    
    public Person () {
    	super();
    }    
    
    public Person(String created, String modified, Integer dni, String firstName, String lastName, String email, String city) {
		this.created = created;
		this.modified = modified;
		this.dni = dni;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.city = city;
	}
    
    //Getters y Setters
    public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}
	
    
	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}

	public Integer getDni() {
		return dni;
	}

	public void setDni(Integer dni) {
		this.dni = dni;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
