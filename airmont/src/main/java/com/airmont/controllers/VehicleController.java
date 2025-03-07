package com.airmont.controllers;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.airmont.models.entity.Vehicle;
import com.airmont.services.VehicleService;

@RestController
@RequestMapping("/vehiculos")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping
    public List<Vehicle> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long id) {
        return vehicleService.getVehicleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/estadisticas")
    public ResponseEntity<Map<String, Integer>> getVehicleStats() {
    	List<Vehicle> allVehicles = vehicleService.getAllVehicles();

        int totalSeats = allVehicles.stream()
                                    .filter(Vehicle::isAvailable)
                                    .mapToInt(Vehicle::getAvailableSeats)
                                    .sum();
        int availableVehicles = (int) allVehicles.stream()
                .filter(Vehicle::isAvailable)
                .count();

        Map<String, Integer> stats = new HashMap<>();
        stats.put("totalSeats", totalSeats);
        stats.put("availableVehicles", availableVehicles);

        return ResponseEntity.ok(stats);
    }


    @PostMapping("/crear")
    public Vehicle createVehicle(@RequestBody Vehicle vehicle) {
        return vehicleService.createVehicle(vehicle);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Long id, @RequestBody Vehicle updatedVehicle) {
        return ResponseEntity.ok(vehicleService.updateVehicle(id, updatedVehicle));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }
}
