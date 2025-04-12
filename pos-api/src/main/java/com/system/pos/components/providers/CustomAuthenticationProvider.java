package com.system.pos.components.providers;

import org.springframework.security.core.Authentication;

public interface CustomAuthenticationProvider {
    Authentication authenticate(Authentication authentication);
}
