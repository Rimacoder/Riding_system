package com.ridingsystem.controller;

import com.ridingsystem.entity.Payment;
import com.ridingsystem.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/booking/{bookingId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Payment pay(@PathVariable Long bookingId,
                       @RequestParam(defaultValue = "stripe-sandbox") String gateway) {
        return paymentService.simulatePayment(bookingId, gateway);
    }

    @GetMapping("/passenger/{passengerId}")
    public List<Payment> passengerPayments(@PathVariable Long passengerId) {
        return paymentService.passengerPayments(passengerId);
    }

    @GetMapping("/driver/{driverId}")
    public List<Payment> driverPayments(@PathVariable Long driverId) {
        return paymentService.driverPayments(driverId);
    }
}
