package com.system.sales.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.sales.dto.Product;
import com.system.sales.dto.SaleDTO;
import com.system.sales.dto.inventory.StockReserveCommand;
import com.system.sales.dto.SaleProduct;
import com.system.sales.entities.OutboxEvent;
import com.system.sales.entities.Sale;
import com.system.sales.enums.OutboxStatus;
import com.system.sales.producer.InventoryPublisher;
import com.system.sales.repositories.OutboxEventRepository;
import com.system.sales.repositories.SaleRepository;
import com.system.sales.service.InventoryService;
import com.system.sales.service.SaleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.system.sales.enums.SaleStatus.IN_PROGRESS;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final RedissonClient redissonClient;
    private final OutboxEventRepository outboxEventRepository;

    private final InventoryPublisher inventoryPublisher;
    private final InventoryService inventoryService;

    private final ObjectMapper objectMapper;

    @Transactional
    public void processSaleStarted(SaleDTO saleDTO) {
        String lockKey = "sale:lock:" + saleDTO.getId();
        RLock lock = redissonClient.getLock(lockKey);

        boolean acquired = false;
        try {
            acquired = lock.tryLock(5, 10, TimeUnit.SECONDS);

            if (!acquired) {
                throw new IllegalStateException("Sale is already being processed.");
            }

            List<Product> products = inventoryService.findProductsByIds(saleDTO.getSaleProducts()
                    .stream().map(SaleProduct::getId).toList());

            Sale sale = new Sale();
            sale.setSaleStatus(IN_PROGRESS);
            sale.setCustomerId(saleDTO.getCustomerId());
            sale.setTimestamp(LocalDateTime.now());
            sale.setTotalAmount(sale.calculateTotalAmount(products));

            saleRepository.save(sale);

            StockReserveCommand command = new StockReserveCommand(saleDTO.getId(), saleDTO.getSaleProducts());

            OutboxEvent outbox = OutboxEvent.builder()
                    .aggregateType("SALE")
                    .aggregateId(sale.getId().toString())
                    .eventType("StockReserveCommand")
                    .payload(objectMapper.writeValueAsString(command))
                    .status(OutboxStatus.PENDING)
                    .createdAt(LocalDateTime.now())
                    .build();

            outboxEventRepository.save(outbox);

//            inventoryPublisher.publishReserveEvent(outbox);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Failed to acquire lock.", e);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to process sale and outbox", ex);
        } finally {
            if (acquired && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
