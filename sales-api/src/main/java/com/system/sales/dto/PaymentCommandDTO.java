package com.system.sales.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class PaymentCommandDTO {

    private UUID id;
    private UUID saleId;
    private BigDecimal amount;
}
