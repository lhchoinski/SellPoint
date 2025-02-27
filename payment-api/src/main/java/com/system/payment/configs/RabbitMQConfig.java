package com.system.payment.configs;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("ecommerce.exchange");
    }

    @Bean
    public Queue salesQueue() {
        return new Queue("sales.queue");
    }

    @Bean
    public Queue paymentQueue() {
        return new Queue("payment.queue");
    }

    @Bean
    public Queue stockQueue() {
        return new Queue("stock.queue");
    }

    @Bean
    public Binding salesBinding(Queue salesQueue, DirectExchange exchange) {
        return BindingBuilder.bind(salesQueue).to(exchange).with("sales.key");
    }

    @Bean
    public Binding paymentBinding(Queue paymentQueue, DirectExchange exchange) {
        return BindingBuilder.bind(paymentQueue).to(exchange).with("payment.key");
    }

    @Bean
    public Binding stockBinding(Queue stockQueue, DirectExchange exchange) {
        return BindingBuilder.bind(stockQueue).to(exchange).with("stock.key");
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
