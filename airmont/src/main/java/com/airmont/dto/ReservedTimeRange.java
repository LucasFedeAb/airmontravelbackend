package com.airmont.dto;

import java.time.LocalTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ReservedTimeRange {

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;
    
    @Column(name = "dniClient", nullable = true)
    private Integer dniClient;

    public ReservedTimeRange() {}

    public ReservedTimeRange(LocalTime startTime, LocalTime endTime, Integer dniClient) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.dniClient = dniClient;
    }
    
    // Getters y Setters

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
  
	public Integer getDniClient() {
		return dniClient;
	}

	public void setDniClient(Integer dniClient) {
		this.dniClient = dniClient;
	}

	public boolean overlapsWith(LocalTime otherStartTime, LocalTime otherEndTime) {
      // Caso 1: Este rango no cruza la medianoche
      if (this.startTime.isBefore(this.endTime)) {
          // Validar solapamiento para rangos estándar
          return !(otherEndTime.isBefore(this.startTime) || otherStartTime.isAfter(this.endTime));
      }

      // Caso 2: Este rango cruza la medianoche (endTime < startTime)
      // Validar si el otro rango comienza o termina dentro de este rango cruzando la medianoche
      return (otherStartTime.isBefore(this.endTime) || otherEndTime.isAfter(this.startTime));
  }
  
  @Override
  public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      ReservedTimeRange that = (ReservedTimeRange) o;
      return Objects.equals(startTime, that.startTime) &&
             Objects.equals(endTime, that.endTime);
  }

  @Override
  public int hashCode() {
      return Objects.hash(startTime, endTime);
  }

}

