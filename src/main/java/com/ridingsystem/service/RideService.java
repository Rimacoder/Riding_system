package com.ridingsystem.service;

import com.ridingsystem.dto.RideRequest;
import com.ridingsystem.entity.Ride;

import java.time.LocalDate;
import java.util.List;

public interface RideService {
    Ride postRide(Long driverId, RideRequest request);
    List<Ride> searchRides(String source, String destination, LocalDate travelDate);
}
