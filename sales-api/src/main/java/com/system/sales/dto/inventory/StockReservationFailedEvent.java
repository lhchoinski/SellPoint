package com.system.sales.dto.inventory;

import lombok.Data;

import java.util.UUID;

@Data
public class StockReservationFailedEvent {
    private UUID saleId;
    private String failedMessage;
}
