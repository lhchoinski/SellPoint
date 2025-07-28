package com.system.sales.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Item {
    private UUID productId;
    private Integer quantity;
    private Double unitPrice;
}
