package com.ridingsystem.controller;

import com.ridingsystem.dto.BookingRequest;
import com.ridingsystem.entity.Booking;
import com.ridingsystem.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Booking create(@Valid @RequestBody BookingRequest request) {
        return bookingService.createBooking(request);
    }

    @GetMapping("/passenger/{passengerId}")
    public List<Booking> passengerBookings(@PathVariable Long passengerId) {
        return bookingService.passengerBookings(passengerId);
    }

    @GetMapping("/driver/{driverId}")
    public List<Booking> driverBookings(@PathVariable Long driverId) {
        return bookingService.driverBookings(driverId);
    }
}
