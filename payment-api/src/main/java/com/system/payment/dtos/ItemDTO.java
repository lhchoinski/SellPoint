package com.system.payment.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import com.system.payment.dtos.groups.AppGroup;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ItemDTO implements Serializable {

    @JsonView({AppGroup.Response.class, AppGroup.ResponsePage.class})
    private Long id;

    @JsonView({AppGroup.Response.class, AppGroup.ResponsePage.class, AppGroup.Request.class})
    @NotNull(message = "{required_message}")
    private String name;

    @JsonView({AppGroup.Response.class, AppGroup.ResponsePage.class, AppGroup.Request.class})
    private String description;

    @JsonView({AppGroup.Response.class, AppGroup.ResponsePage.class, AppGroup.Request.class})
    @NotNull(message = "{required_message}")
    private Double price;

    @JsonView({AppGroup.Response.class, AppGroup.ResponsePage.class})
    private Long quantity;

    @JsonView({AppGroup.Response.class, AppGroup.ResponsePage.class})
    private Boolean active;
}
