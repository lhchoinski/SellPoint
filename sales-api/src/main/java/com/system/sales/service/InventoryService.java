package com.system.sales.service;

import com.system.sales.dto.ProductDTO;

import java.util.UUID;

public interface InventoryService {

    ProductDTO findProductsById(UUID saleProductsId);
}
