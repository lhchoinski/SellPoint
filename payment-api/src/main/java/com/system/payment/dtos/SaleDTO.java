package com.system.payment.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import com.system.payment.dtos.groups.AppGroup;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class SaleDTO implements Serializable {

    @JsonView({AppGroup.Response.class, AppGroup.ResponsePage.class})
    private Long id;

    @JsonView({AppGroup.Response.class, AppGroup.ResponsePage.class, AppGroup.Request.class})
    @NotNull(message = "{required_message}")
    private List<SaleItemDTO> items;

    @JsonView({AppGroup.Response.class, AppGroup.ResponsePage.class})
    private UserDTO user;

    @JsonView({AppGroup.Response.class, AppGroup.ResponsePage.class})
    private CustomerDTO customer;

    @JsonView({AppGroup.Request.class})
    @NotNull(message = "{required_message}")
    private Long customerId;

}
