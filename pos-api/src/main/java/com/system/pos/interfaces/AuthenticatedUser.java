package com.system.pos.interfaces;

import java.util.UUID;

public interface AuthenticatedUser {
    UUID getId();
    void setId(UUID id);

    String getName();
    void setName(String name);

    String getUsername();
    void setUsername(String username);

    String getEmail();
    void setEmail(String email);

}
