package com.system.payment.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import com.system.payment.dtos.groups.AppGroup;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SaleItemDTO implements Serializable {

    @JsonView({AppGroup.Response.class, AppGroup.ResponsePage.class})
    private Long id;

    @JsonView({AppGroup.Response.class, AppGroup.ResponsePage.class})
    @NotNull(message = "{required_message}")
    private ItemDTO item;

    @JsonView({AppGroup.Response.class, AppGroup.ResponsePage.class, AppGroup.Request.class})
    @NotNull(message = "{required_message}")
    private Long itemId;

    @JsonView({AppGroup.Response.class, AppGroup.ResponsePage.class, AppGroup.Request.class})
    @NotNull(message = "{required_message}")
    private Long quantity;

    @JsonView({AppGroup.Response.class, AppGroup.ResponsePage.class})
    @NotNull(message = "{required_message}")
    private Double pricePartial;

    @JsonView({AppGroup.Response.class, AppGroup.ResponsePage.class})
    private SaleDTO sale;

}
