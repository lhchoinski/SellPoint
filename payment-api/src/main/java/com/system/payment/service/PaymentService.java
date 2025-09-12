package com.system.payment.service;

import com.system.payment.dtos.PaymentCommandDTO;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public void processPayment(PaymentCommandDTO paymentCommandDTO) {
        createPayment(paymentCommandDTO);
    }

    private void createPayment(PaymentCommandDTO paymentCommandDTO) {

    }
}
