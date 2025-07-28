package com.system.sales.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class SaleProduct {
    private UUID id;
    private Integer quantity;
}
