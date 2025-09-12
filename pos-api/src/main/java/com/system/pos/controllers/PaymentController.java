package com.system.pos.controllers;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.system.pos.dtos.PaymentDetails;
import com.system.pos.services.PaymentService;

@RestController
@RequestMapping("/pos/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PutMapping("/update")
    public void updatePayment(@RequestBody PaymentDetails paymentDetails) {
        paymentService.updatePayment(paymentDetails);
    }

    @GetMapping("/{saleId}")
    public PaymentDetails getPayment(@PathVariable UUID saleId) {
        return paymentService.getPaymentBySaleId(saleId);
    }
}
