package com.airmont.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airmont.models.entity.Store;
import com.airmont.services.StoreService;

@RestController
@RequestMapping("/tienda")
public class StoreController {
	
	@Autowired
    private StoreService storeService;

    // Obtener todas las tiendas
    @GetMapping("/")
    public List<Store> getAllStores() {
        return storeService.getAllStores();
    }

    // Obtener una tienda por ID
    @GetMapping("/id/{id}")
    public ResponseEntity<Store> getStoreById(@PathVariable Long id) {
        Store store = storeService.getStoreById(id);
        if (store != null) {
            return ResponseEntity.ok(store);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear una nueva tienda
    @PostMapping("/crear")
    public ResponseEntity<Store> createStore(@RequestBody Store store) {
        Store createdStore = storeService.createStore(store);
        return ResponseEntity.ok(createdStore);
    }

    // Actualizar una tienda existente
    @PutMapping("/{id}/actualizar")
    public ResponseEntity<Store> updateStore(@PathVariable Long id, @RequestBody Store storeDetails) {
        Store updatedStore = storeService.updateStore(id, storeDetails);
        if (updatedStore != null) {
            return ResponseEntity.ok(updatedStore);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar una tienda por ID
    @DeleteMapping("/{id}/eliminar")
    public ResponseEntity<Void> deleteStore(@PathVariable Long id) {
        boolean isDeleted = storeService.deleteStore(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}