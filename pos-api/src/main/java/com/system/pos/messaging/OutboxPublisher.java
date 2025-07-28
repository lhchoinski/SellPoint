package com.system.pos.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.pos.entities.OutboxEvent;
import com.system.pos.enums.OutboxStatus;
import com.system.pos.repositories.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;

import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

@Component
@RequiredArgsConstructor
public class OutboxPublisher {

    @Value("${spring.rabbitmq.exchanges.sales}")
    private String exchange;

    @Value("${spring.rabbitmq.routing-keys.sale-started}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;
    private final OutboxEventRepository outboxEventRepository;

    private final ObjectMapper objectMapper;

    @TransactionalEventListener(phase = AFTER_COMMIT)
    public void publishPendingEvent(OutboxEvent event) {
        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, objectMapper.readTree(event.getPayload()));
            event.setStatus(OutboxStatus.SENT);
            event.setSentAt(LocalDateTime.now());
        } catch (Exception e) {
            event.setStatus(OutboxStatus.FAILED);
            // TODO: log/handler
        }
        outboxEventRepository.save(event);
    }
}
