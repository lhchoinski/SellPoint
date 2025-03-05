package com.system.estoque.services;

import com.system.estoque.dtos.PageDTO;
import com.system.estoque.dtos.SaleDTO;
import com.system.estoque.dtos.entities.StockExitDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.handler.annotation.Headers;

import java.util.List;
import java.util.Map;

public interface StockExitService {

    String create(SaleDTO saleDTO);

    PageDTO<StockExitDTO> findAll(String search, Pageable pageable);

    StockExitDTO findById(Long id);

    StockExitDTO update(StockExitDTO stockExitDTO);

    void delete(Long id);

}
