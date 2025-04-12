package com.system.pos.services.impl;

import com.system.pos.components.providers.JwtUtil;
import com.system.pos.dtos.UserDTO;
import com.system.pos.dtos.auth.JwtDTO;
import com.system.pos.dtos.request.LoginDTO;
import com.system.pos.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtDTO authenticate(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return processJwt(authentication);
    }

    private JwtDTO processJwt(Authentication user) {

        UserDTO authenticatedUser = customUserDetailsService.loadUsuarioDTOByUsername(user.getName());

        String username = user.getName();

        String accessToken = jwtUtil.generateAccessToken(username);
        String refreshToken = jwtUtil.generateRefreshToken(username);

        JwtDTO jwtDTO = new JwtDTO();
        jwtDTO.setAccessToken(accessToken);
        jwtDTO.setRefreshToken(refreshToken);
        jwtDTO.setUser(authenticatedUser);

        return jwtDTO;
    }

    @Override
    public JwtDTO refreshToken() {
        String jwt = getToken();

        jwtUtil.validateToken(jwt);

        UserDetails user = getUserByToken(jwt);
        return getJwtDTO(user);
    }

    private JwtDTO getJwtDTO(UserDetails user) {
        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(user.getUsername(),
                null, user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(newAuthentication);

        return processJwt(newAuthentication);
    }

    private String getToken() {
        String token = jwtUtil.getAuthorization();

        if (token != null) {
            if (token.startsWith("Bearer ")) {
                return token.replace("Bearer ", "");
            }

            return token;
        }

        return null;
    }

    private UserDetails getUserByToken(String token) {
        String username = jwtUtil.extractUsername(token);

        return customUserDetailsService.loadUserByUsername(username);
    }
}
