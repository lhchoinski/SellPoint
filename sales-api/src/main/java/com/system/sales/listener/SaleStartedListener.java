package com.system.sales.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.system.sales.dto.SaleDTO;
import com.system.sales.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaleStartedListener {

    private final SaleService saleService;

    @RabbitListener(queues = "sales.sale.started", concurrency = "50")
    public void handleSaleStarted(SaleDTO saleDTO) throws JsonProcessingException {
        saleService.saleStartedProcess(saleDTO);
    }
}