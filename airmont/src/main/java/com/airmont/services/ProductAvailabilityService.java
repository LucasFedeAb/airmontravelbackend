package com.airmont.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airmont.models.entity.ProductAvailability;
import com.airmont.repositories.ProductAvailabilityRepository;

@Service
public class ProductAvailabilityService {

    @Autowired
    private ProductAvailabilityRepository availabilityRepository;

    public List<ProductAvailability> getAvailableDates(Long productId) {
        return availabilityRepository.findByProductIdAndIsReservedFalse(productId);
    }

    public boolean reserveDate(Long productId, LocalDate date) {
        ProductAvailability availability = availabilityRepository.findByProductIdAndAvailableDateAndIsReservedFalse(productId, date);

        if (availability != null) {
            availability.setReserved(true);
            availabilityRepository.save(availability);
            return true;
        }
        return false;
    }
}
