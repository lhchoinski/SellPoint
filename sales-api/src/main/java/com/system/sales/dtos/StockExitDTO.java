package com.system.sales.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import com.system.sales.dtos.groups.AppGroup;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class StockExitDTO implements Serializable {

    @JsonView({AppGroup.Response.class, AppGroup.ResponsePage.class})
    private Long id;

    @JsonView({AppGroup.Response.class, AppGroup.ResponsePage.class})
    private ItemDTO item;

    @JsonView({AppGroup.Request.class})
    @NotNull(message = "{required_message}")
    private Long itemId;

    @JsonView({AppGroup.Response.class, AppGroup.ResponsePage.class, AppGroup.Request.class})
    @NotNull(message = "{required_message}")
    private Long quantity;

    @JsonView({AppGroup.Response.class, AppGroup.ResponsePage.class})
    private LocalDateTime date_exit;

    @JsonView({AppGroup.Response.class, AppGroup.ResponsePage.class})
    private UserDTO user;

    @JsonView({AppGroup.Request.class})
    @NotNull(message = "{required_message}")
    private UUID userId;

    @JsonView({AppGroup.Response.class, AppGroup.ResponsePage.class, AppGroup.Request.class})
    private String observation;
}
