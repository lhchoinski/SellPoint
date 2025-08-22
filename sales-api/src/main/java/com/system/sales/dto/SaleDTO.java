package com.system.sales.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTO {
    private UUID id;
    private UUID customerId;
    private List<SaleProductDTO> saleProductDTOS;
    private LocalDateTime timestamp;
}

