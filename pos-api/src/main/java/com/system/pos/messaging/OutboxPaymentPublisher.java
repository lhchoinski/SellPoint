package com.system.pos.messaging;

import java.time.LocalDateTime;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.pos.enums.OutboxStatus;
import com.system.pos.repositories.OutboxEventRepository;

@Component
@RequiredArgsConstructor
public class OutboxPaymentPublisher {

    @Value("${spring.rabbitmq.exchanges.payments}")
    private String exchange;

    @Value("${spring.rabbitmq.routing-keys.payments.update}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;
    private final OutboxEventRepository outboxEventRepository;

    private final ObjectMapper objectMapper;

    public void publishPendingEvent() {
        outboxEventRepository.findAllByStatus(OutboxStatus.PENDING).forEach(event -> {
            try {
                rabbitTemplate.convertAndSend(exchange, routingKey, objectMapper.readTree(event.getPayload()));
                event.setStatus(OutboxStatus.SENT);
                event.setSentAt(LocalDateTime.now());
            } catch (Exception e) {
                event.setStatus(OutboxStatus.FAILED);
                // log do erro, opcional
            }
            outboxEventRepository.save(event);
        });
    }

    public void publishReserveEvent() {
        outboxEventRepository.findAllByStatus(OutboxStatus.PENDING).forEach(event -> {
            try {
                rabbitTemplate.convertAndSend(exchange, routingKey, objectMapper.readTree(event.getPayload()));
                event.setStatus(OutboxStatus.SENT);
                event.setSentAt(LocalDateTime.now());
            } catch (Exception e) {
                event.setStatus(OutboxStatus.FAILED);
            }
            outboxEventRepository.save(event);
        });
    }
}
