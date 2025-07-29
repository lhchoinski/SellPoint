package com.system.sales.entities;

import com.system.sales.dto.ProductDTO;
import com.system.sales.enums.SaleStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "sales")
@Getter
@Setter
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "customer_id")
    private UUID customerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "sale_status")
    private SaleStatus saleStatus;

    @Column(name = "payment_id")
    private UUID paymentId;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    private LocalDateTime timestamp;

    public BigDecimal calculateTotalAmount(List<ProductDTO> saleProductDTOS) {
        return saleProductDTOS.stream()
                .map(saleProduct -> saleProduct.getPrice().multiply(BigDecimal.valueOf(saleProduct.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
