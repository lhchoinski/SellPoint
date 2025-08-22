package com.system.sales.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "sale_products")
@Getter
@Setter
public class SaleProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id")
    private Sale sale;

    @Column(name = "name")
    private String name;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    private Integer quantity;

    private BigDecimal price;
}
