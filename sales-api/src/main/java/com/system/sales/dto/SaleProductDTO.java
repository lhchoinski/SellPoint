package com.system.sales.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class SaleProductDTO {
    private UUID id;
    private Integer quantity;
}
