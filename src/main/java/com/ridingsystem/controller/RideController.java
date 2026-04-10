package com.ridingsystem.controller;

import com.ridingsystem.dto.RideRequest;
import com.ridingsystem.entity.Ride;
import com.ridingsystem.service.RideService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rides")
public class RideController {

    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @PostMapping("/driver/{driverId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Ride postRide(@PathVariable Long driverId, @Valid @RequestBody RideRequest request) {
        return rideService.postRide(driverId, request);
    }

    @GetMapping("/search")
    public List<Ride> search(
            @RequestParam String source,
            @RequestParam String destination,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return rideService.searchRides(source, destination, date);
    }
}
