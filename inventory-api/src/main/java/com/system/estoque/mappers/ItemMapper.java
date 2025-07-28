package com.system.estoque.mappers;

import com.system.estoque.dtos.entities.ProductDTO;
import com.system.estoque.entities.Product;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemMapper {

    Product toEntity(ProductDTO productDTO);

    ProductDTO toDto(Product product);

    List<ProductDTO> toItemDTOs(List<Product> products);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product partialUpdate(ProductDTO productDTO, @MappingTarget Product product);
}
