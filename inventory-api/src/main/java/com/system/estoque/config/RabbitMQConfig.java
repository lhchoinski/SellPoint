package com.system.estoque.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.exchanges.inventory}")
    private String salesExchange;

    @Value("${spring.rabbitmq.routing-keys.inventory.product}")
    private String routingKey;

    @Value("${spring.rabbitmq.routing-keys.inventory.reserve}")
    private String routingKeyReserve;

    @Bean
    public TopicExchange salesExchange() {
        return new TopicExchange(salesExchange);
    }

    @Bean
    public Queue inventoryProductRequestQueue() {
        return new Queue("inventory.product.request", true);
    }

    @Bean
    public Queue inventoryProductReserveRequestQueue() {
        return new Queue("inventory.product.reserve", true);
    }

    @Bean
    public Binding inventoryProductBinding() {
        return BindingBuilder
                .bind(inventoryProductRequestQueue())
                .to(salesExchange())
                .with(routingKey);
    }

    @Bean
    public Binding inventoryProductReserveBinding() {
        return BindingBuilder
                .bind(inventoryProductReserveRequestQueue())
                .to(salesExchange())
                .with(routingKeyReserve);
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
