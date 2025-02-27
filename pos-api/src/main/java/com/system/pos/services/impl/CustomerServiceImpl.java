package com.system.pos.services.impl;

import com.system.pos.dtos.CustomerDTO;
import com.system.pos.dtos.PageDTO;
import com.system.pos.entities.Customer;
import com.system.pos.exeptions.BadRequestException;
import com.system.pos.exeptions.NotFoundException;
import com.system.pos.mappers.CustomerMapper;
import com.system.pos.repositories.CustomerRepository;
import com.system.pos.services.CustomerService;
import com.system.pos.services.specification.CustomerSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    @Override
    public PageDTO<CustomerDTO> findAll(String search, Pageable pageable) {
        Specification<Customer> spec = Specification.where(CustomerSpecification.isNotDeleted())
                .and(CustomerSpecification.hasNameContaining(search));

        Page<Customer> customerPage = customerRepository.findAll(spec, pageable);

        List<CustomerDTO> customerDTOs = customerMapper.toCustomerDTOs(customerPage.getContent());

        return new PageDTO<>(
                customerDTOs,
                customerPage.getTotalPages(),
                customerPage.getTotalElements(),
                customerPage.getNumber(),
                customerPage.getSize()
        );
    }

    @Override
    @Transactional
    public CustomerDTO create(CustomerDTO customerDTO) {

        if (customerDTO.getName() != null) {
            customerDTO.setName(customerDTO.getName().toUpperCase());
        }

        Customer customer = customerMapper.toEntity(customerDTO);
        customer.setActive(true);

        customerRepository.save(customer);

        return customerMapper.toDto(customer);
    }

    @Override
    public CustomerDTO findById(Long id) {
        Customer customer = getCustomer(id);
        return customerMapper.toDto(customer);
    }

    @Override
    @Transactional
    public CustomerDTO update(CustomerDTO customerDTO) {
        Customer customer = getCustomer(customerDTO.getId());

        customerMapper.partialUpdate(customerDTO, customer);
        customerRepository.save(customer);

        return customerMapper.toDto(customer);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Customer customer = getCustomer(id);

        if (customer.getActive()) {
            throw new BadRequestException("Customer is still active");
        }

        customer.setDeletedAt(LocalDateTime.now());

        customerRepository.save(customer);
    }

    @Override
    @Transactional
    public void enable(Long id) {
        Customer customer = getCustomer(id);

        if (customer.getActive()) {
            throw new BadRequestException("Customer already active");
        }

        customer.setActive(true);

        customerRepository.save(customer);

    }

    @Override
    @Transactional
    public void disable(Long id) {
        Customer customer = getCustomer(id);

        customer.setActive(false);

        customerRepository.save(customer);

    }

    private Customer getCustomer(Long id) throws NotFoundException {
        return customerRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(()
                -> new NotFoundException("Customer not found"));
    }
}
