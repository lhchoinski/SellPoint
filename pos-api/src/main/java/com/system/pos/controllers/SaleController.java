package com.system.pos.controllers;

import com.system.pos.dtos.SaleDTO;
import com.system.pos.services.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pos/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @PostMapping
    public void startSale(@RequestBody SaleDTO saleDTO) {
        saleService.startSale(saleDTO);
    }
}
