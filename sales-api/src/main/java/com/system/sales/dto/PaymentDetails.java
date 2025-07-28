package com.system.sales.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class PaymentDetails {

    private String paymentMethod;
    private BigDecimal amount;
    private String cardNumber;
    private String cardHolderName;
    private String cardExpiry;
    private String cardCvv;
}
