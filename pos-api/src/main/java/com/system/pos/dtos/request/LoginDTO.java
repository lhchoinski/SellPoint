package com.system.pos.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    @NotNull(message = "required_message")
    private String username;

    @NotNull(message = "required_message")
    private String password;
}
