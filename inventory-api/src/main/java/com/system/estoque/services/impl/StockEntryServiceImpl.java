package com.system.estoque.services.impl;

import com.system.estoque.dtos.PageDTO;
import com.system.estoque.dtos.entities.StockEntryDTO;
import com.system.estoque.entities.Product;
import com.system.estoque.entities.StockEntry;
import com.system.estoque.entities.Supplier;
import com.system.estoque.exeptions.NotFoundException;
import com.system.estoque.mappers.StockEntryMapper;
import com.system.estoque.repositories.ProductRepository;
import com.system.estoque.repositories.StockEntryRepository;
import com.system.estoque.repositories.SupplierRepository;
import com.system.estoque.services.StockEntryService;
import com.system.estoque.services.specification.StockEntrySpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StockEntryServiceImpl implements StockEntryService {

    private final StockEntryRepository stockEntryRepository;
    private final StockEntryMapper stockEntryMapper;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;

    @Override
    public PageDTO<StockEntryDTO> findAll(String search, Pageable pageable) {
        Specification<StockEntry> spec = Specification.where(StockEntrySpecification.isNotDeleted())
                .and(StockEntrySpecification.hasNameContaining(search));

        Page<StockEntry> stockEntryPage = stockEntryRepository.findAll(spec, pageable);

        List<StockEntryDTO> stockEntryDTOs = stockEntryMapper.toStockEntryDTOs(stockEntryPage.getContent());

        return new PageDTO<>(
                stockEntryDTOs,
                stockEntryPage.getTotalPages(),
                stockEntryPage.getTotalElements(),
                stockEntryPage.getNumber(),
                stockEntryPage.getSize()
        );
    }

    @Override
    @Transactional
    public StockEntryDTO create(StockEntryDTO stockEntryDTO) {

        Supplier supplier = getSupplier(stockEntryDTO.getSupplierId());
        Product product = getProduct(stockEntryDTO.getProductId());

        StockEntry stockEntry = stockEntryMapper.toEntity(stockEntryDTO);

        stockEntry.setProduct(product);
        stockEntry.setSupplier(supplier);
        stockEntry.setQuantity(stockEntryDTO.getQuantity());

        Integer updateQuantity = product.getQuantity() + stockEntry.getQuantity();

        product.setQuantity(updateQuantity);
        stockEntry.setDate_entry(LocalDateTime.now());

        stockEntryRepository.save(stockEntry);

        return stockEntryMapper.toDto(stockEntry);
    }

    @Override
    public StockEntryDTO findById(Long id) {
        StockEntry stockEntry = getStockEntry(id);
        return stockEntryMapper.toDto(stockEntry);
    }

    @Override
    @Transactional
    public StockEntryDTO update(StockEntryDTO stockEntryDTO) {
        StockEntry stockEntry = getStockEntry(stockEntryDTO.getId());

        stockEntry.setQuantity(stockEntryDTO.getQuantity());
        stockEntry.setProduct(getProduct(stockEntryDTO.getProductId()));
        stockEntry.setSupplier(getSupplier(stockEntryDTO.getSupplierId()));

        stockEntryRepository.save(stockEntry);

        return stockEntryMapper.toDto(stockEntry);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        StockEntry stockEntry = getStockEntry(id);

        stockEntry.setDeletedAt(LocalDateTime.now());

        stockEntryRepository.save(stockEntry);
    }

    private StockEntry getStockEntry(Long id) throws NotFoundException {
        return stockEntryRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(()
                -> new NotFoundException("StockEntry not found"));
    }

    private Supplier getSupplier(Long id) throws NotFoundException {
        return supplierRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(()
                -> new NotFoundException("Supplier not found"));
    }

    private Product getProduct(UUID id) throws NotFoundException {
        return productRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(()
                -> new NotFoundException("Item not found"));
    }
}
