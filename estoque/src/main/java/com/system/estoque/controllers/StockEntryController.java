package com.system.estoque.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.system.estoque.dtos.PageDTO;
import com.system.estoque.dtos.entities.StockEntryDTO;
import com.system.estoque.dtos.groups.AppGroup;
import com.system.estoque.services.StockEntryService;
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
@RequestMapping("api/stock-entry")
@RequiredArgsConstructor
@Tag(name = "Stock Entry")
public class StockEntryController {

    private final StockEntryService stockEntryService;

    @Operation(summary = "Find all", description = "Find all stockEntrys")
    @GetMapping
    public PageDTO<StockEntryDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return stockEntryService.findAll(search, pageable);
    }

    @Operation(summary = "Create", description = "Create stockEntry")
    @PostMapping
    @JsonView(AppGroup.ResponsePage.class)
    public ResponseEntity<StockEntryDTO> create(
            @RequestBody
            @Valid
            @JsonView(AppGroup.Request.class)
            StockEntryDTO stockEntryDTO
    ) {

        return ResponseEntity.ok(stockEntryService.create(stockEntryDTO));
    }

    @Operation(summary = "Find by id", description = "Find stockEntry by id")
    @GetMapping("/{id}")
    @JsonView(AppGroup.ResponsePage.class)
    public ResponseEntity<StockEntryDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(stockEntryService.findById(id));
    }

    @Operation(summary = "Update", description = "Update stockEntry")
    @PutMapping("/{id}")
    public ResponseEntity<StockEntryDTO> update(
            @PathVariable("id") Long id,
            @Validated(AppGroup.Request.class)
            @RequestBody
            @JsonView(AppGroup.Request.class)
            StockEntryDTO stockEntryDTO
    ) {
        stockEntryDTO.setId(id);

        return ResponseEntity.ok(stockEntryService.update(stockEntryDTO));
    }

    @Operation(summary = "Delete", description = "Delete stockEntry")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        stockEntryService.delete(id);

        return ResponseEntity.ok().build();
    }
}
