package com.system.sales.repositories;

import com.system.sales.entities.SaleProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SaleProductRepository extends JpaRepository<SaleProduct, UUID> {
}
