package com.system.pos.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.system.pos.dtos.SaleDTO;
import com.system.pos.dtos.groups.AppGroup;
import com.system.pos.services.PosService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/pos")
@RequiredArgsConstructor
@Tag(name = "Pos (Point Of Sale)")
public class PosController {

    private final PosService posService;

    @PostMapping("/sale")
    public String createSale(
            @Validated({AppGroup.Request.class})
            @RequestBody
            @JsonView({AppGroup.Request.class})
            SaleDTO saleRequest) {
        return posService.initSale(saleRequest);
    }
}
