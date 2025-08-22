package com.system.sales.service;

import com.system.sales.dto.ProductInventoryDTO;

import java.util.UUID;

public interface InventoryService {

    ProductInventoryDTO findProductsById(UUID saleProductsId);
}
