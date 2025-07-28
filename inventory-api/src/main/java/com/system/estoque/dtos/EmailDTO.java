package com.system.estoque.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Setter
@Getter
public class EmailDTO implements Serializable {

    private UUID userId;

    private String emailTo;

    private String subject;

    private String text;

}