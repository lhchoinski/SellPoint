package com.system.payment.enums;

public enum PaymentStatus {
    PENDING,      // Aguardando pagamento (ex: PIX não pago)
    PROCESSING,   // Pagamento sendo processado pela gateway
    APPROVED,     // Pagamento aprovado
    REJECTED,     // Pagamento recusado
    REFUNDED,     // Estornado
    CANCELED
}
