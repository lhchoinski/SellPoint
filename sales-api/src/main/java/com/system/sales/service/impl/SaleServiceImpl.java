package com.system.sales.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.sales.component.DistributedLockComponent;
import com.system.sales.dto.ProductDTO;
import com.system.sales.dto.SaleDTO;
import com.system.sales.dto.SaleProduct;
import com.system.sales.dto.inventory.StockReserveCommand;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.system.sales.enums.SaleStatus.IN_PROGRESS;

@Slf4j
@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final DistributedLockComponent distributedLockComponent;
    private final OutboxEventRepository outboxEventRepository;

    private final InventoryPublisher inventoryPublisher;
    private final InventoryService inventoryService;

    private final ObjectMapper objectMapper;

    @Override
    public void processSaleStarted(SaleDTO saleDTO) {
        long startTime = System.currentTimeMillis();
        String lockKey = "sale:lock:" + saleDTO.getId();

        distributedLockComponent.executeWithLock(lockKey, 5, 10, () -> {
            try {

                List<ProductDTO> products = new ArrayList<>();
               saleDTO.getSaleProducts().forEach(product -> {
                   products.add(inventoryService.findProductsById(product.getId()));
                });

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
                long endTime = System.currentTimeMillis();
                log.info("Processamento da venda {} concluído em {} ms", saleDTO.getId(), (endTime - startTime));
            } catch (Exception e) {
                throw new RuntimeException("Erro ao processar venda e outbox", e);
            }

            return null;
        });
    }
}
