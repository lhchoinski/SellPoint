package com.system.sales.listener;

import com.system.sales.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PaymentListener {

    private final PaymentService paymentService;

    @RabbitListener(queues = "payments.payment.started")
    public void listen(UUID saleId) {
        paymentService.processPayment(saleId);
    }
}
