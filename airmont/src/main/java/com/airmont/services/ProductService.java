package com.airmont.services;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.airmont.dto.ProductDTO;
import com.airmont.models.entity.Product;
import com.airmont.repositories.ProductRepository;

@Service
public class ProductService {
	
	@Autowired 
	private DateTimeConverterService dateTimeConverterService;
	
	@Autowired
    private ProductRepository productRepository;
	
	@Autowired
    private SimpMessagingTemplate messagingTemplate;
	
	public Product getProductById(Integer id) {
		return productRepository.findById(id).orElse(null);
	}
	
	public ProductDTO getProductDTOById(Integer id) {
	    Product product = productRepository.findById(id).orElse(null);
	    if (product != null) {
	        return new ProductDTO(product);
	    } else {
	        return null;
	    }
	}
	
	public Product getProductByBarCode(Integer code) {
		return productRepository.findByCode(code);
	}
	
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    //Pagination
    public Page<Product> getAllProductsPagination(Pageable pageable) {
    	return productRepository.findAll(pageable);
    }
    
    public Page<Product> getProductsByCategory(String category, Pageable pageable) {
        return productRepository.findByCategory(category, pageable);
    }
    
    public Page<Product> getProductsInPromotion(Boolean isPromotion, Pageable pageable) {
        return productRepository.findByIsPromotion(isPromotion, pageable);
    }
    
    public Page<Product> getProductsNewIn(Boolean newEntry, Pageable pageable) {
        return productRepository.findByNewEntry(newEntry, pageable);
    }
    
    public Page<Product> getProductsFeatured(Boolean isFeatured, Pageable pageable) {
        return productRepository.findByFeatured(isFeatured, pageable);
    }
    
    public Page<Product> getProductsByCategoryAndSubcategory(String category, String subcategory, Pageable pageable) {
        return productRepository.findByCategoryAndSubcategory(category, subcategory, pageable);
    }
    
    public Page<Product> searchProducts(String keyword, Pageable pageable) {
        return productRepository.searchProducts(keyword, pageable);
    }

    @Transactional
    public Product createProduct(Product product) {
        // Validaciones
        validateProduct(product);

        // Evitar duplicados por código de barra
        if (productRepository.existsByCode(product.getCode())) {
            throw new IllegalArgumentException("El código de barra ya existe.");
        }
        
        // Obtener fecha Creacion a traves de Java
        ZonedDateTime currentDateTime = dateTimeConverterService.getCurrentDateTime();
		String formattedDate = dateTimeConverterService.formatterDateTime(currentDateTime);
		product.setDateCreation(formattedDate);
        // Establecer valores por defecto si es necesario
        product.setDateCreation(product.getDateCreation() == null ? formattedDate : product.getDateCreation());
        product.setDateModified(formattedDate);

        // Guardar producto
        return productRepository.save(product);
    }

    private void validateProduct(Product product) {
        if (Objects.isNull(product.getName()) || product.getName().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío.");
        }

        if (Objects.isNull(product.getCategory()) || product.getCategory().isEmpty()) {
            throw new IllegalArgumentException("La categoría no puede estar vacía.");
        }

        if (Objects.isNull(product.getPrice()) || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a cero.");
        }

        if (Objects.isNull(product.getMinAge()) || product.getMinAge() < 0) {
            throw new IllegalArgumentException("La edad mínima no puede ser negativa.");
        }

        if (Objects.isNull(product.getStartTimeReserve()) || Objects.isNull(product.getEndTimeReserve())) {
            throw new IllegalArgumentException("Las horas de inicio reserva y fin reserva son obligatorias.");
        }
        
        if (Objects.isNull(product.getStartTime()) || Objects.isNull(product.getEndTime())) {
            throw new IllegalArgumentException("Las horas de inicio tour y fin tour son obligatorias.");
        }

//        if (product.getStartTime() >= product.getEndTime()) {
//            throw new IllegalArgumentException("La hora de inicio debe ser anterior a la hora de fin.");
//        }
    }
    
	@Transactional
    public Product updateProductById(Integer id, Product product) {
    	try {
			if (productRepository.existsById(id)) {
				
				Product existingProduct = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
				
				product.setId(id);
				product.setDateCreation(existingProduct.getDateCreation());
				
				System.out.println("Hs inicio reserva" + product.getStartTimeReserve());
				System.out.println("Hs fin reserva" + product.getEndTimeReserve());
				
		        // Obtener fecha Creacion a traves de Java
				ZonedDateTime cuurentDateTime = dateTimeConverterService.getCurrentDateTime();
				String formattedDate = dateTimeConverterService.formatterDateTime(cuurentDateTime);
				product.setDateModified(formattedDate);
                Product updatedProduct = productRepository.save(product);
                
                // Enviar notificación a todos los suscriptores sobre el producto actualizado
                sendProduct(updatedProduct);
                
                return updatedProduct;
			}
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
    	return null;
    }
	
	 // Método para enviar el producto actualizado a los suscriptores    
    public void sendProduct(Product updatedProduct) {
        // Enviar directamente el objeto, será convertido a JSON automáticamente
        messagingTemplate.convertAndSend("/topic/product", updatedProduct);
    }
    
    public boolean deleteProductById(Integer id) {
		try {
			productRepository.deleteById(id);
			return true;
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
	}
}
