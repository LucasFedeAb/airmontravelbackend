package com.airmont.controllers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.airmont.models.entity.Product;
import com.airmont.models.entity.ProductAvailability;
import com.airmont.repositories.ProductRepository;
import com.airmont.services.ProductAvailabilityService;
import com.airmont.services.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/productos")
public class ProductController {
	
	@Autowired
    private ProductService productService;
	@Autowired
    private ProductRepository productRepository;
	
	@Autowired
    private ProductAvailabilityService availabilityService;
	
	//Crear Producto
	@PostMapping(value = "/crear", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        try {
            Product createdProduct = productService.createProduct(product);
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED); // Codigo 201
        } catch (IllegalArgumentException e) {
        	System.out.println("Error al crear producto " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // Codigo 400
        }
    }
	
	// Actualizar Producto
    @PutMapping(value = "/{id}/editar", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Product> updateProduct(@PathVariable("id") Integer id, @RequestBody Product product) {
    	Product productUpdated = productService.updateProductById(id, product);
		if (productUpdated != null) {
			return new ResponseEntity<>(productUpdated, HttpStatus.OK); // Codigo 200
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Error 404
		}
	}
    
 // Eliminar Producto segun id
    @DeleteMapping(value = "/{id}/eliminar")
	public ResponseEntity<Void> deleteProduct(@PathVariable("id") Integer id) {
		boolean deletedProduct = productService.deleteProductById(id);
		if (deletedProduct) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Codigo 204
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Error 404
		}
	}
    
 // Obtener Producto segun id
    @GetMapping(value = "/producto/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Product> getProductById(@PathVariable("id") Integer id) {
		try {
			Product product = productService.getProductById(id);
			if (product != null) {
				return new ResponseEntity<>(product, HttpStatus.OK); // Codigo 200
			} else {
				return new ResponseEntity<>(product, HttpStatus.NOT_FOUND); // Codigo 404
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Error 500
		}

	}
    
    @GetMapping("/prod-ids")
    public ResponseEntity<List<Product>> getProductsByIds(@RequestParam String ids) {
        List<Integer> idList = Arrays.stream(ids.split(","))
                                     .map(Integer::parseInt)
                                     .collect(Collectors.toList());
        List<Product> products = productRepository.findAllById(idList);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    
    @GetMapping("/existeCodigo/{code}")
	public ResponseEntity<Map<String, Boolean>> existeCodigo(@PathVariable Integer code) {
	    boolean exists = productRepository.existsByCode(code); // Método para verificar si existe
	    Map<String, Boolean> response = new HashMap<>();
	    response.put("exists", exists);
	    return ResponseEntity.ok(response);
	}
    
    //OBTENER PRODUCTOS CON PAGINACION
    
    // Obtener todos los productos con paginación
    @GetMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Page<Product>> getAllProductsPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Product> products = productService.getAllProductsPagination(pageable);
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/categoria/{category}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Page<Product>> getProductsByCategory(
            @PathVariable("category") String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Product> productsByCategory = productService.getProductsByCategory(category, pageable);
            return new ResponseEntity<>(productsByCategory, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/categoria/{category}/subcategoria/{subcategory}", produces = { MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<Page<Product>> getProductsByCategoryAndSubcategory(
          @PathVariable("category") String category,
          @PathVariable("subcategory") String subcategory,
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "8") int size) {
      try {
    	  Pageable pageable = PageRequest.of(page, size);
          Page<Product> productsByCategoryAndSubcategory = productService.getProductsByCategoryAndSubcategory(category, subcategory, pageable);
          return new ResponseEntity<>(productsByCategoryAndSubcategory, HttpStatus.OK); // Código 200
      } catch (Exception e) {
          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Error 500
      }
  }
    @GetMapping (value = "/ofertas", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Page<Product>> getProductsInPromotion(
			@RequestParam(name = "isPromotion", required = false, defaultValue = "true") Boolean isPromotion,
			@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size) {
		try {
			Pageable pageable = PageRequest.of(page, size);
            Page<Product> productsInPromotion = productService.getProductsInPromotion(isPromotion, pageable);
            return new ResponseEntity<>(productsInPromotion, HttpStatus.OK); // Codigo 200
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Error 500
		}
	}
    
    @GetMapping (value = "/nuevos-ingresos", produces = { MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<Page<Product>> getProductsNewIn(
		  @RequestParam(name = "newEntry", required = false, defaultValue = "true") Boolean newEntry,
		  @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "8") int size) {
  	try {
  		Pageable pageable = PageRequest.of(page, size);
        Page<Product> productsNewIn = productService.getProductsNewIn(newEntry, pageable);
        return new ResponseEntity<>(productsNewIn, HttpStatus.OK); // Codigo 200 		
  	} catch (Exception e) {
  		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Error 500
  	}
  }
    
    @GetMapping (value = "/destacados", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Page<Product>> getProductsFeatured(
			@RequestParam(name = "isFeatured", required = false, defaultValue = "true") Boolean isFeatured,
			@RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "8") int size) {
		try {
			Pageable pageable = PageRequest.of(page, size);
	        Page<Product> productsFeatured = productService.getProductsFeatured(isFeatured, pageable);
	        return new ResponseEntity<>(productsFeatured, HttpStatus.OK); // Codigo 200 	
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Error 500
		}
	}
   
    //Obtener productos por input search
    @GetMapping("/search")
    public ResponseEntity<Page<Product>> searchProducts(
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "8") int size) {
    	try {
			Pageable pageable = PageRequest.of(page, size);
	        Page<Product> productsSearch = productService.searchProducts(keyword, pageable);
	        return new ResponseEntity<>(productsSearch, HttpStatus.OK); // Codigo 200 	
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Error 500
		}
    }
    
    @GetMapping(value = "/producto/code:{code}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Product> getProductByBarCode(@PathVariable("code") Integer code) {
		try {
			Product product = productService.getProductByBarCode(code);
			if (product != null) {
				return new ResponseEntity<>(product, HttpStatus.OK); // Codigo 200
			} else {
				return new ResponseEntity<>(product, HttpStatus.NOT_FOUND); // Codigo 404
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Error 500
		}
	}
    
    @GetMapping("/{productId}/availabilities")
    public ResponseEntity<List<ProductAvailability>> getAvailableDates(@PathVariable Long productId) {
        List<ProductAvailability> availabilities = availabilityService.getAvailableDates(productId);
        return ResponseEntity.ok(availabilities);
    }

    @PostMapping("/{productId}/reserve")
    public ResponseEntity<String> reserveDate(@PathVariable Long productId, @RequestBody LocalDate date) {
        boolean success = availabilityService.reserveDate(productId, date);
        if (success) {
            return ResponseEntity.ok("Reservation successful for date: " + date);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Date is not available.");
        }
    }
    
}

