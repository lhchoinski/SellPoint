package com.system.pos.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.exchanges.sales}")
    private String salesExchange;

    @Value("${spring.rabbitmq.exchanges.inventory}")
    private String inventoryExchange;

    @Value("${spring.rabbitmq.routing-keys.sale-started}")
    private String routingKey;

    @Value("${spring.rabbitmq.routing-keys.inventory.product}")
    private String inventoryRoutingKey;

    @Bean
    public TopicExchange salesExchange() {
        return new TopicExchange(salesExchange);
    }

    @Bean
    public TopicExchange inventoryExchange() {
        return new TopicExchange(inventoryExchange);
    }

    @Bean
    public Queue saleStartedQueue() {
        return new Queue("sales.sale.started", true);
    }

    @Bean
    public Queue inventoryStartedQueue() {
        return new Queue("inventory.product.request", true);
    }

    @Bean
    public Binding saleStartedBinding() {
        return BindingBuilder.bind(saleStartedQueue())
                .to(salesExchange())
                .with(routingKey);
    }

    @Bean
    public Binding inventoryStartedBinding() {
        return BindingBuilder.bind(inventoryStartedQueue())
                .to(inventoryExchange())
                .with(inventoryRoutingKey);
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter converter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter);
        return template;
    }
}
