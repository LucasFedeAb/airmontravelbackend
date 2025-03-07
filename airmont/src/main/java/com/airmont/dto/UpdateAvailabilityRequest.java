package com.airmont.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class UpdateAvailabilityRequest {
    private LocalDate date;
    private boolean available;
    private String reason;
    BigDecimal totalSaleManual;
    private boolean clearPrevData;
    private List<ReservedTimeRange> reservedTimeRanges;

    // Getters y setters
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
    
    public BigDecimal getTotalSaleManual() {
		return totalSaleManual;
	}

	public void setTotalSaleManual(BigDecimal totalSaleManual) {
		this.totalSaleManual = totalSaleManual;
	}
	
	public boolean isClearPrevData() {
		return clearPrevData;
	}

	public void setClearPrevData(boolean clearPrevData) {
		this.clearPrevData = clearPrevData;
	}

	public List<ReservedTimeRange> getReservedTimeRanges() {
        return reservedTimeRanges;
    }

    public void setReservedTimeRanges(List<ReservedTimeRange> reservedTimeRanges) {
        this.reservedTimeRanges = reservedTimeRanges;
    }
}