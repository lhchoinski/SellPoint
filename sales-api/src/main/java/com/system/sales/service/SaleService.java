package com.system.sales.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.system.sales.dto.SaleDTO;

public interface SaleService {
    void saleStartedProcess(SaleDTO saleDTO) throws JsonProcessingException;
}
