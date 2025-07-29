package com.system.estoque.services.impl;

import com.system.estoque.dtos.entities.ProductDTO2;
import com.system.estoque.dtos.PageDTO;
import com.system.estoque.entities.Product;
import com.system.estoque.exeptions.NotFoundException;
import com.system.estoque.mappers.ProductMapper;
import com.system.estoque.repositories.ProductRepository;
import com.system.estoque.services.ProductService;
import com.system.estoque.services.specification.ItemSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    @Override
    public PageDTO<ProductDTO2> findAll(String search, Pageable pageable) {
        Specification<Product> spec = Specification.where(ItemSpecification.isNotDeleted())
                .and(ItemSpecification.hasNameContaining(search));

        Page<Product> itemPage = productRepository.findAll(spec, pageable);

        List<ProductDTO2> productDTO2s = productMapper.toItemDTOs(itemPage.getContent());

        return new PageDTO<>(
                productDTO2s,
                itemPage.getTotalPages(),
                itemPage.getTotalElements(),
                itemPage.getNumber(),
                itemPage.getSize()
        );
    }

    @Override
    @Transactional
    public ProductDTO2 create(ProductDTO2 productDTO2) {

        if (productDTO2.getName() != null) {
            productDTO2.setName(productDTO2.getName().toUpperCase());
        }

        Product product = productMapper.toEntity(productDTO2);
//        product.setActive(true);

        productRepository.save(product);

        return productMapper.toDto(product);
    }

    @Override
    public ProductDTO2 findById(UUID id) {
        Product product = getItem(id);
        return productMapper.toDto(product);
    }

    @Override
    @Transactional
    public ProductDTO2 update(ProductDTO2 productDTO2) {
        Product product = getItem(productDTO2.getId());

        productMapper.partialUpdate(productDTO2, product);
        productRepository.save(product);

        return productMapper.toDto(product);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Product product = getItem(id);

//        if (product.getActive()) {
//            throw new BadRequestException("Item is still active");
//        }

//        product.setDeletedAt(LocalDateTime.now());

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
        return productRepository.findById(id).orElseThrow(()
                -> new NotFoundException("Item not found"));
    }
}
