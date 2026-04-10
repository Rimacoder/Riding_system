package com.ridingsystem.controller;

import com.ridingsystem.repository.BookingRepository;
import com.ridingsystem.repository.PaymentRepository;
import com.ridingsystem.repository.RideRepository;
import com.ridingsystem.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final RideRepository rideRepository;
    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;

    public AdminController(UserRepository userRepository,
                           RideRepository rideRepository,
                           BookingRepository bookingRepository,
                           PaymentRepository paymentRepository) {
        this.userRepository = userRepository;
        this.rideRepository = rideRepository;
        this.bookingRepository = bookingRepository;
        this.paymentRepository = paymentRepository;
    }

    @GetMapping("/metrics")
    public Map<String, Object> metrics() {
        return Map.of(
                "totalUsers", userRepository.count(),
                "totalRides", rideRepository.count(),
                "totalBookings", bookingRepository.count(),
                "totalPayments", paymentRepository.count()
        );
    }
}
