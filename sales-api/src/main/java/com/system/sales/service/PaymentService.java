package com.system.sales.service;

import com.system.sales.dto.PaymentDetails;

public interface PaymentService {

    void processPayment(PaymentDetails paymentDetails);
}
