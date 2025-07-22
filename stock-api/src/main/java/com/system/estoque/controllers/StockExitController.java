package com.system.estoque.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.system.estoque.dtos.PageDTO;
import com.system.estoque.dtos.entities.StockExitDTO;
import com.system.estoque.dtos.groups.AppGroup;
import com.system.estoque.services.StockExitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/stock-exit")
@RequiredArgsConstructor
@Tag(name = "Stock Exit")
public class StockExitController {

    private final StockExitService stockExitService;

    @Operation(summary = "Find all", description = "Find all stockExits")
    @GetMapping
    public PageDTO<StockExitDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return stockExitService.findAll(search, pageable);
    }

    @Operation(summary = "Find by id", description = "Find stockExit by id")
    @GetMapping("/{id}")
    @JsonView(AppGroup.ResponsePage.class)
    public ResponseEntity<StockExitDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(stockExitService.findById(id));
    }

    @Operation(summary = "Update", description = "Update stockExit")
    @PutMapping("/{id}")
    public ResponseEntity<StockExitDTO> update(
            @PathVariable("id") Long id,
            @Validated(AppGroup.Request.class)
            @RequestBody
            @JsonView(AppGroup.Request.class)
            StockExitDTO stockExitDTO
    ) {
        stockExitDTO.setId(id);

        return ResponseEntity.ok(stockExitService.update(stockExitDTO));
    }

    @Operation(summary = "Delete", description = "Delete stockExit")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        stockExitService.delete(id);

        return ResponseEntity.ok().build();
    }
}
