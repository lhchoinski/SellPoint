package com.system.estoque.mappers;

import com.system.estoque.dtos.entities.ItemDTO;
import com.system.estoque.entities.Item;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemMapper {

    Item toEntity(ItemDTO itemDTO);

    ItemDTO toDto(Item item);

    List<ItemDTO> toItemDTOs(List<Item> items);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Item partialUpdate(ItemDTO itemDTO, @MappingTarget Item item);
}
