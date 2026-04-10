package com.ridingsystem.service.impl;

import com.ridingsystem.dto.BookingRequest;
import com.ridingsystem.entity.Booking;
import com.ridingsystem.entity.BookingStatus;
import com.ridingsystem.entity.Ride;
import com.ridingsystem.entity.Role;
import com.ridingsystem.entity.User;
import com.ridingsystem.repository.BookingRepository;
import com.ridingsystem.repository.RideRepository;
import com.ridingsystem.repository.UserRepository;
import com.ridingsystem.service.BookingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RideRepository rideRepository;
    private final UserRepository userRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, RideRepository rideRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.rideRepository = rideRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Booking createBooking(BookingRequest request) {
        Ride ride = rideRepository.findById(request.getRideId())
                .orElseThrow(() -> new IllegalArgumentException("Ride not found"));
        User passenger = userRepository.findById(request.getPassengerId())
                .orElseThrow(() -> new IllegalArgumentException("Passenger not found"));

        if (passenger.getRole() != Role.PASSENGER) {
            throw new IllegalArgumentException("Only passengers can book rides");
        }
        if (ride.getAvailableSeats() < request.getSeatsBooked()) {
            throw new IllegalArgumentException("Insufficient seats available");
        }

        BigDecimal fare = ride.getBaseFare().add(ride.getRatePerKm().multiply(request.getDistanceKm()))
                .multiply(BigDecimal.valueOf(request.getSeatsBooked()))
                .setScale(2, RoundingMode.HALF_UP);

        Booking booking = Booking.builder()
                .ride(ride)
                .passenger(passenger)
                .seatsBooked(request.getSeatsBooked())
                .distanceKm(request.getDistanceKm())
                .fareAmount(fare)
                .bookingStatus(BookingStatus.CONFIRMED)
                .bookedAt(LocalDateTime.now())
                .build();

        ride.setAvailableSeats(ride.getAvailableSeats() - request.getSeatsBooked());
        rideRepository.save(ride);

        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> passengerBookings(Long passengerId) {
        return bookingRepository.findByPassengerId(passengerId);
    }

    @Override
    public List<Booking> driverBookings(Long driverId) {
        return bookingRepository.findByRideDriverId(driverId);
    }
}
