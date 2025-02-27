package com.system.estoque.services.impl;

import com.system.estoque.dtos.PageDTO;
import com.system.estoque.dtos.entities.SupplierDTO;
import com.system.estoque.entities.Supplier;
import com.system.estoque.exeptions.BadRequestException;
import com.system.estoque.exeptions.NotFoundException;
import com.system.estoque.mappers.SupplierMapper;
import com.system.estoque.repositories.SupplierRepository;
import com.system.estoque.services.SupplierService;
import com.system.estoque.services.specification.SupplierSpecification;
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
public class SupplierServiceImpl implements SupplierService {

    private final SupplierMapper supplierMapper;
    private final SupplierRepository supplierRepository;

    @Override
    public PageDTO<SupplierDTO> findAll(String search, Pageable pageable) {
        Specification<Supplier> spec = Specification.where(SupplierSpecification.isNotDeleted())
                .and(SupplierSpecification.hasNameContaining(search));

        Page<Supplier> supplierPage = supplierRepository.findAll(spec, pageable);

        List<SupplierDTO> supplierDTOs = supplierMapper.toSupplierDTOs(supplierPage.getContent());

        return new PageDTO<>(
                supplierDTOs,
                supplierPage.getTotalPages(),
                supplierPage.getTotalElements(),
                supplierPage.getNumber(),
                supplierPage.getSize()
        );
    }

    @Override
    @Transactional
    public SupplierDTO create(SupplierDTO supplierDTO) {

        if (supplierDTO.getName() != null) {
            supplierDTO.setName(supplierDTO.getName().toUpperCase());
        }

        Supplier supplier = supplierMapper.toEntity(supplierDTO);
        supplier.setActive(true);

        supplierRepository.save(supplier);

        return supplierMapper.toDto(supplier);
    }

    @Override
    public SupplierDTO findById(Long id) {
        Supplier supplier = getSupplier(id);
        return supplierMapper.toDto(supplier);
    }

    @Override
    @Transactional
    public SupplierDTO update(SupplierDTO supplierDTO) {
        Supplier supplier = getSupplier(supplierDTO.getId());

        supplierMapper.partialUpdate(supplierDTO, supplier);
        supplierRepository.save(supplier);

        return supplierMapper.toDto(supplier);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Supplier supplier = getSupplier(id);

        if (supplier.getActive()) {
            throw new BadRequestException("Supplier is still active");
        }

        supplier.setDeletedAt(LocalDateTime.now());

        supplierRepository.save(supplier);
    }

    @Override
    @Transactional
    public void enable(Long id) {
        Supplier supplier = getSupplier(id);

        if (supplier.getActive()) {
            throw new BadRequestException("Supplier already active");
        }

        supplier.setActive(true);

        supplierRepository.save(supplier);

    }

    @Override
    @Transactional
    public void disable(Long id) {
        Supplier supplier = getSupplier(id);

        supplier.setActive(false);

        supplierRepository.save(supplier);

    }

    private Supplier getSupplier(Long id) throws NotFoundException {
        return supplierRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(()
                -> new NotFoundException("Supplier not found"));
    }
}
