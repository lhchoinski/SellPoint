package com.system.pos.controllers;

import com.system.pos.interfaces.AuthenticatedUser;
import com.system.pos.services.AuthenticatedUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Usuários")
@RestController
@RequestMapping("${api-prefix}/user/authenticated")
@RequiredArgsConstructor
public class AuthenticatedUserRestController {

    private final AuthenticatedUserService authenticatedUserService;


    @Operation(summary = "Usuário logado", description = "Usuário logado")
    @GetMapping
    public ResponseEntity<AuthenticatedUser> authenticatedUser() {
        return ResponseEntity.ok(authenticatedUserService.getAuthenticatedUser());
    }
}
