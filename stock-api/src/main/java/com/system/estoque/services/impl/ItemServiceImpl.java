package com.system.estoque.services.impl;

import com.system.estoque.dtos.entities.ItemDTO;
import com.system.estoque.dtos.PageDTO;
import com.system.estoque.entities.Item;
import com.system.estoque.exeptions.BadRequestException;
import com.system.estoque.exeptions.NotFoundException;
import com.system.estoque.mappers.ItemMapper;
import com.system.estoque.repositories.ItemRepository;
import com.system.estoque.services.ItemService;
import com.system.estoque.services.specification.ItemSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemMapper itemMapper;
    private final ItemRepository itemRepository;

    @Override
    public PageDTO<ItemDTO> findAll(String search, Pageable pageable) {
        Specification<Item> spec = Specification.where(ItemSpecification.isNotDeleted())
                .and(ItemSpecification.hasNameContaining(search));

        Page<Item> itemPage = itemRepository.findAll(spec, pageable);

        List<ItemDTO> itemDTOs = itemMapper.toItemDTOs(itemPage.getContent());

        return new PageDTO<>(
                itemDTOs,
                itemPage.getTotalPages(),
                itemPage.getTotalElements(),
                itemPage.getNumber(),
                itemPage.getSize()
        );
    }

    @Override
    @Transactional
    public ItemDTO create(ItemDTO itemDTO) {

        if (itemDTO.getName() != null) {
            itemDTO.setName(itemDTO.getName().toUpperCase());
        }

        Item item = itemMapper.toEntity(itemDTO);
        item.setActive(true);

        itemRepository.save(item);

        return itemMapper.toDto(item);
    }

    @Override
    public ItemDTO findById(Long id) {
        Item item = getItem(id);
        return itemMapper.toDto(item);
    }

    @Override
    @Transactional
    public ItemDTO update(ItemDTO itemDTO) {
        Item item = getItem(itemDTO.getId());

        itemMapper.partialUpdate(itemDTO, item);
        itemRepository.save(item);

        return itemMapper.toDto(item);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Item item = getItem(id);

        if (item.getActive()) {
            throw new BadRequestException("Item is still active");
        }

        item.setDeletedAt(LocalDateTime.now());

        itemRepository.save(item);
    }

    @Override
    @Transactional
    public void enable(Long id) {
        Item item = getItem(id);

        if (item.getActive()) {
            throw new BadRequestException("Item already active");
        }

        item.setActive(true);

        itemRepository.save(item);

    }

    @Override
    @Transactional
    public void disable(Long id) {
        Item item = getItem(id);

        item.setActive(false);

        itemRepository.save(item);

    }

    private Item getItem(Long id) throws NotFoundException {
        return itemRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(()
                -> new NotFoundException("Item not found"));
    }
}
