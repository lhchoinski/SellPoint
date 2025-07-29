package com.system.sales.listener;

import com.system.sales.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryListener {

    private final InventoryService inventoryService;

    @RabbitListener(queues = "inventory.product.request")
    public void handleStockReserved() {

    }

}