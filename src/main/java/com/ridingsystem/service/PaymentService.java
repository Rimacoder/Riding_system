package com.ridingsystem.service;

import com.ridingsystem.entity.Payment;

import java.util.List;

public interface PaymentService {
    Payment simulatePayment(Long bookingId, String gateway);
    List<Payment> passengerPayments(Long passengerId);
    List<Payment> driverPayments(Long driverId);
}
