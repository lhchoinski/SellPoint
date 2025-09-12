package com.system.sales.service;

import java.util.UUID;

public interface PaymentService {

    void processPayment(UUID saleId);
}
