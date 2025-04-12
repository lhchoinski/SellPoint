package com.system.pos.services.impl;

import com.system.pos.dtos.auth.AuthenticatedUserDTO;
import com.system.pos.entities.User;
import com.system.pos.interfaces.AuthenticatedUser;
import com.system.pos.repositories.UserRepository;
import com.system.pos.services.AuthenticatedUserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticatedUserServiceImpl implements AuthenticatedUserService {

    protected final UserRepository userRepository;

    protected final MessageSource messageSource;

    @Getter
    protected AuthenticatedUser currentUserDTO = null;

    @Getter
    protected UserDetails currentUser = null;

    @Override
    @Transactional(readOnly = true)
    public AuthenticatedUser getAuthenticatedUser() {
        loadAuthenticatedUser();

        if (currentUser == null) {
            throw new BadCredentialsException(messageSource.getMessage("evoluitec.invalidToken", null, LocaleContextHolder.getLocale()));
        }

        return currentUserDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public void loadAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BadCredentialsException(messageSource.getMessage("evoluitec.userNotAuthenticated", null, LocaleContextHolder.getLocale()));
        }

        String username = authentication.getName();

        Optional<User> optionalUser = userRepository.findByUserName(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            currentUser = user;

            currentUserDTO = new AuthenticatedUserDTO();
            currentUserDTO.setUsername(user.getUsername());
            currentUserDTO.setEmail(user.getEmail());
            currentUserDTO.setName(user.getName());
            currentUserDTO.setId(user.getId());
        } else {
            throw new BadCredentialsException(messageSource.getMessage("evoluitec.userNotFound", null, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    @Transactional
    public AuthenticatedUser user2AuthenticatedUserDTO(UserDetails user) {

        AuthenticatedUser authenticatedUserDTO = new AuthenticatedUserDTO();
        authenticatedUserDTO.setUsername(user.getUsername());

        return authenticatedUserDTO;
    }
}
