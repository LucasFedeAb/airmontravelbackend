package com.airmont.models.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "stores")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 150)
    private String legalName;
    
    @Column(name="legajo", nullable = true, length = 150)
    private String legalFile;

    @Column(length = 13)
    private String cuit;

    @Column(nullable = false, length = 150)
    private String address;

    @Column(nullable = false, length = 50)
    private String city;

    @Column(nullable = false, length = 50)
    private String province;

    @Column(nullable = false, length = 10)
    private String postalCode;

    @Column(nullable = false, length = 15)
    private String phone;

    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String cbu;
    
    @Column(nullable = false)
    private String alias;
    
    @Column(nullable = false)
    private String bankTransferName;
    
    @Column(nullable = false)
    private String nameTransferRecipient;
    
    @Column(nullable = false)
    private String cuitTransferRecipient;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String logo;
    
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String secondaryLogo;

    @Column(name = "opening_date")
    private LocalDate openingDate;
    
    @Column(name = "Horarios", columnDefinition = "TEXT")
    private String storeHours;
    
    @Column(nullable = true)
    private String whatsappNumber;
    
    @Column(nullable = true)
    private String instagram;
    
    @Column(nullable = true)
    private String facebook;

    // Default constructor
    public Store() {
    }

    // Constructor with parameters
	public Store(Long id, String name, String legalName, String cuit, String address, String city, String province,
			String postalCode, String phone, String email, String cbu, String alias, String bankTransferName,
			String nameTransferRecipient, String cuitTransferRecipient, String logo, String secondaryLogo,
			LocalDate openingDate, String storeHours, String whatsappNumber, String instagram, String facebook) {
		super();
		this.id = id;
		this.name = name;
		this.legalName = legalName;
		this.cuit = cuit;
		this.address = address;
		this.city = city;
		this.province = province;
		this.postalCode = postalCode;
		this.phone = phone;
		this.email = email;
		this.cbu = cbu;
		this.alias = alias;
		this.bankTransferName = bankTransferName;
		this.nameTransferRecipient = nameTransferRecipient;
		this.cuitTransferRecipient = cuitTransferRecipient;
		this.logo = logo;
		this.secondaryLogo = secondaryLogo;
		this.openingDate = openingDate;
		this.storeHours = storeHours;
		this.whatsappNumber = whatsappNumber;
		this.instagram = instagram;
		this.facebook = facebook;
	}
	
	
	// Getters and Setters
    public Long getId() {
        return id;
    }

	public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }
    
    public String getLegalFile() {
		return legalFile;
	}

	public void setLegalFile(String legalFile) {
		this.legalFile = legalFile;
	}

	public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCbu() {
		return cbu;
	}

	public void setCbu(String cbu) {
		this.cbu = cbu;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getBankTransferName() {
		return bankTransferName;
	}

	public void setBankTransferName(String bankTransferName) {
		this.bankTransferName = bankTransferName;
	}

	public String getNameTransferRecipient() {
		return nameTransferRecipient;
	}

	public void setNameTransferRecipient(String nameTransferRecipient) {
		this.nameTransferRecipient = nameTransferRecipient;
	}

	public String getCuitTransferRecipient() {
		return cuitTransferRecipient;
	}

	public void setCuitTransferRecipient(String cuitTransferRecipient) {
		this.cuitTransferRecipient = cuitTransferRecipient;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	public String getSecondaryLogo() {
		return secondaryLogo;
	}

	public void setSecondaryLogo(String secondaryLogo) {
		this.secondaryLogo = secondaryLogo;
	}

	public LocalDate getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(LocalDate openingDate) {
        this.openingDate = openingDate;
    }

	public String getStoreHours() {
		return storeHours;
	}

	public void setStoreHours(String storeHours) {
		this.storeHours = storeHours;
	}

	public String getWhatsappNumber() {
		return whatsappNumber;
	}

	public void setWhatsappNumber(String whatsappNumber) {
		this.whatsappNumber = whatsappNumber;
	}

	public String getInstagram() {
		return instagram;
	}

	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}
}
