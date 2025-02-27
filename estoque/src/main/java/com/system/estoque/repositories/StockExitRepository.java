package com.system.estoque.repositories;

import com.system.estoque.entities.StockExit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface StockExitRepository extends JpaRepository<StockExit, Long>, JpaSpecificationExecutor<StockExit> {

    Optional<StockExit> findByIdAndDeletedAtIsNull(Long id);

}
