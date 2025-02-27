package com.system.estoque.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.system.estoque.dtos.entities.ItemDTO;
import com.system.estoque.dtos.PageDTO;
import com.system.estoque.dtos.groups.AppGroup;
import com.system.estoque.services.ItemService;
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
@RequestMapping("api/item")
@RequiredArgsConstructor
@Tag(name = "Item")
public class ItemController {

    private final ItemService itemService;

    @Operation(summary = "Find all", description = "Find all items")
    @GetMapping
    public PageDTO<ItemDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return itemService.findAll(search, pageable);
    }

    @Operation(summary = "Create", description = "Create item")
    @PostMapping
    @JsonView(AppGroup.Response.class)
    public ResponseEntity<ItemDTO> create(
            @RequestBody
            @Valid
            @JsonView(AppGroup.Request.class)
            ItemDTO itemDTO
    ) {

        return ResponseEntity.ok(itemService.create(itemDTO));
    }

    @Operation(summary = "Find by id", description = "Find item by id")
    @GetMapping("/{id}")
    @JsonView(AppGroup.ResponsePage.class)
    public ResponseEntity<ItemDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(itemService.findById(id));
    }

    @Operation(summary = "Update", description = "Update item")
    @PutMapping("/{id}")
    public ResponseEntity<ItemDTO> update(
            @PathVariable("id") Long id,
            @Validated(AppGroup.Request.class)
            @RequestBody
            @JsonView(AppGroup.Request.class)
            ItemDTO itemDTO
    ) {
        itemDTO.setId(id);

        return ResponseEntity.ok(itemService.update(itemDTO));
    }

    @Operation(summary = "Delete", description = "Delete item")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        itemService.delete(id);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Enable", description = "Enable item")
    @PutMapping({"/{id}/enable"})
    public ResponseEntity<Void> enable(@PathVariable("id") Long id) {
        itemService.enable(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Disable", description = "Disable item")
    @PutMapping({"/{id}/disable"})
    public ResponseEntity<Void> disable(@PathVariable("id") Long id) {
        itemService.disable(id);
        return ResponseEntity.ok().build();
    }
}
