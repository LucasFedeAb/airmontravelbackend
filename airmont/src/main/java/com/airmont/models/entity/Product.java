package com.airmont.models.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.airmont.converter.ListToStringConverter;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table (name = "Productos")
public class Product {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	@Column(name = "Creado")
    private String dateCreation;
	@Column(name = "Modificado")
    private String dateModified;
	@Column(name = "Codigo_barra", unique = true)
	private Integer code;
	@Column(name = "Categoria")
	private String category;
	@Column(name = "Subcategoria", nullable = true)
	private String subcategory;
	@Column(name = "Nombre")
	private String name;
	@Column(name = "Temporada")
	private String season;
	@Lob
	@Column(name = "Descripcion", columnDefinition = "LONGTEXT")
	private String description;
	@Lob
	@Column(name = "Reseña_De_Lugar", columnDefinition = "LONGTEXT")
	private String placeReview;
	@Column(name = "Tag_Busqueda", columnDefinition = "LONGTEXT")
	private String searchTag;
	@Column(name = "Precio_Costo")
    private BigDecimal priceCost;
	@Column(name = "Precio_Real")
	private BigDecimal priceReal;
	@Column(name = "Precio_Venta")
    private BigDecimal price;
	@Column(name = "%_Descuento")
    private double percentageDiscount;
	@Column(name = "Oferta")
	private boolean isPromotion;
	@Column(name = "Destacado")
	private boolean featured;
	@Column(name = "Nuevo_Ingreso")
	private boolean newEntry;
	@Column(name = "Producto_Activo")
	private boolean isActive;
	@Column(name = "Imagen_Principal", columnDefinition = "TEXT")
	private String imageMain;
	@Column(name = "imagenes", columnDefinition = "LONGTEXT")
	@Convert(converter = ListToStringConverter.class)
	private List<String> images;
	
	//Especifico turismo
	@Column(name="Tipo_Tour")
    private String tourType;
	
	@Column(name="Lugar")
    private String location;
	
	@Column(name="Origen", nullable = false, length = 200)
    private String origin;
	
	@Column(name="convenir_destino")
	private boolean agreeDestination = false;
	
	@Column(name="convenir_horario")
	private boolean agreeHours = false;
	
	@Column(name="Destino", nullable = false, length = 200)
    private String destination;
	
	@Column(name="Hora_Inicio_Reserva", nullable = false)
    private double startTimeReserve;
	
	@Column(name="Hora_Fin_Reserva", nullable = false)
    private double endTimeReserve;
	
	@Column(name="Hora_Inicio", nullable = false)
    private double startTime;
	
	@Column(name="Hora_Fin", nullable = false)
    private double endTime;
	
	@Column(name="Duracion", nullable = false)
    private String duration;
	
	@Column(name="Dias", nullable = false)
    private String days;
	
	@ElementCollection
    @CollectionTable(name = "Días_Disponibles", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "day_of_week", nullable = false)
    private List<Integer> availableDays = new ArrayList<>(); // Lista de días disponibles (0-6)
	
	@Column(name="Dificultad", nullable = false)
    private String difficulty;
	
	@Column(name="Idioma", nullable = false)
    private String language;
	
	@Column(name="Edad_Minima", nullable = false)
    private int minAge;
	
	@Column(name="Url_Tour", nullable = true)
    private String urlTour;
	
	
	@ElementCollection
    @CollectionTable(name = "Servicios_Incluidos", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "service", nullable = false)
    private List<String> includedServices = new ArrayList<>();
	
	@ElementCollection
    @CollectionTable(name = "Servicios_No_Incluidos", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "service", nullable = false)
    private List<String> notIncludedServices = new ArrayList<>();
	
	@Column(name="Info_Adicional", nullable = true, length = 300)
    private String additionalInfo;
	
	@Column(name="Info_Importante", nullable = true, length = 300)
    private String importantInfo;

    @Column(name="Politica_Cancelacion", nullable = true, length = 300)
    private String cancellationPolicy;

    @Column(name="Disponible", nullable = false)
    private boolean isAvailable;
	
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Product(Integer id, String dateCreation, String dateModified, Integer code, String category,
			String subcategory, String name, String season, String description, String placeReview, String searchTag,
			BigDecimal priceCost, BigDecimal priceReal, BigDecimal price, double percentageDiscount,
			boolean isPromotion, boolean featured, boolean newEntry, boolean isActive, String imageMain,
			List<String> images, String tourType, String location, String origin, boolean agreeDestination, boolean agreeHours, 
			String destination, double startTime, double endTime, double startTimeReserve, double endTimeReserve, String duration, String days, String difficulty,
			String language, int minAge, String urlTour, List<String> includedServices,
			List<String> notIncludedServices, String additionalInfo, String importantInfo, String cancellationPolicy,
			boolean isAvailable) {
		super();
		this.id = id;
		this.dateCreation = dateCreation;
		this.dateModified = dateModified;
		this.code = code;
		this.category = category;
		this.subcategory = subcategory;
		this.name = name;
		this.season = season;
		this.description = description;
		this.placeReview = placeReview;
		this.searchTag = searchTag;
		this.priceCost = priceCost;
		this.priceReal = priceReal;
		this.price = price;
		this.percentageDiscount = percentageDiscount;
		this.isPromotion = isPromotion;
		this.featured = featured;
		this.newEntry = newEntry;
		this.isActive = isActive;
		this.imageMain = imageMain;
		this.images = images;
		this.tourType = tourType;
		this.location = location;
		this.origin = origin;
		this.agreeDestination = agreeDestination;
		this.agreeHours = agreeHours;
		this.destination = destination;
		this.startTimeReserve = startTimeReserve;
		this.endTimeReserve = endTimeReserve;
		this.startTime = startTime;
		this.endTime = endTime;
		this.duration = duration;
		this.days = days;
		this.difficulty = difficulty;
		this.language = language;
		this.minAge = minAge;
		this.urlTour = urlTour;
		this.includedServices = includedServices;
		this.notIncludedServices = notIncludedServices;
		this.additionalInfo = additionalInfo;
		this.importantInfo = importantInfo;
		this.cancellationPolicy = cancellationPolicy;
		this.isAvailable = isAvailable;
	}

	//Getters y Setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(String dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getDateModified() {
		return dateModified;
	}

	public void setDateModified(String dateModified) {
		this.dateModified = dateModified;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getDescription() {
		return description;
	}
	
	public String getPlaceReview() {
		return placeReview;
	}

	public void setPlaceReview(String placeReview) {
		this.placeReview = placeReview;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSearchTag() {
		return searchTag;
	}

	public void setSearchTag(String searchTag) {
		this.searchTag = searchTag;
	}

	public BigDecimal getPriceCost() {
		return priceCost;
	}

	public void setPriceCost(BigDecimal priceCost) {
		this.priceCost = priceCost;
	}

	public BigDecimal getPriceReal() {
		return priceReal;
	}

	public void setPriceReal(BigDecimal priceReal) {
		this.priceReal = priceReal;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public double getPercentageDiscount() {
		return percentageDiscount;
	}

	public void setPercentageDiscount(double percentageDiscount) {
		this.percentageDiscount = percentageDiscount;
	}

	public boolean isPromotion() {
		return isPromotion;
	}

	public void setPromotion(boolean isPromotion) {
		this.isPromotion = isPromotion;
	}

	public boolean isFeatured() {
		return featured;
	}

	public void setFeatured(boolean featured) {
		this.featured = featured;
	}

	public boolean isNewEntry() {
		return newEntry;
	}

	public void setNewEntry(boolean newEntry) {
		this.newEntry = newEntry;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getImageMain() {
		return imageMain;
	}

	public void setImageMain(String imageMain) {
		this.imageMain = imageMain;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public String getTourType() {
		return tourType;
	}

	public void setTourType(String tourType) {
		this.tourType = tourType;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	public boolean isAgreeDestination() {
		return agreeDestination;
	}

	public void setAgreeDestination(boolean agreeDestination) {
		this.agreeDestination = agreeDestination;
	}

	public boolean isAgreeHours() {
		return agreeHours;
	}

	public void setAgreeHours(boolean agreeHours) {
		this.agreeHours = agreeHours;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public double getStartTimeReserve() {
		return startTimeReserve;
	}

	public void setStartTimeReserve(double startTimeReserve) {
		this.startTimeReserve = startTimeReserve;
	}

	public double getEndTimeReserve() {
		return endTimeReserve;
	}

	public void setEndTimeReserve(double endTimeReserve) {
		this.endTimeReserve = endTimeReserve;
	}

	public double getStartTime() {
		return startTime;
	}

	public void setStartTime(double startTime) {
		this.startTime = startTime;
	}

	public double getEndTime() {
		return endTime;
	}

	public void setEndTime(double endTime) {
		this.endTime = endTime;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}
	
	public List<Integer> getAvailableDays() {
		return availableDays;
	}

	public void setAvailableDays(List<Integer> availableDays) {
		this.availableDays = availableDays;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public int getMinAge() {
		return minAge;
	}

	public void setMinAge(int minAge) {
		this.minAge = minAge;
	}
	
	public String getUrlTour() {
		return urlTour;
	}

	public void setUrlTour(String urlTour) {
		this.urlTour = urlTour;
	}

	public List<String> getIncludedServices() {
		return includedServices;
	}

	public void setIncludedServices(List<String> includedServices) {
		this.includedServices = includedServices;
	}

	public List<String> getNotIncludedServices() {
		return notIncludedServices;
	}

	public void setNotIncludedServices(List<String> notIncludedServices) {
		this.notIncludedServices = notIncludedServices;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public String getImportantInfo() {
		return importantInfo;
	}

	public void setImportantInfo(String importantInfo) {
		this.importantInfo = importantInfo;
	}

	public String getCancellationPolicy() {
		return cancellationPolicy;
	}

	public void setCancellationPolicy(String cancellationPolicy) {
		this.cancellationPolicy = cancellationPolicy;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

}
