package com.system.sales.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleFailedEvent {
    private String saleId;
    private LocalDateTime failedAt;
    private String reason;
}