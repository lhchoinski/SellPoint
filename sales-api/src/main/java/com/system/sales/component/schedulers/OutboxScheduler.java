package com.system.sales.component.schedulers;

import com.system.sales.producer.InventoryPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxScheduler {

    private final InventoryPublisher inventoryPublisher;

    @Scheduled(cron = "*/1 * * * * *")
    public void scheduleOutbox() {
        log.info("Starting outbox scheduler");
        inventoryPublisher.publishReserveEvent();
    }
}
