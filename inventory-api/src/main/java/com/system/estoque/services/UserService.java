package com.system.estoque.services;

import com.system.estoque.dtos.entities.UserDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface UserService {
    UserDTO create(UserDTO userDTO);

    UserDTO findUserById(UUID id);
}
