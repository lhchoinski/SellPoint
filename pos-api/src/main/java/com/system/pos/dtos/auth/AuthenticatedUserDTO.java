package com.system.pos.dtos.auth;

import com.system.pos.interfaces.AuthenticatedUser;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class AuthenticatedUserDTO implements AuthenticatedUser, Serializable {
    private UUID id;
    private String name;
    private String username;
    private String email;
    private String theme;

}
