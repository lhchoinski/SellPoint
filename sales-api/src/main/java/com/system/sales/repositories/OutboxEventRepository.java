package com.system.sales.repositories;

import com.system.sales.entities.OutboxEvent;
import com.system.sales.enums.OutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, UUID> {

    List<OutboxEvent> findAllByStatus(OutboxStatus status);

}
