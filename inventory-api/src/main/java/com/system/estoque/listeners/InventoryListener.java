package com.system.estoque.listeners;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.estoque.dtos.ProductDTO;
import com.system.estoque.dtos.entities.ProductDTO2;
import com.system.estoque.entities.Product;
import com.system.estoque.mappers.ProductMapper;
import com.system.estoque.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryListener {

    private final ObjectMapper objectMapper;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @RabbitListener(queues = "inventory.product.request")
    public String handleProductQuery(String productId) {
        try {
            UUID id = UUID.fromString(productId);
            log.info("id:: {}", id);
            Product product = productRepository.findById(id).orElse(null);

            ProductDTO dto = productMapper.toItemDTOs2(product);
            log.info("dto:: {}", dto.getQuantity());
            return objectMapper.writeValueAsString(dto);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
