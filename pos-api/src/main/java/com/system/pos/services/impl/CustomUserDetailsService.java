package com.system.pos.services.impl;

import com.system.pos.dtos.UserDTO;
import com.system.pos.entities.User;
import com.system.pos.exeptions.NotFoundException;
import com.system.pos.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User usuario = userRepository.findByUserName(login)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        return org.springframework.security.core.userdetails.User
                .withUsername(usuario.getUsername())
                .password(usuario.getPassword())
                .roles(usuario.getRole().name())
                .build();
    }

    public UserDTO loadUsuarioDTOByUsername(String username) {
        Optional<User> user = userRepository.findByUserName(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(user.get().getUsername());
        userDTO.setPassword(user.get().getPassword());
        userDTO.setRole(user.get().getRole().name());
        userDTO.setEmail(user.get().getEmail());
        userDTO.setName(user.get().getName());
        userDTO.setActive(user.get().getActive());

        return userDTO;
    }
}
