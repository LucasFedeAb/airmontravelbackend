package com.airmont.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.airmont.dto.ReservedTimeRange;
import com.airmont.dto.UpdateAvailabilityRequest;
import com.airmont.models.entity.ReservationCalendar;
import com.airmont.services.ReservationCalendarService;

@RestController
@RequestMapping("/api/calendar")
public class ReservationCalendarController {

    @Autowired
    private ReservationCalendarService calendarService;

    // Inicializar el calendario
    @PostMapping("/initialize")
    public String initializeCalendar(@RequestParam int daysInFuture) {
        calendarService.initializeCalendar(daysInFuture);
        return "Calendario inicializado con " + daysInFuture + " días.";
    }
    
    // Endpoint para agregar días al calendario ya inicializado
    @PostMapping("/add-days")
    public String addDays(@RequestParam int daysToAdd) {
        return calendarService.addDaysToCalendar(daysToAdd);
    }
    
 // Endpoint para resetear el calendario desde el día actual
    @PostMapping("/reset")
    public String resetCalendar(@RequestParam int daysInFuture) {
        return calendarService.resetCalendar(daysInFuture);
    }

    // Reservar una fecha manualmente
    @PostMapping("/reserve")
    public String reserveDate(@RequestParam String date, @RequestParam(required = false) String reason) {
        LocalDate localDate = LocalDate.parse(date); // Convertir String a LocalDate
        calendarService.reserveDateManually(localDate, reason);
        return "Fecha " + date + " reservada con razón: " + reason;
    }
       
 // Endpoint para actualizar la disponibilidad de una fecha manualmente
    @PutMapping("/update-availability")
    public String updateAvailability(@RequestBody UpdateAvailabilityRequest request) {
        // Convertir la fecha del formato String a LocalDate (si es necesario)
        LocalDate localDate = request.getDate();
        boolean available = request.isAvailable();
        String reason = request.getReason();
        List<ReservedTimeRange> reservedTimeRanges = request.getReservedTimeRanges();
        BigDecimal totalSaleManual = request.getTotalSaleManual();
        boolean clearPrevData = request.isClearPrevData();

        return calendarService.updateDateAvailability(localDate, available, reason, reservedTimeRanges, totalSaleManual, clearPrevData);
    }

    // Obtener fechas disponibles
    @GetMapping("/available")
    public List<ReservationCalendar> getAvailableDates() {
        return calendarService.getAvailableDates();
    }

    // Actualizar la disponibilidad basada en ventas
    @PostMapping("/update-from-sales")
    public String updateFromSales() {
        calendarService.updateAvailabilityFromSales();
        return "Disponibilidad actualizada basada en ventas.";
    }

    // Obtener todas las fechas del calendario
    @GetMapping("/all")
    public List<ReservationCalendar> getAllDates() {
        return calendarService.getAllDates();
    }
}

