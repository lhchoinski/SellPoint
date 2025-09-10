package com.system.estoque.entities;

import com.system.estoque.enums.OutboxStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "outbox_event")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutboxEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String aggregateType;
    private String aggregateId;

    @Column(columnDefinition = "TEXT")
    private String payload;

    private String eventType;

    @Enumerated(EnumType.STRING)
    private OutboxStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime sentAt;
}
