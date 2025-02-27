package com.system.sales.services.impl;

import com.system.sales.dtos.SaleDTO;
import com.system.sales.dtos.StockExitDTO;
import com.system.sales.services.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "sales.queue")
    public void processSales(SaleDTO saleRequest) {
        try {
            processPayment(saleRequest);

            StockExitDTO stockResponse = (StockExitDTO) rabbitTemplate.convertSendAndReceive("ecommerce.exchange", "stock.key", saleRequest);

            if (stockResponse != null) {
                System.out.println("Venda processada com sucesso! Estoque atualizado: " + stockResponse);
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
        e.getMessage();
    }
}

}
