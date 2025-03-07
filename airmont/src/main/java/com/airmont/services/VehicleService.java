package com.airmont.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airmont.models.entity.Vehicle;
import com.airmont.repositories.VehicleRepository;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Optional<Vehicle> getVehicleById(Long id) {
        return vehicleRepository.findById(id);
    }

    public Vehicle createVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public Vehicle updateVehicle(Long id, Vehicle updatedVehicle) {
        return vehicleRepository.findById(id)
                .map(vehicle -> {
                    vehicle.setVehicleType(updatedVehicle.getVehicleType());
                    vehicle.setBrand(updatedVehicle.getBrand());
                    vehicle.setModel(updatedVehicle.getModel());
                    vehicle.setColor(updatedVehicle.getColor());
                    vehicle.setYear(updatedVehicle.getYear());
                    vehicle.setLicensePlate(updatedVehicle.getLicensePlate());
                    vehicle.setVehicleImage(updatedVehicle.getVehicleImage());
                    vehicle.setAvailableSeats(updatedVehicle.getAvailableSeats());
                    vehicle.setDriverFirstName(updatedVehicle.getDriverFirstName());
                    vehicle.setDriverLastName(updatedVehicle.getDriverLastName());
                    vehicle.setDriverDni(updatedVehicle.getDriverDni());
                    vehicle.setDriverPhoneNumber(updatedVehicle.getDriverPhoneNumber());
                    vehicle.setDriverWhatsapp(updatedVehicle.getDriverWhatsapp());
                    vehicle.setAvailable(updatedVehicle.isAvailable());
                    return vehicleRepository.save(vehicle);
                })
                .orElseGet(() -> {
                    updatedVehicle.setId(id);
                    return vehicleRepository.save(updatedVehicle);
                });
    }

    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }
}
