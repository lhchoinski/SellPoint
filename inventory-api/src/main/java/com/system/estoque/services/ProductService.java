package com.system.estoque.services;

import com.system.estoque.dtos.PageDTO;
import com.system.estoque.dtos.entities.ProductDTO;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductService {

    PageDTO<ProductDTO> findAll(String search, Pageable pageable);

    ProductDTO create(ProductDTO productDTO);

    ProductDTO findById(UUID id);

    ProductDTO update(ProductDTO productDTO);

    void delete(UUID id);

    void enable(UUID id);

    void disable(UUID id);

}
