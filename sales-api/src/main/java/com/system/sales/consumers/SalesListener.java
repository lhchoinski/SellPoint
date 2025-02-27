//package com.system.sales.consumers;
//
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class SalesListener {
//
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    public String processSale(String saleData) {
//        // Processa a venda (aplica a regra de negócio)
//        String paymentResponse = (String) rabbitTemplate.convertSendAndReceive("ecommerce.exchange", "payment.key", saleData);
//
//        if (paymentResponse != null && paymentResponse.equals("Pagamento aprovado")) {
//            // Envia para o estoque
//            return (String) rabbitTemplate.convertSendAndReceive("ecommerce.exchange", "stock.key", saleData);
//        }
//        return "Erro: Pagamento não aprovado.";
//    }
//}