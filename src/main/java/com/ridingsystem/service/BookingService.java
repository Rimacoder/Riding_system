package com.ridingsystem.service;

import com.ridingsystem.dto.BookingRequest;
import com.ridingsystem.entity.Booking;

import java.util.List;

public interface BookingService {
    Booking createBooking(BookingRequest request);
    List<Booking> passengerBookings(Long passengerId);
    List<Booking> driverBookings(Long driverId);
}
