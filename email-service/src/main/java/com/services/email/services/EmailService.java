package com.services.email.services;

import com.services.email.entities.Email;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    void sendEmail(Email email);

}
