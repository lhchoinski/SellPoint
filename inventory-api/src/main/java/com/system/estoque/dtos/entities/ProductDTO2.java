package com.system.estoque.dtos.entities;

import com.fasterxml.jackson.annotation.JsonView;
import com.system.estoque.dtos.groups.AppGroup;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class ProductDTO2 implements Serializable {

    @JsonView({AppGroup.Response.class, AppGroup.ResponsePage.class})
    private UUID id;

    @JsonView({AppGroup.Response.class, AppGroup.ResponsePage.class, AppGroup.Request.class})
    @NotNull(message = "{required_message}")
    private String name;

    @JsonView({AppGroup.Response.class, AppGroup.ResponsePage.class, AppGroup.Request.class})
    private String description;

    @JsonView({AppGroup.Response.class, AppGroup.ResponsePage.class, AppGroup.Request.class})
    @NotNull(message = "{required_message}")
    private String price;

    @JsonView({AppGroup.Response.class, AppGroup.ResponsePage.class})
    private String quantity;

    @JsonView({AppGroup.Response.class, AppGroup.ResponsePage.class})
    private Boolean active;
}
