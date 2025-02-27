package com.system.estoque.services;

import com.system.estoque.dtos.PageDTO;
import com.system.estoque.dtos.entities.SupplierDTO;
import org.springframework.data.domain.Pageable;

public interface SupplierService {

    SupplierDTO create(SupplierDTO supplierDTO);

    PageDTO<SupplierDTO> findAll(String search, Pageable pageable);

    SupplierDTO findById(Long id);

    SupplierDTO update(SupplierDTO supplierDTO);

    void delete(Long id);

    void enable(Long id);

    void disable(Long id);
}
