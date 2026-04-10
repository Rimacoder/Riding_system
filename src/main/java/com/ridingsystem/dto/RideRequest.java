package com.ridingsystem.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class RideRequest {
    @NotBlank
    private String source;
    @NotBlank
    private String destination;
    @NotNull
    private LocalDate travelDate;
    @NotNull
    private LocalTime travelTime;
    @NotNull
    @Min(1)
    private Integer availableSeats;
    @NotNull
    @DecimalMin("0.0")
    private BigDecimal baseFare;
    @NotNull
    @DecimalMin("0.0")
    private BigDecimal ratePerKm;
}
