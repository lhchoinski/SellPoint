package com.system.estoque.consumers;

import com.system.estoque.dtos.SaleDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class StockListener {

//    @RabbitListener(queues = "stock.queue")
//    public String updateStock(SaleDTO saleData) {
//        System.out.println("Stock: Atualizando estoque para a venda: " + saleData);
//
//        boolean stockUpdated = true; // Simula a atualização de estoque
//
//        if (stockUpdated) {
//            return "Venda concluída com sucesso!";
//        } else {
//            return "Erro: Estoque insuficiente.";
//        }
//    }
}
