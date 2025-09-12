package com.system.pos.services;

import static com.system.pos.constants.OutboxConstants.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.pos.dtos.PaymentDetails;
import com.system.pos.entities.OutboxEvent;
import com.system.pos.enums.OutboxStatus;
import com.system.pos.repositories.OutboxEventRepository;
import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final OutboxEventRepository outboxEventRepository;

    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.exchanges.inventory}")
    private String exchange;

    @Value("${spring.rabbitmq.routing-keys.inventory.product}")
    private String routingKey;

    @Transactional
    public void updatePayment(PaymentDetails paymentDetails) {
        PaymentDetails paymentUpdate = getPaymentBySaleId(paymentDetails.getSaleId());

        paymentUpdate.setPaymentMethod(paymentDetails.getPaymentMethod());
        paymentUpdate.setAmount(paymentDetails.getAmount());
        paymentUpdate.setCardNumber(paymentDetails.getCardNumber());
        paymentUpdate.setCardCvv(paymentDetails.getCardCvv());
        paymentUpdate.setCardExpiry(paymentDetails.getCardExpiry());
        paymentUpdate.setCardHolderName(paymentDetails.getCardHolderName());

        try {
            String payload = objectMapper.writeValueAsString(paymentUpdate);

            OutboxEvent outboxEvent = OutboxEvent.builder()
                    .aggregateType(PAYMENT)
                    .aggregateId(UUID.randomUUID().toString()) // pegar id do usuario logado
                    .eventType(PAYMENT_UPDATE_EVENT)
                    .payload(payload)
                    .status(OutboxStatus.PENDING)
                    .createdAt(LocalDateTime.now())
                    .build();
            outboxEventRepository.save(outboxEvent);

        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize and save event to outbox", e);
        }
    }

    public PaymentDetails getPaymentBySaleId(UUID saleid) {
        try {
            String json = (String) rabbitTemplate.convertSendAndReceive(exchange, routingKey, saleid.toString());

            if (json == null) {
                throw new RuntimeException("Timeout ou erro ao obter resposta do inventory");
            }

            return objectMapper.readValue(json, PaymentDetails.class);

        } catch (Exception e) {
            throw new RuntimeException("search error on RabbitMQ", e);
        }
    }
}
