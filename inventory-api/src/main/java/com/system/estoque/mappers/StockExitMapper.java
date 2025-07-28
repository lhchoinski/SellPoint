package com.system.estoque.mappers;

import com.system.estoque.dtos.entities.StockExitDTO;
import com.system.estoque.entities.StockExit;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface StockExitMapper {

    StockExit toEntity(StockExitDTO stockExitDTO);

    StockExitDTO toDto(StockExit stockExit);

    List<StockExitDTO> toStockExitDTOs(List<StockExit> stockExits);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    StockExit partialUpdate(StockExitDTO stockExitDTO, @MappingTarget StockExit stockExit);
}
