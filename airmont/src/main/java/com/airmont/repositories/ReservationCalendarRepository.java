package com.airmont.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airmont.models.entity.ReservationCalendar;

public interface ReservationCalendarRepository extends JpaRepository<ReservationCalendar, Long> {

    // Obtener fechas desde el día actual en adelante
    List<ReservationCalendar> findByDateGreaterThanEqual(LocalDate date);
    
 // Encontrar la última fecha registrada en el calendario
    ReservationCalendar findTopByOrderByDateDesc();
    
 // Eliminar todas las fechas del calendario
    void deleteAll();

    // Obtener una fecha específica
    ReservationCalendar findByDate(LocalDate date);
    
    
}

