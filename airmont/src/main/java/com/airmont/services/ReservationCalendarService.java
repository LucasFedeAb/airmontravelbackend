package com.airmont.services;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airmont.dto.ReservedTimeRange;
import com.airmont.models.entity.ReservationCalendar;
import com.airmont.models.entity.SaleItem;
import com.airmont.repositories.ReservationCalendarRepository;
import com.airmont.repositories.SaleItemRepository;

import jakarta.transaction.Transactional;

@Service
public class ReservationCalendarService {

    @Autowired
    private ReservationCalendarRepository calendarRepository;

    @Autowired
    private SaleItemRepository saleItemRepository;

    // Inicializar el calendario desde el día actual hasta N días en el futuro
    public void initializeCalendar(int daysInFuture) {
        LocalDate today = LocalDate.now();

        List<ReservationCalendar> calendar = IntStream.range(0, daysInFuture)
                .mapToObj(i -> today.plusDays(i))
                .map(date -> new ReservationCalendar(date, true, null, null))
                .collect(Collectors.toList());

        calendarRepository.saveAll(calendar);
    }
    
 // Agregar días al calendario ya inicializado
    public String addDaysToCalendar(int daysToAdd) {
        // Encontrar la última fecha registrada en el calendario
        ReservationCalendar lastDateEntry = calendarRepository.findTopByOrderByDateDesc();

        // Si no hay fechas en el calendario, lanza un error
        if (lastDateEntry == null) {
            throw new RuntimeException("El calendario no está inicializado. Inicializa el calendario primero.");
        }

        LocalDate lastDate = lastDateEntry.getDate();
        LocalDate newStartDate = lastDate.plusDays(1); // Comenzar desde el día siguiente a la última fecha
        LocalDate newEndDate = newStartDate.plusDays(daysToAdd - 1);

        // Generar las nuevas fechas
        List<ReservationCalendar> newDates = newStartDate.datesUntil(newEndDate.plusDays(1))
                .map(date -> new ReservationCalendar(date, true, null, null)) // Todas las nuevas fechas están disponibles inicialmente
                .toList();

        // Guardar las nuevas fechas en el calendario
        calendarRepository.saveAll(newDates);

        return "Se agregaron " + daysToAdd + " días al calendario, desde " + newStartDate + " hasta " + newEndDate + ".";
    }
    
 // Resetear el calendario desde el día actual
    @Transactional
    public String resetCalendar(int daysInFuture) {
        // Eliminar todas las fechas existentes
        calendarRepository.deleteAll();
        
     // Verificar si la tabla está vacía
        if (calendarRepository.count() > 0) {
            throw new RuntimeException("No se pudieron eliminar todas las fechas del calendario.");
        }

        // Obtener el día actual
        LocalDate today = LocalDate.now();

        // Crear nuevas fechas desde hoy hasta el rango especificado
        List<ReservationCalendar> newDates = today.datesUntil(today.plusDays(daysInFuture))
                .map(date -> new ReservationCalendar(date, true, null, null)) // Todas las fechas disponibles inicialmente
                .toList();

        // Guardar las nuevas fechas en la base de datos
        calendarRepository.saveAll(newDates);

        return "El calendario se ha reseteado desde el día " + today + " hasta " + today.plusDays(daysInFuture - 1) + ".";
    }

    // Marcar una fecha como no disponible manualmente
    public void reserveDateManually(LocalDate date, String reason) {
        ReservationCalendar reservation = calendarRepository.findByDate(date);

        if (reservation == null) {
            throw new RuntimeException("La fecha " + date + " no está disponible en el calendario.");
        }

        reservation.setAvailable(false);
        reservation.setReason(reason);
        reservation.setTypeReserve("Cancelacíon Manual");
        reservation.setTotalSaleManual(BigDecimal.ZERO);
        calendarRepository.save(reservation);
    }

    // Actualizar disponibilidad basada en ventas (automático)
    public void updateAvailabilityFromSales() {
        // Obtener todas las fechas reservadas en ventas
        List<String> reservedDates = saleItemRepository.findAll().stream()
                .map(SaleItem::getReservedDate)
                .collect(Collectors.toList());

        // Actualizar las fechas del calendario
        reservedDates.forEach(dateStr -> {
            LocalDate date = LocalDate.parse(dateStr); // Convertir String a LocalDate
            ReservationCalendar reservation = calendarRepository.findByDate(date);

            if (reservation != null) {
            	System.out.print("");
                reservation.setAvailable(false);
                reservation.setReason("Reservada por venta");
                if (reservation.getTypeReserve() != null) {
                	if (reservation.getTypeReserve().equals("Manual") || reservation.getTypeReserve().equals("Venta Web y Manual")) {
                		reservation.setTypeReserve("Venta Web y Manual");
                    }
                } else {
                	reservation.setTypeReserve("Venta Web");
                }
                calendarRepository.save(reservation);
            }
        });
    }
    
	public String updateDateAvailability(LocalDate date, boolean available, String reason,
			List<ReservedTimeRange> reservedTimeRanges, BigDecimal totalSaleManual, boolean clearPrevData) {
// Buscar la fecha en el calendario
		ReservationCalendar calendarDate = calendarRepository.findByDate(date);

		if (calendarDate == null) {
			throw new RuntimeException("La fecha " + date + " no está registrada en el calendario.");
		}

// Limpiar datos previos si clearPrevData es true
		if (clearPrevData) {
			calendarDate.getReservedTimeRanges().clear();
			calendarDate.setTotalSaleManual(BigDecimal.ZERO);
			calendarDate.setReason(null); // Limpiar la razón previa
		}

// Actualizar el estado de disponibilidad
		calendarDate.setAvailable(available);
		
		if (calendarDate.getTypeReserve().equals("Venta Web")) {
			calendarDate.setTypeReserve("Venta Web y Manual");
		} else {
			calendarDate.setTypeReserve("Manual");
		}
		
		if (calendarDate.getTotalSaleManual() == null) {
			calendarDate.setTotalSaleManual(totalSaleManual);
		} else {
			calendarDate.setTotalSaleManual(calendarDate.getTotalSaleManual().add(totalSaleManual));
		}

// Construir la nueva razón
		String newReason = reason != null ? reason + " $" + totalSaleManual
				: (available ? "Reserva cancelada manualmente" : "Reservada manualmente") + " $" + totalSaleManual;

// Agregar los nuevos rangos de tiempo a los existentes
		if (reservedTimeRanges != null && !reservedTimeRanges.isEmpty()) {
			for (ReservedTimeRange range : reservedTimeRanges) {
				calendarDate.addTimeRange(range.getStartTime(), range.getEndTime(), range.getDniClient());
				newReason += " " + range.getStartTime() + "hs a " + range.getEndTime() + "hs";
			}
		}

// Concatenar la nueva razón con la razón existente (si existe)
		if (calendarDate.getReason() != null && !calendarDate.getReason().isEmpty()) {
			calendarDate.setReason(calendarDate.getReason() + "," + " | " + newReason);
		} else {
			calendarDate.setReason(newReason);
		}

// Guardar los cambios en la base de datos
		calendarRepository.save(calendarDate);

		return "La disponibilidad de la fecha " + date + " se actualizó a " + (available ? "disponible" : "reservada")
				+ ".";
	}

    // Obtener fechas disponibles desde hoy en adelante
    public List<ReservationCalendar> getAvailableDates() {
    	// Obtener el día actual
        LocalDate today = LocalDate.now();
        
        return calendarRepository.findByDateGreaterThanEqual(today.plusDays(1))
                .stream()
                .filter(ReservationCalendar::isAvailable)
                .collect(Collectors.toList());
    }

    // Obtener todas las fechas del calendario
    public List<ReservationCalendar> getAllDates() {
        return calendarRepository.findAll();
    }
    
    
    //Calcular el procentaje de ocupacion de una fecha y establecer disponibilidad
    
    private static final double OCCUPANCY_THRESHOLD = 0.6; // 60% del día
    
    @Transactional
    public void updateAvailabilityBasedOnOccupancy(LocalDate date) {
        // Obtener el calendario para la fecha específica
        ReservationCalendar calendarDate = calendarRepository.findByDate(date);
        if (calendarDate == null) {
            throw new RuntimeException("El calendario para la fecha " + date + " no está registrado.");
        }

        // **Ignorar franja horaria entre 00:00 y 06:00**
        LocalTime ignoreStart = LocalTime.MIDNIGHT; // 00:00
        LocalTime ignoreEnd = LocalTime.of(6, 0); // 06:00

        // Inicializar los minutos reservados del día
        int totalReservedMinutes = 0;

        // Procesar cada rango horario
        for (ReservedTimeRange range : calendarDate.getReservedTimeRanges()) {
            LocalTime startTime = range.getStartTime();
            LocalTime endTime = range.getEndTime();

            if (endTime.isBefore(startTime)) {
                // Caso: rango cruza la medianoche
                // Minutos desde startTime hasta el final del día (23:59)
                totalReservedMinutes += calculateEffectiveMinutes(startTime, LocalTime.MAX, ignoreStart, ignoreEnd);

                // Minutos desde el inicio del día siguiente (00:00) hasta `endTime`
                LocalDate nextDay = date.plusDays(1);
                ReservationCalendar nextDayCalendar = calendarRepository.findByDate(nextDay);
                if (nextDayCalendar != null) {
                    totalReservedMinutes += calculateEffectiveMinutes(LocalTime.MIN, endTime, ignoreStart, ignoreEnd);
                }
            } else {
                // Caso: rango está dentro del mismo día
                totalReservedMinutes += calculateEffectiveMinutes(startTime, endTime, ignoreStart, ignoreEnd);
            }
        }

        // **Calcular el porcentaje total acumulado de ocupación**
        int totalMinutesInDay = 18 * 60; // Excluyendo la franja 00:00-06:00, quedan 18 horas = 1080 minutos
        double occupancyPercentage = (double) totalReservedMinutes / totalMinutesInDay;

        // **Imprimir para depuración**
        System.out.println("Total minutos reservados acumulados para " + date + ": " + totalReservedMinutes);
        System.out.println("Porcentaje de ocupación acumulado para " + date + ": " + occupancyPercentage);

        // **Actualizar `available` basado en el porcentaje de ocupación acumulado**
        if (occupancyPercentage >= OCCUPANCY_THRESHOLD) {
            calendarDate.setAvailable(false);
        } else {
            calendarDate.setAvailable(true);
        }
        
        if (calendarDate.getTypeReserve() != null) {
        	if (calendarDate.getTypeReserve().equals("Manual") || calendarDate.getTypeReserve().equals("Venta Web y Manual")) {
            	calendarDate.setTypeReserve("Venta Web y Manual");
            }
        } else {
        	calendarDate.setTypeReserve("Venta Web");
        }
       

        // Guardar los cambios en el calendario
        calendarRepository.save(calendarDate);
    }

    /**
     * Calcula los minutos efectivos entre startTime y endTime, ignorando la franja horaria entre ignoreStart y ignoreEnd.
     */
    private int calculateEffectiveMinutes(LocalTime startTime, LocalTime endTime, LocalTime ignoreStart, LocalTime ignoreEnd) {
    	
    	System.out.println("startTime: " + startTime + "- endTime: " + endTime);
    	System.out.println("ignoreStart: " + ignoreStart + "- ignoreEnd: " + ignoreEnd);
    	
    	// Caso 1: El rango está completamente dentro de la franja ignorada
        if (!startTime.isAfter(ignoreStart) && !endTime.isBefore(ignoreEnd)) {
            return 0; // Rango completamente ignorado
        }
    	
     // Caso 2: El rango incluye parcialmente la franja ignorada
        if (startTime.isBefore(ignoreEnd) && endTime.isAfter(ignoreStart)) {
            if (startTime.isBefore(ignoreEnd)) {
                startTime = ignoreEnd; // Ajustar inicio fuera de la franja ignorada
            }
            if (endTime.isAfter(ignoreStart) && endTime.isBefore(ignoreEnd)) {
                endTime = ignoreStart; // Ajustar fin fuera de la franja ignorada
            }
        }

     // Caso 3: Rango válido fuera de la franja ignorada
        if (endTime.isAfter(startTime)) {
            return (int) Duration.between(startTime, endTime).toMinutes();
        }

        return 0; // Rango no válido o completamente excluido
    }

}

