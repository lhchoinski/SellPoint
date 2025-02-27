package com.system.pos.services;

import com.system.pos.dtos.CustomerDTO;
import com.system.pos.dtos.PageDTO;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

    PageDTO<CustomerDTO> findAll(String search, Pageable pageable);

    CustomerDTO create(CustomerDTO customerDTO);

    CustomerDTO findById(Long id);

    CustomerDTO update(CustomerDTO customerDTO);

    void delete(Long id);

    void enable(Long id);

    void disable(Long id);

}
