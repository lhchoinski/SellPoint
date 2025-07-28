package com.system.estoque.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.system.estoque.dtos.entities.UserDTO;
import com.system.estoque.dtos.groups.AppGroup;
import com.system.estoque.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
@Tag(name = "User")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Create", description = "Create user")
    @PostMapping("/users")
    @JsonView({AppGroup.Response.class})
    public ResponseEntity<UserDTO> create(
            @RequestBody
            @Valid
            @JsonView(AppGroup.Request.class)
            UserDTO userDto
    ) {

        return ResponseEntity.ok(userService.create(userDto));
    }

    @Operation(summary = "Find by id", description = "Find user by id")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

}