package com.system.sales.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
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

    @Value("${spring.rabbitmq.routing-keys.sale-completed}")
    private String saleCompletedRoutingKey;

    @Value("${spring.rabbitmq.routing-keys.sale-failed}")
    private String saleFailedRoutingKey;

    @Bean
    public TopicExchange salesExchange() {
        return new TopicExchange(salesExchange);
    }

    @Bean
    public Queue saleCompletedQueue() {
        return new Queue("sales.sale.completed", true);
    }

    @Bean
    public Binding saleCompletedBinding() {
        return BindingBuilder.bind(saleCompletedQueue())
                .to(salesExchange())
                .with(saleCompletedRoutingKey);
    }

    @Bean
    public Queue saleFailedQueue() {
        return new Queue("sales.sale.failed", true);
    }

    @Bean
    public Binding saleFailedBinding() {
        return BindingBuilder.bind(saleFailedQueue())
                .to(salesExchange())
                .with(saleFailedRoutingKey);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
