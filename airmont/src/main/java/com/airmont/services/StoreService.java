package com.airmont.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airmont.models.entity.Store;
import com.airmont.repositories.StoreRepository;

@Service
public class StoreService {
	
	@Autowired
    private StoreRepository storeRepository;


    // Método para obtener todas las tiendas
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    // Método para obtener una tienda por ID
    public Store getStoreById(Long id) {
        Optional<Store> store = storeRepository.findById(id);
        return store.orElse(null);
    }

    // Método para crear una nueva tienda
    public Store createStore(Store store) {
        return storeRepository.save(store);
    }

    // Método para actualizar una tienda existente
    public Store updateStore(Long id, Store storeDetails) {
        Optional<Store> storeOptional = storeRepository.findById(id);
        if (storeOptional.isPresent()) {
            Store store = storeOptional.get();
            store.setName(storeDetails.getName());
            store.setLegalName(storeDetails.getLegalName());
            store.setLegalFile(storeDetails.getLegalFile());
            store.setCuit(storeDetails.getCuit());
            store.setAddress(storeDetails.getAddress());
            store.setCity(storeDetails.getCity());
            store.setProvince(storeDetails.getProvince());
            store.setPostalCode(storeDetails.getPostalCode());
            store.setPhone(storeDetails.getPhone());
            store.setEmail(storeDetails.getEmail());
            store.setCbu(storeDetails.getCbu());
            store.setAlias(storeDetails.getAlias());
            store.setBankTransferName(storeDetails.getBankTransferName());
            store.setNameTransferRecipient(storeDetails.getNameTransferRecipient());
            store.setCuitTransferRecipient(storeDetails.getCuitTransferRecipient());
            store.setLogo(storeDetails.getLogo());
            store.setSecondaryLogo(storeDetails.getSecondaryLogo());
            store.setOpeningDate(storeDetails.getOpeningDate());
            store.setStoreHours(storeDetails.getStoreHours());
            store.setWhatsappNumber(storeDetails.getWhatsappNumber());
            store.setInstagram(storeDetails.getInstagram());
            store.setFacebook(storeDetails.getFacebook());
            return storeRepository.save(store);
        } else {
            return null; // O puedes lanzar una excepción
        }
    }

    // Método para eliminar una tienda por ID
    public boolean deleteStore(Long id) {
        Optional<Store> store = storeRepository.findById(id);
        if (store.isPresent()) {
            storeRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
