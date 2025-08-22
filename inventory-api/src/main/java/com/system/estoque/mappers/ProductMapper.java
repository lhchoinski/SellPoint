package com.system.estoque.mappers;

import com.system.estoque.dtos.ProductInventoryDTO;
import com.system.estoque.dtos.entities.ProductDTO2;
import com.system.estoque.entities.Product;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    Product toEntity(ProductDTO2 productDTO2);

    ProductDTO2 toDto(Product product);

    List<ProductDTO2> toItemDTOs(List<Product> products);

    ProductInventoryDTO toItemDTOs2(Product product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product partialUpdate(ProductDTO2 productDTO2, @MappingTarget Product product);
}
