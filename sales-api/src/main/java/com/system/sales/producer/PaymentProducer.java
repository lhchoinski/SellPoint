//package com.system.sales.producer;
//
//import com.system.sales.dto.payment.PaymentCommand;
//import lombok.RequiredArgsConstructor;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class PaymentProducer {
//    private final RabbitTemplate rabbitTemplate;
//
//    @Value("${mq.exchanges.payment}")
//    private String paymentExchange;
//
//    @Value("${mq.routing-keys.payment.process}")
//    private String paymentProcessRoutingKey;
//
//    public void sendPaymentCommand(PaymentCommand command) {
//        rabbitTemplate.convertAndSend(paymentExchange, paymentProcessRoutingKey, command);
//    }
//}