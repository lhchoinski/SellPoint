package com.system.pos.repositories;

import com.system.pos.entities.OutboxEvent;
import com.system.pos.enums.OutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, UUID> {

    List<OutboxEvent> findAllByStatus(OutboxStatus status);

}
