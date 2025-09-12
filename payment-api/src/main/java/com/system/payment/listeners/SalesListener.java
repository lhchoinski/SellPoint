package com.system.payment.listeners;

import com.system.payment.dtos.PaymentCommandDTO;
import com.system.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SalesListener {

    private final PaymentService paymentService;

    @RabbitListener(queues = "payments.payment.started")
    public void paymentStarted(PaymentCommandDTO paymentCommandDTO) {
         paymentService.processPayment(paymentCommandDTO);
    }
}
