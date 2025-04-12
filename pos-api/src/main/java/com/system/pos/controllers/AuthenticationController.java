package com.system.pos.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.system.pos.dtos.auth.JwtDTO;
import com.system.pos.dtos.groups.AppGroup;
import com.system.pos.dtos.request.LoginDTO;
import com.system.pos.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Tag(name = "Login")
@RequestMapping("${api-prefix}/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @JsonView(AppGroup.Response.class)
    @Operation(summary = "Login", description = "Login")
    @PostMapping("/login")
    public ResponseEntity<JwtDTO> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(authenticationService.authenticate(loginDTO));
    }

    @Operation(summary = "Refresh Token", description = "Refresh Token")
    @PostMapping("/refresh-token")
    public ResponseEntity<JwtDTO> refreshToken() {
        return ResponseEntity.ok(authenticationService.refreshToken());
    }

}