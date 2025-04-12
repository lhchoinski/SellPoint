package com.system.pos.services;

import com.system.pos.interfaces.AuthenticatedUser;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticatedUserService {
    AuthenticatedUser getCurrentUserDTO();

    UserDetails getCurrentUser();

    AuthenticatedUser getAuthenticatedUser();

    void loadAuthenticatedUser();

    AuthenticatedUser user2AuthenticatedUserDTO(UserDetails user);

}
