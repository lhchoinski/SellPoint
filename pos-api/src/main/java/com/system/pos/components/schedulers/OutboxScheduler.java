package com.system.pos.components.schedulers;

import com.system.pos.messaging.OutboxPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxScheduler {

    private final OutboxPublisher outboxPublisher;

    @Scheduled(cron = "*/1 * * * * *")
    public void scheduleOutbox() {
        log.info("Starting outbox scheduler");
        outboxPublisher.publishPendingEvent();
    }
}
