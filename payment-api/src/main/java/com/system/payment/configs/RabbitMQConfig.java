package com.system.payment.configs;

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

    @Value("${spring.rabbitmq.exchanges.payments}")
    private String paymentsExchange;

    @Value("${spring.rabbitmq.routing-keys.payments.started}")
    private String startedRoutingKey;

    @Value("${spring.rabbitmq.routing-keys.payments.update}")
    private String udpateRoutingKey;

    @Bean
    public TopicExchange paymentsExchange() {
        return new TopicExchange(paymentsExchange);
    }

    @Bean
    public Queue paymentStartedQueue() {
        return new Queue("payments.payment.started", true);
    }

    @Bean
    public Queue paymentUpdateQueue() {
        return new Queue("payments.payment.update", true);
    }

    @Bean
    public Binding paymentStartedBinding() {
        return BindingBuilder.bind(paymentStartedQueue())
                .to(paymentsExchange())
                .with(startedRoutingKey);
    }

    @Bean
    public Binding paymentUpdateBinding() {
        return BindingBuilder.bind(paymentUpdateQueue())
                .to(paymentsExchange())
                .with(udpateRoutingKey);
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
