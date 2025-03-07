package com.airmont.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.airmont.models.entity.Client;
import com.airmont.services.ClientService;

@RestController
@RequestMapping("/clientes")
public class ClientController {
    @Autowired
    private ClientService clientService;

    
    // Crear nuevo Cliente 
    @PostMapping(value = "/crear", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
		try {
			clientService.createClient(client);
			return new ResponseEntity<>(client, HttpStatus.CREATED); // Codigo 201

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Error 500
			
		}
	}
    
    // Actualizar cliente
    @PutMapping(value="/{id}/actualizar", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Client> updateClient(@PathVariable("id") Integer dni, @RequestBody Client client) {
    	Client clientUpdated = clientService.updateClientByDni(dni, client);
    	
			if (clientUpdated != null) {
				return new ResponseEntity<>(client, HttpStatus.OK); // Codigo 200
			} else {
				return new ResponseEntity<>(client, HttpStatus.NOT_FOUND); // Codigo 404
			}
    }
    
    // Obtener todos los clientes
    @GetMapping (value = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Client>> listClients() {
		try {
			List<Client> clients = clientService.getAllClients();
			return new ResponseEntity<>(clients, HttpStatus.OK); // Codigo 200

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Error 500
		}
	}
    
    // Obtener cliente segun dni
    @GetMapping(value = "/cliente/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Client> getClientByDNI(@PathVariable("id") Integer dni) {
		try {
			Client client = clientService.getClientByDni(dni);
			if (client != null) {
				return new ResponseEntity<>(client, HttpStatus.OK); // Codigo 200
			} else {
				return new ResponseEntity<>(client, HttpStatus.NOT_FOUND); // Codigo 404
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Error 500
		}

	}
    
    // Eliminar cliente segund dni
    @DeleteMapping(value = "/{id}/eliminar")
	public ResponseEntity<Void> deleteClient(@PathVariable("id") Integer dni) {
    	try {
    		boolean deletedClient = clientService.deleteClientByDNI(dni);
    			if (deletedClient) {
    				return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Codigo 204
    			} else {
    				return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Error 404
    			}
    	} catch(Exception e) {
    		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Error 500
    		}
    	}
    
}