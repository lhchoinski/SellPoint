package com.services.email.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class EmailDto implements Serializable {

    private UUID userId;
    private String emailTo;
    private String subject;
    private String text;
}
