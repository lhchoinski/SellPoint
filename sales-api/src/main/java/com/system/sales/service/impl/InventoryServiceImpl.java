package com.system.sales.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.sales.dto.Product;
import com.system.sales.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public List<Product> findProductsByIds(List<UUID> productIds) {
//        try {


            // Faz o envio da mensagem e espera a resposta (RPC)
            rabbitTemplate.convertAndSend(exchange, routingKey, productIds);

//            if (responseJson == null) {
//                throw new RuntimeException("Timeout ou erro ao obter resposta do inventory");
//            }
//
//            // Converte o JSON recebido para lista de Products
//            return objectMapper.readValue(responseJson, new TypeReference<>() {
//            });
//        } catch (Exception e) {
//            throw new RuntimeException("Erro na consulta de produtos no inventory via RabbitMQ", e);
//        }
        return new ArrayList<>();
    }
}

