package com.system.pos.dtos.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRefreshToken {
    private String refreshToken;
}
