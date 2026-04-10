package com.ridingsystem.repository;

import com.ridingsystem.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByBookingPassengerId(Long passengerId);
    List<Payment> findByBookingRideDriverId(Long driverId);
}
