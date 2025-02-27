package com.system.pos.configs;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String POS_EXCHANGE = "pos.exchange";
    public static final String SALES_QUEUE = "sales.queue";
    public static final String PAYMENT_QUEUE = "payment.queue";
    public static final String STOCK_QUEUE = "stock.queue";

    @Bean
    public DirectExchange posExchange() {
        return new DirectExchange(POS_EXCHANGE);
    }

    @Bean
    public Queue salesQueue() {
        return new Queue(SALES_QUEUE);
    }

    @Bean
    public Queue paymentQueue() {
        return new Queue(PAYMENT_QUEUE);
    }

    @Bean
    public Queue stockQueue() {
        return new Queue(STOCK_QUEUE);
    }

    @Bean
    public Binding salesBinding(DirectExchange posExchange, Queue salesQueue) {
        return BindingBuilder.bind(salesQueue).to(posExchange).with("sales");
    }

    @Bean
    public Binding paymentBinding(DirectExchange posExchange, Queue paymentQueue) {
        return BindingBuilder.bind(paymentQueue).to(posExchange).with("payment");
    }

    @Bean
    public Binding stockBinding(DirectExchange posExchange, Queue stockQueue) {
        return BindingBuilder.bind(stockQueue).to(posExchange).with("stock");
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
