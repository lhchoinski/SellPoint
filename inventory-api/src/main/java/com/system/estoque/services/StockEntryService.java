package com.system.estoque.services;

import com.system.estoque.dtos.PageDTO;
import com.system.estoque.dtos.entities.StockEntryDTO;
import org.springframework.data.domain.Pageable;

public interface StockEntryService {

    StockEntryDTO create(StockEntryDTO stockEntryDTO);

    PageDTO<StockEntryDTO> findAll(String search, Pageable pageable);

    StockEntryDTO findById(Long id);

    StockEntryDTO update(StockEntryDTO stockEntryDTO);

    void delete(Long id);

}
