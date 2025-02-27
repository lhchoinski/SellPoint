package com.system.estoque.mappers;

import com.system.estoque.dtos.entities.StockEntryDTO;
import com.system.estoque.entities.StockEntry;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface StockEntryMapper {

    StockEntry toEntity(StockEntryDTO stockEntryDTO);

    StockEntryDTO toDto(StockEntry stockEntry);

    List<StockEntryDTO> toStockEntryDTOs(List<StockEntry> stockEntrys);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    StockEntry partialUpdate(StockEntryDTO stockEntryDTO, @MappingTarget StockEntry stockEntry);
}
