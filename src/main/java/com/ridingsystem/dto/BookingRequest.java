package com.ridingsystem.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookingRequest {
    @NotNull
    private Long rideId;
    @NotNull
    @Min(1)
    private Integer seatsBooked;
    @NotNull
    @DecimalMin("0.1")
    private BigDecimal distanceKm;
    @NotNull
    private Long passengerId;
}
