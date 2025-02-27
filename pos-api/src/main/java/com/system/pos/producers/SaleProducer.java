//package com.system.pos.producers;
//
//import com.system.pos.dtos.SaleDTO;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class SaleProducer {
//
//    private final RabbitTemplate rabbitTemplate;
//
//    @Value(value = "stock.#")
//    private String routingKey;
//
//    public void sendSaleToQueue(SaleDTO saleDTO) {
//
//        rabbitTemplate.convertAndSend("stock_exchange", routingKey, saleDTO);
//    }
//
//}
