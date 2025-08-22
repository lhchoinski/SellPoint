package com.system.sales.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.sales.dto.ProductInventoryDTO;
import com.system.sales.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Value("${spring.rabbitmq.exchanges.inventory}")
    private String exchange;

    @Value("${spring.rabbitmq.routing-keys.inventory.product}")
    private String routingKey;

    public ProductInventoryDTO findProductsById(UUID productId) {
        try {
            String json = (String) rabbitTemplate.convertSendAndReceive(exchange, routingKey, productId.toString());

            if (json == null) {
                throw new RuntimeException("Timeout ou erro ao obter resposta do inventory");
            }

            return objectMapper.readValue(json, ProductInventoryDTO.class);

        } catch (Exception e) {
            throw new RuntimeException("search error on RabbitMQ", e);
        }
    }
}

