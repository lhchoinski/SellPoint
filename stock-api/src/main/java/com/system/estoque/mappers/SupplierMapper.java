package com.system.estoque.mappers;

import com.system.estoque.dtos.entities.SupplierDTO;
import com.system.estoque.entities.Supplier;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SupplierMapper {

    Supplier toEntity(SupplierDTO supplierDTO);

    SupplierDTO toDto(Supplier supplier);

    List<SupplierDTO> toSupplierDTOs(List<Supplier> suppliers);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Supplier partialUpdate(SupplierDTO supplierDTO, @MappingTarget Supplier supplier);
}
