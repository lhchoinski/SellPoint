package com.system.sales.service.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.sales.dto.ProductDTO;
import com.system.sales.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public ProductDTO findProductsById(UUID productId) {
        try {
            String json = (String) rabbitTemplate.convertSendAndReceive(exchange, routingKey, productId.toString());

            if (json == null) {
                throw new RuntimeException("Timeout ou erro ao obter resposta do inventory");
            }

            return objectMapper.readValue(json, ProductDTO.class);

        } catch (Exception e) {
            throw new RuntimeException("Erro na consulta de produto no inventory via RabbitMQ", e);
        }
    }
}

