package com.system.pos.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.pos.dtos.SaleDTO;
import com.system.pos.entities.OutboxEvent;
import com.system.pos.enums.OutboxStatus;
import com.system.pos.messaging.OutboxPublisher;
import com.system.pos.repositories.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.system.pos.constants.OutboxConstants.SALE;
import static com.system.pos.constants.OutboxConstants.SALE_STARTED_EVENT;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final OutboxEventRepository outboxEventRepository;
    private final OutboxPublisher outboxPublisher;

    private final ObjectMapper objectMapper;

    public void startSale(SaleDTO saleDTO) {
        try {
            saleDTO.setId(UUID.randomUUID());
            String payload = objectMapper.writeValueAsString(saleDTO);

            OutboxEvent outboxEvent = OutboxEvent.builder()
                    .aggregateType(SALE)
                    .aggregateId(saleDTO.getCustomerId().toString())
                    .eventType(SALE_STARTED_EVENT)
                    .payload(payload)
                    .status(OutboxStatus.PENDING)
                    .createdAt(LocalDateTime.now())
                    .build();

            outboxEventRepository.save(outboxEvent);
            outboxPublisher.publishPendingEvent(outboxEvent);

        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize and save event to outbox", e);
        }
    }
}
