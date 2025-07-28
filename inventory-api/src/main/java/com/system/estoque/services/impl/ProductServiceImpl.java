package com.system.estoque.services.impl;

import com.system.estoque.dtos.entities.ProductDTO;
import com.system.estoque.dtos.PageDTO;
import com.system.estoque.entities.Product;
import com.system.estoque.exeptions.NotFoundException;
import com.system.estoque.mappers.ItemMapper;
import com.system.estoque.repositories.ProductRepository;
import com.system.estoque.services.ProductService;
import com.system.estoque.services.specification.ItemSpecification;
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
public class ProductServiceImpl implements ProductService {

    private final ItemMapper itemMapper;
    private final ProductRepository productRepository;

    @Override
    public PageDTO<ProductDTO> findAll(String search, Pageable pageable) {
        Specification<Product> spec = Specification.where(ItemSpecification.isNotDeleted())
                .and(ItemSpecification.hasNameContaining(search));

        Page<Product> itemPage = productRepository.findAll(spec, pageable);

        List<ProductDTO> productDTOS = itemMapper.toItemDTOs(itemPage.getContent());

        return new PageDTO<>(
                productDTOS,
                itemPage.getTotalPages(),
                itemPage.getTotalElements(),
                itemPage.getNumber(),
                itemPage.getSize()
        );
    }

    @Override
    @Transactional
    public ProductDTO create(ProductDTO productDTO) {

        if (productDTO.getName() != null) {
            productDTO.setName(productDTO.getName().toUpperCase());
        }

        Product product = itemMapper.toEntity(productDTO);
//        product.setActive(true);

        productRepository.save(product);

        return itemMapper.toDto(product);
    }

    @Override
    public ProductDTO findById(UUID id) {
        Product product = getItem(id);
        return itemMapper.toDto(product);
    }

    @Override
    @Transactional
    public ProductDTO update(ProductDTO productDTO) {
        Product product = getItem(productDTO.getId());

        itemMapper.partialUpdate(productDTO, product);
        productRepository.save(product);

        return itemMapper.toDto(product);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Product product = getItem(id);

//        if (product.getActive()) {
//            throw new BadRequestException("Item is still active");
//        }

        product.setDeletedAt(LocalDateTime.now());

        productRepository.save(product);
    }

    @Override
    @Transactional
    public void enable(UUID id) {
        Product product = getItem(id);

//        if (product.getActive()) {
//            throw new BadRequestException("Item already active");
//        }
//
//        product.setActive(true);

        productRepository.save(product);

    }

    @Override
    @Transactional
    public void disable(UUID id) {
        Product product = getItem(id);

//        product.setActive(false);

        productRepository.save(product);

    }

    private Product getItem(UUID id) throws NotFoundException {
        return productRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(()
                -> new NotFoundException("Item not found"));
    }
}
