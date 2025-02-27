package com.system.estoque.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.system.estoque.dtos.PageDTO;
import com.system.estoque.dtos.entities.SupplierDTO;
import com.system.estoque.dtos.groups.AppGroup;
import com.system.estoque.services.SupplierService;
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
@RequestMapping("api/supplier")
@RequiredArgsConstructor
@Tag(name = "Supplier")
public class SupplierController {

    private final SupplierService supplierService;

    @Operation(summary = "Find all", description = "Find all suppliers")
    @GetMapping
    public PageDTO<SupplierDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return supplierService.findAll(search, pageable);
    }

    @Operation(summary = "Create", description = "Create supplier")
    @PostMapping
    @JsonView(AppGroup.Response.class)
    public ResponseEntity<SupplierDTO> create(
            @RequestBody
            @Valid
            @JsonView(AppGroup.Request.class)
            SupplierDTO supplierDTO
    ) {

        return ResponseEntity.ok(supplierService.create(supplierDTO));
    }

    @Operation(summary = "Find by id", description = "Find supplier by id")
    @GetMapping("/{id}")
    @JsonView(AppGroup.ResponsePage.class)
    public ResponseEntity<SupplierDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(supplierService.findById(id));
    }

    @Operation(summary = "Update", description = "Update supplier")
    @PutMapping("/{id}")
    public ResponseEntity<SupplierDTO> update(
            @PathVariable("id") Long id,
            @Validated(AppGroup.Request.class)
            @RequestBody
            @JsonView(AppGroup.Request.class)
            SupplierDTO supplierDTO
    ) {
        supplierDTO.setId(id);

        return ResponseEntity.ok(supplierService.update(supplierDTO));
    }

    @Operation(summary = "Delete", description = "Delete supplier")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        supplierService.delete(id);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Enable", description = "Enable supplier")
    @PutMapping({"/{id}/enable"})
    public ResponseEntity<Void> enable(@PathVariable("id") Long id) {
        supplierService.enable(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Disable", description = "Disable supplier")
    @PutMapping({"/{id}/disable"})
    public ResponseEntity<Void> disable(@PathVariable("id") Long id) {
        supplierService.disable(id);
        return ResponseEntity.ok().build();
    }
}
