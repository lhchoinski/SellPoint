package com.system.payment.service;

import com.system.payment.dtos.SaleDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final RabbitTemplate rabbitTemplate;

    public PaymentService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "payment.queue")
    public void processPayment(SaleDTO saleDTO) {
        System.out.println("Payment: Validando pagamento para: " + saleDTO.getId());

        rabbitTemplate.convertSendAndReceive("stock.queue", saleDTO);
    }
}
