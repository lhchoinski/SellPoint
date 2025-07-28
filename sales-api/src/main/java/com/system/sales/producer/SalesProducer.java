package com.system.sales.producer;

import com.system.sales.dto.SaleCompletedEvent;
import com.system.sales.dto.SaleFailedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SalesProducer {

    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String saleCompletedRoutingKey;
    private final String saleFailedRoutingKey;

    public SalesProducer(RabbitTemplate rabbitTemplate,
                         @Value("${spring.rabbitmq.exchanges.sales}") String exchange,
                         @Value("${spring.rabbitmq.routing-keys.sale-completed}") String saleCompletedRoutingKey,
                         @Value("${spring.rabbitmq.routing-keys.sale-failed}") String saleFailedRoutingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.saleCompletedRoutingKey = saleCompletedRoutingKey;
        this.saleFailedRoutingKey = saleFailedRoutingKey;
    }

    public void publishSaleCompleted(SaleCompletedEvent event) {
        rabbitTemplate.convertAndSend(exchange, saleCompletedRoutingKey, event);
    }

    public void publishSaleFailed(SaleFailedEvent event) {
        rabbitTemplate.convertAndSend(exchange, saleFailedRoutingKey, event);
    }
}
