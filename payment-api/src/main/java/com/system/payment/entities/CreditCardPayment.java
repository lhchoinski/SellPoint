package com.system.payment.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "credit_card_payments")
@PrimaryKeyJoinColumn(name = "payment_id")
@Getter
@Setter
public class CreditCardPayment extends Payment {

    @Column(name = "installments")
    private Integer installments; // Número de parcelas

    @Column(name = "card_brand")
    private String cardBrand; // Ex: "VISA", "MASTERCARD"

    @Column(name = "card_last_four_digits")
    private String cardLastFourDigits;

    @Column(name = "installment_value")
    private BigDecimal installmentValue; // Valor de cada parcela

}