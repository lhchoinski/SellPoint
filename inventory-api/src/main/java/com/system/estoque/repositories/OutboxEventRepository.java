package com.system.estoque.repositories;


import com.system.estoque.entities.OutboxEvent;
import com.system.estoque.enums.OutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, UUID> {

    List<OutboxEvent> findAllByStatus(OutboxStatus status);

}
