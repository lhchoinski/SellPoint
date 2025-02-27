package com.system.estoque.services.impl;

import com.system.estoque.dtos.entities.UserDTO;
import com.system.estoque.entities.User;
import com.system.estoque.exeptions.BadRequestException;
import com.system.estoque.mappers.UserMapper;
import com.system.estoque.producers.UserProducer;
import com.system.estoque.repositories.UserRepository;
import com.system.estoque.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserProducer userProducer;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDTO create(UserDTO userDTO) {

        if (userDTO.getName() != null) {
            userDTO.setName(userDTO.getName().toUpperCase());
        }

        userDTO.setActive(true);

        User user = userMapper.toEntity(userDTO);
        userRepository.save(user);

        userProducer.publishMessageEmail(user);

        return userMapper.toDto(user);
    }

    @Override
    public UserDTO findUserById(UUID id) {
        User user = getUser(id);
        return userMapper.toDto(user);
    }

    private User getUser(UUID id) throws BadRequestException {
        return userRepository.findById(id).orElseThrow(()
                -> new BadRequestException("User not found"));
    }

}
