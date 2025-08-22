package com.system.sales.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.sales.component.DistributedLockComponent;
import com.system.sales.producer.InventoryPublisher;
import com.system.sales.repositories.OutboxEventRepository;
import com.system.sales.repositories.SaleRepository;
import com.system.sales.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class SaleServiceImplTest {

    @InjectMocks
    private SaleServiceImpl saleService;

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private InventoryService inventoryService;

    @Mock
    private InventoryPublisher inventoryPublisher;

    @Mock
    private OutboxEventRepository outboxEventRepository;

    @Mock
    private DistributedLockComponent distributedLockComponent;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldProcessSaleInsideDistributedLock() throws Exception {
//        UUID saleId = UUID.fromString("cdc66889-568c-4f84-85e1-9f18418ad8d4");
//        UUID productId = UUID.fromString("056f7b00-7fb7-4bde-95c4-d95c84dcc4cf");
//        UUID customerId = UUID.fromString("056f7b00-7fb7-4bde-95c4-d95c84dcc4cf");
//
//        SaleDTO saleDTO = new SaleDTO(saleId, customerId, List.of(
//                new SaleProductDTO(productId, 2)), LocalDateTime.now()
//        );
//
//        ProductInventoryDTO productDTO = new ProductDTO(productId, 2, new BigDecimal("10.00"));
//
//        when(inventoryService.findProductsById().thenReturn(List.of(productDTO));
//        when(saleRepository.save(any(Sale.class))).thenAnswer(i -> i.getArgument(0));
//        when(outboxEventRepository.save(any(OutboxEvent.class))).thenAnswer(i -> i.getArgument(0));
//
//        doAnswer(invocation -> {
//            var supplier = (java.util.function.Supplier<?>) invocation.getArgument(3);
//            return supplier.get();
//        }).when(distributedLockComponent).executeWithLock(anyString(), anyLong(), anyLong(), any());
//
//        saleService.processSaleStarted(saleDTO);
//
//        verify(distributedLockComponent).executeWithLock(eq("sale:lock:" + saleId), anyLong(), anyLong(), any());
//        verify(saleRepository).save(any(Sale.class));
//        verify(outboxEventRepository).save(any(OutboxEvent.class));
//        verify(inventoryPublisher).publishReserveEvent();
    }
}