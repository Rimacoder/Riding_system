package com.ridingsystem.service.impl;

import com.ridingsystem.dto.RideRequest;
import com.ridingsystem.entity.Ride;
import com.ridingsystem.entity.Role;
import com.ridingsystem.entity.User;
import com.ridingsystem.repository.RideRepository;
import com.ridingsystem.repository.UserRepository;
import com.ridingsystem.service.RideService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final UserRepository userRepository;

    public RideServiceImpl(RideRepository rideRepository, UserRepository userRepository) {
        this.rideRepository = rideRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Ride postRide(Long driverId, RideRequest request) {
        User driver = userRepository.findById(driverId)
                .orElseThrow(() -> new IllegalArgumentException("Driver not found"));

        if (driver.getRole() != Role.DRIVER) {
            throw new IllegalArgumentException("Only drivers can post rides");
        }

        Ride ride = Ride.builder()
                .source(request.getSource())
                .destination(request.getDestination())
                .travelDate(request.getTravelDate())
                .travelTime(request.getTravelTime())
                .availableSeats(request.getAvailableSeats())
                .baseFare(request.getBaseFare())
                .ratePerKm(request.getRatePerKm())
                .driver(driver)
                .build();

        return rideRepository.save(ride);
    }

    @Override
    public List<Ride> searchRides(String source, String destination, LocalDate travelDate) {
        return rideRepository.findBySourceIgnoreCaseAndDestinationIgnoreCaseAndTravelDate(source, destination, travelDate);
    }
}
