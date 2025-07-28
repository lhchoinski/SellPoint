package com.system.sales.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.system.sales.dto.Product;

import java.util.List;
import java.util.UUID;

public interface InventoryService {

    List<Product> findProductsByIds(List<UUID> saleProductsIds) throws JsonProcessingException;
}
