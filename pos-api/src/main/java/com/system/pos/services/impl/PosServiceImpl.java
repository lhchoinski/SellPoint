package com.system.pos.services.impl;

import com.system.pos.dtos.SaleDTO;
import com.system.pos.dtos.UserDTO;
import com.system.pos.entities.Customer;
import com.system.pos.entities.User;
import com.system.pos.exeptions.NotFoundException;
import com.system.pos.interfaces.AuthenticatedUser;
import com.system.pos.repositories.CustomerRepository;
import com.system.pos.services.PosService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PosServiceImpl implements PosService {

    private final CustomerRepository customerRepository;
    private final RabbitTemplate rabbitTemplate;
    private final SessionService sessionService;

    public String initSale(SaleDTO saleDTO) {
        try {

            saleDTO.setUser(sessionService.getAuthenticatedUserDTO());

            rabbitTemplate.convertSendAndReceive("sales.queue", saleDTO);
            return "Sale initiated Successfully";

        } catch (Exception e) {
            return "Error starting sale : " + e.getMessage();
        }
    }

    private Customer getCustomer(Long id) throws NotFoundException {
        return customerRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(()
                -> new NotFoundException("Customer not found"));
    }
}
