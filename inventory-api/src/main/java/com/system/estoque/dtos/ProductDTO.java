package com.system.estoque.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProductDTO {
    private UUID id;
    private Integer quantity;
    private BigDecimal price;
}