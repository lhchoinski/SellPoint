package com.system.estoque.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockReserveCommand {

    private UUID saleId;
    private List<SaleProductDTO> saleProductDTOS;
}
