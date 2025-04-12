package com.system.pos.components.filters;

import com.system.pos.components.providers.JwtUtil;
import com.system.pos.utils.PublicRoutes;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtRequestFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException {

        // Se a rota for pública, ignora a verificação do token JWT
        if (PublicRoutes.isPublic(request)) {
            chain.doFilter(request, response);  // Passa a requisição para o próximo filtro (sem bloquear)
            return;
        }

        final String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        // Verifica se o cabeçalho de autorização está presente
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);  // Remove o prefixo "Bearer "
            try {
                username = jwtUtil.extractUsername(jwt);  // Extrai o nome de usuário do token
            } catch (Exception e) {
                // Se houver erro ao extrair o nome de usuário, responde com 401
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Unauthorized: Invalid or expired token.");
                return;  // Impede o processamento da requisição
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Token não presente
            response.getWriter().write("Unauthorized: No token provided.");
            return;
        }

        // Verifica se o token é válido
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtUtil.validateToken(jwt)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        username, null, null);
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // Token inválido
                response.getWriter().write("Unauthorized: Invalid token.");
                return;
            }
        }

        chain.doFilter(request, response); // Continua o filtro caso o token seja válido
    }
}
