package com.system.pos.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.system.pos.dtos.CustomerDTO;
import com.system.pos.dtos.PageDTO;
import com.system.pos.dtos.groups.AppGroup;
import com.system.pos.services.CustomerService;
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
@RequestMapping("api/customer")
@RequiredArgsConstructor
@Tag(name = "Customer")
public class CustomerController {

    private final CustomerService customerService;

    @Operation(summary = "Find all", description = "Find all customers")
    @GetMapping
    public PageDTO<CustomerDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return customerService.findAll(search, pageable);
    }

    @Operation(summary = "Create", description = "Create customer")
    @PostMapping
    @JsonView(AppGroup.Response.class)
    public ResponseEntity<CustomerDTO> create(
            @RequestBody
            @Valid
            @JsonView(AppGroup.Request.class)
            CustomerDTO customerDTO
    ) {

        return ResponseEntity.ok(customerService.create(customerDTO));
    }

    @Operation(summary = "Find by id", description = "Find customer by id")
    @GetMapping("/{id}")
    @JsonView(AppGroup.ResponsePage.class)
    public ResponseEntity<CustomerDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(customerService.findById(id));
    }

    @Operation(summary = "Update", description = "Update customer")
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> update(
            @PathVariable("id") Long id,
            @Validated(AppGroup.Request.class)
            @RequestBody
            @JsonView(AppGroup.Request.class)
            CustomerDTO customerDTO
    ) {
        customerDTO.setId(id);

        return ResponseEntity.ok(customerService.update(customerDTO));
    }

    @Operation(summary = "Delete", description = "Delete customer")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        customerService.delete(id);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Enable", description = "Enable customer")
    @PutMapping({"/{id}/enable"})
    public ResponseEntity<Void> enable(@PathVariable("id") Long id) {
        customerService.enable(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Disable", description = "Disable customer")
    @PutMapping({"/{id}/disable"})
    public ResponseEntity<Void> disable(@PathVariable("id") Long id) {
        customerService.disable(id);
        return ResponseEntity.ok().build();
    }
}
