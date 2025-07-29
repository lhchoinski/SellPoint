package com.system.estoque.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ProductDTO {
    private UUID id;
    private Integer quantity;
    private BigDecimal price;
}