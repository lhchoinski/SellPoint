package com.services.email.consumers;

import com.services.email.dtos.EmailDto;
import com.services.email.entities.Email;
import com.services.email.services.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    final EmailService emailService;

    public EmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${broker.queue.email.name}")
    public void listenEmailQueue(@Payload EmailDto emailDto) {
        var email = new Email();
        BeanUtils.copyProperties(emailDto, email);
        emailService.sendEmail(email);
    }

}