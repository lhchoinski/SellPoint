package com.system.estoque.services;

import com.system.estoque.dtos.PageDTO;
import com.system.estoque.dtos.entities.ItemDTO;
import org.springframework.data.domain.Pageable;

public interface ItemService {

    PageDTO<ItemDTO> findAll(String search, Pageable pageable);

    ItemDTO create(ItemDTO itemDTO);

    ItemDTO findById(Long id);

    ItemDTO update(ItemDTO itemDTO);

    void delete(Long id);

    void enable(Long id);

    void disable(Long id);

}
