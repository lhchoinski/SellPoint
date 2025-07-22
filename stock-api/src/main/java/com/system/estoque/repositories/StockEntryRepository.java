package com.system.estoque.repositories;

import com.system.estoque.entities.StockEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockEntryRepository extends JpaRepository<StockEntry, Long>, JpaSpecificationExecutor<StockEntry> {

    Optional<StockEntry> findByIdAndDeletedAtIsNull(Long id);
}
