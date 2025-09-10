package com.system.estoque.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.estoque.dtos.ProductInventoryDTO;
import com.system.estoque.dtos.StockReserveCommand;
import com.system.estoque.entities.Product;
import com.system.estoque.mappers.ProductMapper;
import com.system.estoque.repositories.ProductRepository;
import com.system.estoque.services.StockExitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryListener {

    private final ObjectMapper objectMapper;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final StockExitService stockExitService;

    @RabbitListener(queues = "inventory.product.request")
    public String handleProductQuery(String productId) {
        try {
            UUID id = UUID.fromString(productId);
            Product product = productRepository.findById(id).orElse(null);

            ProductInventoryDTO dto = productMapper.toItemDTOs2(product);
            return objectMapper.writeValueAsString(dto);

        } catch (Exception e) {
            return null;
        }
    }

    @RabbitListener(queues = "inventory.product.reserve")
    public void reserveProduct(StockReserveCommand command) throws JsonProcessingException {
        stockExitService.reserveStock(command);
    }
}
