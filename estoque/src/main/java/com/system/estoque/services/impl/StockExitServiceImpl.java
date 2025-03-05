package com.system.estoque.services.impl;

import com.system.estoque.dtos.PageDTO;
import com.system.estoque.dtos.SaleDTO;
import com.system.estoque.dtos.SaleItemDTO;
import com.system.estoque.dtos.entities.StockExitDTO;
import com.system.estoque.entities.Item;
import com.system.estoque.entities.StockExit;
import com.system.estoque.entities.User;
import com.system.estoque.exeptions.NotFoundException;
import com.system.estoque.mappers.StockExitMapper;
import com.system.estoque.repositories.ItemRepository;
import com.system.estoque.repositories.StockExitRepository;
import com.system.estoque.repositories.UserRepository;
import com.system.estoque.services.StockExitService;
import com.system.estoque.services.specification.StockExitSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StockExitServiceImpl implements StockExitService {

    private final StockExitRepository stockExitRepository;
    private final StockExitMapper stockExitMapper;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    private final RabbitTemplate rabbitTemplate;

    @Override
    public PageDTO<StockExitDTO> findAll(String search, Pageable pageable) {
        Specification<StockExit> spec = Specification.where(StockExitSpecification.isNotDeleted())
                .and(StockExitSpecification.hasNameContaining(search));

        Page<StockExit> stockExitPage = stockExitRepository.findAll(spec, pageable);

        List<StockExitDTO> stockExitDTOs = stockExitMapper.toStockExitDTOs(stockExitPage.getContent());

        return new PageDTO<>(
                stockExitDTOs,
                stockExitPage.getTotalPages(),
                stockExitPage.getTotalElements(),
                stockExitPage.getNumber(),
                stockExitPage.getSize()
        );
    }

    @Override
    @Transactional
    @RabbitListener(queues = "stock.queue")
    public String create(SaleDTO saleDTO) {

        for (SaleItemDTO item : saleDTO.getItems()) {
            StockExit stockExit = new StockExit();
            stockExit.setItemId(item.getItemId());
            stockExit.setUserId(UUID.randomUUID());
            stockExit.setQuantity(item.getQuantity());
            stockExit.setDate_exit(LocalDateTime.now());

            stockExitRepository.save(stockExit);
        }

        System.out.println("Stock: Estoque atualizado, enviando resposta...");
        return "OK";
    }

    @Override
    public StockExitDTO findById(Long id) {
        StockExit stockExit = getStockExit(id);
        return stockExitMapper.toDto(stockExit);
    }

    @Override
    @Transactional
    public StockExitDTO update(StockExitDTO stockExitDTO) {
        StockExit stockExit = getStockExit(stockExitDTO.getId());

        stockExit.setQuantity(stockExitDTO.getQuantity());
        stockExit.setItem(getItem(stockExitDTO.getItemId()));
        stockExit.setUser(getUser(stockExitDTO.getUserId()));

        stockExitRepository.save(stockExit);

        return stockExitMapper.toDto(stockExit);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        StockExit stockExit = getStockExit(id);

        stockExit.setDeletedAt(LocalDateTime.now());

        stockExitRepository.save(stockExit);
    }

    private StockExit getStockExit(Long id) throws NotFoundException {
        return stockExitRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(()
                -> new NotFoundException("StockExit not found"));
    }

    private User getUser(UUID id) throws NotFoundException {
        return userRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(()
                -> new NotFoundException("User not found"));
    }

    private Item getItem(Long id) throws NotFoundException {
        return itemRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(()
                -> new NotFoundException("Item not found"));
    }
}
