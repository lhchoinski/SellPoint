package com.system.sales.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.sales.enums.OutboxStatus;
import com.system.sales.repositories.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class InventoryPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.exchanges.inventory}")
    private String exchange;

    @Value("${spring.rabbitmq.routing-keys.inventory.reserve}")
    private String routingKey;

    private final ObjectMapper objectMapper;
    private final OutboxEventRepository outboxEventRepository;

    public void publishReserveEvent() {
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
}
