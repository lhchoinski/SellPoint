package com.system.estoque.components.schedulers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.system.estoque.producers.PaymenyPublisher;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxScheduler {

    private final PaymenyPublisher paymenyPublisher;

    @Scheduled(cron = "*/1 * * * * *")
    public void scheduleOutbox() {
        log.info("Starting outbox scheduler");
        paymenyPublisher.publishReserveEvent();
    }
}
