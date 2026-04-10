package com.ridingsystem.service.impl;

import com.ridingsystem.entity.Booking;
import com.ridingsystem.entity.Payment;
import com.ridingsystem.entity.PaymentStatus;
import com.ridingsystem.repository.BookingRepository;
import com.ridingsystem.repository.PaymentRepository;
import com.ridingsystem.service.PaymentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, BookingRepository bookingRepository) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Payment simulatePayment(Long bookingId, String gateway) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        Payment payment = Payment.builder()
                .booking(booking)
                .amount(booking.getFareAmount())
                .paymentGateway(gateway)
                .transactionRef("TXN-" + UUID.randomUUID())
                .status(PaymentStatus.SUCCESS)
                .paidAt(LocalDateTime.now())
                .build();

        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> passengerPayments(Long passengerId) {
        return paymentRepository.findByBookingPassengerId(passengerId);
    }

    @Override
    public List<Payment> driverPayments(Long driverId) {
        return paymentRepository.findByBookingRideDriverId(driverId);
    }
}
