package com.system.sales.producer;

import com.system.sales.dto.PaymentCommandDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.exchanges.payments}")
    private String paymentExchange;

    @Value("${spring.rabbitmq.routing-keys.payments.update}")
    private String paymentProcessRoutingKey;

    public void createPaymentCommand(PaymentCommandDTO command) {
        rabbitTemplate.convertAndSend(paymentExchange, paymentProcessRoutingKey, command);
    }
}