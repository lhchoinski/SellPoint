package com.system.estoque.listeners;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.estoque.entities.Product;
import com.system.estoque.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InventoryListener {

    private final ObjectMapper objectMapper;
    private final ProductRepository productRepository;

//    @RabbitListener(queues = "inventory.product.request")
    public String handleProductQuery(List<UUID> productIds) {
        try {
            List<Product> products = productRepository.findAllById(productIds);
            return objectMapper.writeValueAsString(products);
        } catch (Exception e) {
            e.printStackTrace();
            return "[]";
        }
    }
}
