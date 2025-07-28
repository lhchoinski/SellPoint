package com.system.sales.dto.payment;

import com.system.sales.dto.PaymentDetails;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentCommand {
    private PaymentDetails paymentDetails;
}
