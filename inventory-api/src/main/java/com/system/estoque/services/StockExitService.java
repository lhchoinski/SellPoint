package com.system.estoque.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.system.estoque.dtos.PageDTO;
import com.system.estoque.dtos.SaleDTO;
import com.system.estoque.dtos.StockReserveCommand;
import com.system.estoque.dtos.entities.StockExitDTO;
import org.springframework.data.domain.Pageable;

public interface StockExitService {

    String create(SaleDTO saleDTO);

    void reserveStock(StockReserveCommand command) throws JsonProcessingException;

    PageDTO<StockExitDTO> findAll(String search, Pageable pageable);

    StockExitDTO findById(Long id);

    StockExitDTO update(StockExitDTO stockExitDTO);

    void delete(Long id);

}
