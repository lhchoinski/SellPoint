package com.system.estoque.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.system.estoque.dtos.entities.ProductDTO;
import com.system.estoque.dtos.PageDTO;
import com.system.estoque.dtos.groups.AppGroup;
import com.system.estoque.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/item")
@RequiredArgsConstructor
@Tag(name = "Item")
public class ItemController {

    private final ProductService productService;

    @Operation(summary = "Find all", description = "Find all items")
    @GetMapping
    public PageDTO<ProductDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return productService.findAll(search, pageable);
    }

    @Operation(summary = "Create", description = "Create item")
    @PostMapping
    @JsonView(AppGroup.Response.class)
    public ResponseEntity<ProductDTO> create(
            @RequestBody
            @Valid
            @JsonView(AppGroup.Request.class)
            ProductDTO productDTO
    ) {

        return ResponseEntity.ok(productService.create(productDTO));
    }

    @Operation(summary = "Find by id", description = "Find item by id")
    @GetMapping("/{id}")
    @JsonView(AppGroup.ResponsePage.class)
    public ResponseEntity<ProductDTO> findById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @Operation(summary = "Update", description = "Update item")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(
            @PathVariable("id") UUID id,
            @Validated(AppGroup.Request.class)
            @RequestBody
            @JsonView(AppGroup.Request.class)
            ProductDTO productDTO
    ) {
        productDTO.setId(id);

        return ResponseEntity.ok(productService.update(productDTO));
    }

    @Operation(summary = "Delete", description = "Delete item")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        productService.delete(id);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Enable", description = "Enable item")
    @PutMapping({"/{id}/enable"})
    public ResponseEntity<Void> enable(@PathVariable("id") UUID id) {
        productService.enable(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Disable", description = "Disable item")
    @PutMapping({"/{id}/disable"})
    public ResponseEntity<Void> disable(@PathVariable("id") UUID id) {
        productService.disable(id);
        return ResponseEntity.ok().build();
    }
}
