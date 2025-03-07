package com.airmont.models.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.airmont.dto.ReservedTimeRange;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "reservation_calendar")
public class ReservationCalendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) // Un día único en el calendario
    private LocalDate date;

    @Column(nullable = false)
    private boolean available;

    @Column(nullable = true, length = 255)
    private String reason;
    
    @Column(nullable = true)
    private String typeReserve;
    
    @Column(nullable = true)
    private BigDecimal totalSaleManual;
    
    @ElementCollection
    @CollectionTable(name = "reservation_times", joinColumns = @JoinColumn(name = "calendar_id"))
    @Column(name = "reserved_range")
    private List<ReservedTimeRange> reservedTimeRanges = new ArrayList<>();

    public ReservationCalendar() {}

    public ReservationCalendar(LocalDate date, boolean available, String reason, String typeReserve) {
        this.date = date;
        this.available = available;
        this.reason = reason;
        this.typeReserve = typeReserve;
    }
    
    // Getters y Setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getTypeReserve() {
		return typeReserve;
	}

	public void setTypeReserve(String typeReserve) {
		this.typeReserve = typeReserve;
	}
	
	public BigDecimal getTotalSaleManual() {
		return totalSaleManual;
	}

	public void setTotalSaleManual(BigDecimal totalSaleManual) {
		this.totalSaleManual = totalSaleManual;
	}

	public List<ReservedTimeRange> getReservedTimeRanges() {
		return reservedTimeRanges;
	}

	public void setReservedTimeRanges(List<ReservedTimeRange> reservedTimeRanges) {
		this.reservedTimeRanges = reservedTimeRanges;
	}

	public boolean isTimeRangeAvailable(LocalTime startTime, LocalTime endTime) {
        for (ReservedTimeRange range : reservedTimeRanges) {
            if (range.overlapsWith(startTime, endTime)) {
                return false; // Hay solapamiento con un rango ya reservado
            }
        }
        return true;
    }

    public void addTimeRange(LocalTime startTime, LocalTime endTime, Integer dniClient) {
        this.reservedTimeRanges.add(new ReservedTimeRange(startTime, endTime, dniClient));
    }
    
    
 // Método para agregar un código de venta al motivo (reason)
    public void addCodeSaleToReason(String codeSale) {
        if (this.reason == null || this.reason.isEmpty()) {
            this.reason = "Reservado por venta ID: " + codeSale;
        } else {
            List<String> codes = getCodeSalesFromReason();
            if (!codes.contains(codeSale)) {
                codes.add(codeSale);
                this.reason = "Reservado por venta ID: " + String.join(", ", codes);
            }
        }
    }

    // Método para eliminar un código de venta del motivo (reason)
    public void removeCodeSaleFromReason(String codeSale) {
        List<String> codes = getCodeSalesFromReason();
        codes.remove(codeSale);

        if (codes.isEmpty()) {
            this.reason = null; // Si no quedan códigos, el motivo queda vacío
        } else {
            this.reason = "Reservado por venta ID: " + String.join(", ", codes);
        }
    }

    // Obtener la lista de códigos de venta desde el motivo (reason)
    public List<String> getCodeSalesFromReason() {
        if (this.reason == null || this.reason.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.stream(this.reason.replace("Reservado por venta ID: ", "").split(", "))
                .collect(Collectors.toList());
    }
  
}

