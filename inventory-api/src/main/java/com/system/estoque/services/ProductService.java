package com.system.estoque.services;

import com.system.estoque.dtos.PageDTO;
import com.system.estoque.dtos.entities.ProductDTO2;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductService {

    PageDTO<ProductDTO2> findAll(String search, Pageable pageable);

    ProductDTO2 create(ProductDTO2 productDTO2);

    ProductDTO2 findById(UUID id);

    ProductDTO2 update(ProductDTO2 productDTO2);

    void delete(UUID id);

    void enable(UUID id);

    void disable(UUID id);

}
