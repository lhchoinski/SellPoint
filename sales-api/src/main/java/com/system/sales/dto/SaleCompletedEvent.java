package com.system.sales.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleCompletedEvent {
    private String saleId;
    private LocalDateTime completedAt;
    private String status;
}