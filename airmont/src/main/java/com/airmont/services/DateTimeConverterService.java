package com.airmont.services;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

@Service
public class DateTimeConverterService {
	
	
	// Método para obtener la fecha y hora actual del sistema
    public ZonedDateTime getCurrentDateTime() {
        return ZonedDateTime.now();  // Obtener la fecha y hora actual del sistema
    }
    
    // Método para formatear la fecha y hora
    public String formatterDateTime(ZonedDateTime dateTime) {
        // Formateador para fecha y hora
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm:ss");
        // Formatear la fecha y hora usando el formateador
        return dateTime.format(formatter);
    }
}
