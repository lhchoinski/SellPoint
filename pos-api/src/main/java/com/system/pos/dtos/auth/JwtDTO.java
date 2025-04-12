package com.system.pos.dtos.auth;

import com.fasterxml.jackson.annotation.JsonView;
import com.system.pos.dtos.UserDTO;
import com.system.pos.dtos.groups.AppGroup;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtDTO {

    @JsonView(AppGroup.Response.class)
    private String accessToken;

    @JsonView(AppGroup.Response.class)
    private String refreshToken;

    @JsonView(AppGroup.Response.class)
    private UserDTO user;
}
