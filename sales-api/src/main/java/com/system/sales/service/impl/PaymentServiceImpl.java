package com.system.sales.service.impl;

import com.system.sales.dto.PaymentCommandDTO;
import com.system.sales.entities.Sale;
import com.system.sales.producer.PaymentProducer;
import com.system.sales.repositories.SaleRepository;
import com.system.sales.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final SaleRepository saleRepository;
    private final PaymentProducer paymentProducer;

    @Override
    public void processPayment(UUID saleId) {
        Sale sale = saleRepository.findById(saleId).orElseThrow();

        PaymentCommandDTO commandDTO = new PaymentCommandDTO();
        commandDTO.setSaleId(sale.getId());
        commandDTO.setAmount(sale.getTotalAmount());

        paymentProducer.createPaymentCommand(commandDTO);

    }
}
