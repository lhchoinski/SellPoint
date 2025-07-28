//package com.system.sales.listener;
//
//import com.system.sales.dto.inventory.StockReservationFailedEvent;
//import com.system.sales.dto.inventory.StockReservedEvent;
//import com.system.sales.service.InventoryService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class StockListener {
//
//    private final InventoryService inventoryService;
//
//    @RabbitListener(queues = "sales.stock.reserved")
//    public void handleStockReserved(StockReservedEvent event) {
//        inventoryService.handleStockReserved(event);
//    }
//
//    @RabbitListener(queues = "sales.stock.failed")
//    public void handleStockFailed(StockReservationFailedEvent event) {
//        inventoryService.handleStockFailed(event);
//    }
//}