package com.system.sales.services.impl;

import com.system.sales.dtos.SaleDTO;
import com.system.sales.services.SaleService;
import exeptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "sales.queue")
    public void processSales(SaleDTO saleRequest) {
        try {
            processPayment(saleRequest);

            String stockResponse = (String) rabbitTemplate.convertSendAndReceive(
                    "ecommerce.exchange",
                    "stock.key",
                    saleRequest
            );

            if (stockResponse != null && stockResponse.equals("OK")) {
                System.out.println("Venda processada com sucesso! Estoque atualizado.");
            } else {
                System.err.println("Erro: Não houve resposta do estoque.");
            }


        } catch (Exception e) {
            System.err.println("Erro ao processar venda: " + e.getMessage());
        }
    }

private void processPayment(SaleDTO saleRequest) {
    try {
        rabbitTemplate.convertSendAndReceive("payment.queue", saleRequest);
    } catch (Exception e) {
       throw new BadRequestException(e.getMessage());
    }
}

}
