package com.system.pos.dtos;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDetails {
    private UUID saleId;
    private String paymentMethod;
    private BigDecimal amount;
    private String cardNumber;
    private String cardHolderName;
    private String cardExpiry;
    private String cardCvv;
}
