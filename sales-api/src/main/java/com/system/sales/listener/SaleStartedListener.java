package com.system.sales.listener;

import com.system.sales.dto.SaleDTO;
import com.system.sales.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaleStartedListener {

    private final SaleService saleService;

    @RabbitListener(queues = "sales.sale.started")
    public void handleSaleStarted(SaleDTO saleDTO) {
        saleService.processSaleStarted(saleDTO);
    }
}