package com.system.sales.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.sales.dto.ProductInventoryDTO;
import com.system.sales.dto.SaleDTO;
import com.system.sales.dto.inventory.StockReserveCommand;
import com.system.sales.entities.OutboxEvent;
import com.system.sales.entities.Sale;
import com.system.sales.entities.SaleProduct;
import com.system.sales.enums.OutboxStatus;
import com.system.sales.repositories.OutboxEventRepository;
import com.system.sales.repositories.SaleProductRepository;
import com.system.sales.repositories.SaleRepository;
import com.system.sales.service.InventoryService;
import com.system.sales.service.SaleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.system.sales.enums.SaleStatus.IN_PROGRESS;

@Slf4j
@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final SaleProductRepository saleProductRepository;
    private final OutboxEventRepository outboxEventRepository;
    private final InventoryService inventoryService;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void saleStartedProcess(SaleDTO saleDTO) throws JsonProcessingException {
        Sale sale = createSale(saleDTO);
        List<SaleProduct> saleProducts =  createSaleProducts(saleDTO, sale);

        sale.setTotalAmount(sale.calculateTotalAmount(saleProducts));
        saleRepository.save(sale);

        sendToOutbox(saleDTO,sale);
    }

    private Sale createSale(SaleDTO saleDTO) {
        Sale sale = new Sale();
        sale.setSaleStatus(IN_PROGRESS);
        sale.setCustomerId(saleDTO.getCustomerId());
        sale.setTimestamp(LocalDateTime.now());
        sale.setTotalAmount(BigDecimal.ZERO);

       return saleRepository.save(sale);
    }

    private List<SaleProduct> createSaleProducts(SaleDTO saleDTO, Sale sale) {
        List<SaleProduct> saleProducts = saleDTO.getSaleProductDTOS().stream().map(product -> {
            ProductInventoryDTO productInventory = inventoryService.findProductsById(product.getId());

            SaleProduct saleProduct = new SaleProduct();
            saleProduct.setSale(sale);
            saleProduct.setName(productInventory.getName());
            saleProduct.setProductId(productInventory.getId());
            saleProduct.setPrice(productInventory.getPrice());
            saleProduct.setQuantity(product.getQuantity());

            return saleProduct;
        }).toList();

       return saleProductRepository.saveAll(saleProducts);
    }

    private void sendToOutbox(SaleDTO saleDTO, Sale sale) throws JsonProcessingException {
        StockReserveCommand command = new StockReserveCommand(sale.getId(), saleDTO.getSaleProductDTOS());

        OutboxEvent outbox = OutboxEvent.builder()
                .aggregateType("SALE")
                .aggregateId(sale.getId().toString())
                .eventType("StockReserveCommand")
                .payload(objectMapper.writeValueAsString(command))
                .status(OutboxStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        outboxEventRepository.save(outbox);
    }
}
