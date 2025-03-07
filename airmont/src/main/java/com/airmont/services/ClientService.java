package com.airmont.services;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.airmont.models.entity.Client;
import com.airmont.repositories.ClientRepository;


@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private DateTimeConverterService dateTimeConverterService;
    
    public List<Client> getAllClients() {
		return clientRepository.findAll();
	}
	
	public Client getClientByDni(Integer dni) {
		return clientRepository.findById(dni).orElse(null);
	}
	
	public Client getClientById(Integer id) {
		return clientRepository.findById(id).orElse(null);
	}

	public Client createClient(Client client) {
	    Integer dni = client.getDni();
	    // Verificar si el cliente ya existe en la base de datos
	    Client existingClient = clientRepository.findById(dni).orElse(null);
	    try {
	    	if (existingClient != null) {
	    		throw new RuntimeException("El cliente con DNI " + dni + " ya existe en la base de datos.");
		    } else {
		        client.setId(dni);
		     // Obtener fecha Creacion a traves de Java
		        ZonedDateTime currentDateTime = dateTimeConverterService.getCurrentDateTime();
				String formattedDate = dateTimeConverterService.formatterDateTime(currentDateTime);
		        client.setCreated(formattedDate);
		        client.setModified(client.getCreated());
		        System.out.println("Cliente con DNI:" + dni + " creado con éxito");
		        return clientRepository.save(client);
		    }
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public Client updateClientByDni(Integer dni, Client client) {
		try {
			Client existingClient = clientRepository.findById(dni).orElse(null);
			if (existingClient != null) {
				// Mantener la fecha de creación existente
				client.setCreated(existingClient.getCreated());
				client.setDni(dni);
				client.setId(dni);
				
				// Obtener fecha Modificación a traves de Java
		        ZonedDateTime currentDateTime = dateTimeConverterService.getCurrentDateTime();
				String formattedDate = dateTimeConverterService.formatterDateTime(currentDateTime);
		        client.setModified(formattedDate);
				return clientRepository.save(client);
			}
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		return null;
	}
    
	public boolean deleteClientByDNI(Integer dni) {
		try {
			clientRepository.deleteById(dni);
			System.out.println("Cliente con DNI:" + dni + " eliminado con éxito");
			return true;
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
	}
}
