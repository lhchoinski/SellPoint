package com.system.pos.services;


import com.system.pos.dtos.auth.JwtDTO;
import com.system.pos.dtos.request.LoginDTO;

public interface AuthenticationService  {
    JwtDTO authenticate(LoginDTO loginDTO);

    JwtDTO refreshToken();
}
