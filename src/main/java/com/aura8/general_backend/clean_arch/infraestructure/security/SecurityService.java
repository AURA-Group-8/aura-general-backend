package com.aura8.general_backend.clean_arch.infraestructure.security;

import com.aura8.general_backend.clean_arch.core.domain.UsersToken;
import com.aura8.general_backend.clean_arch.core.gateway.SecurityGateway;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService implements SecurityGateway {

    private final AuthenticationManager authenticationManager;
    private final GerenciadorTokenJwt gerenciadorTokenJwt;

    public SecurityService(AuthenticationManager authenticationManager, GerenciadorTokenJwt gerenciadorTokenJwt) {
        this.authenticationManager = authenticationManager;
        this.gerenciadorTokenJwt = gerenciadorTokenJwt;
    }

    @Override
    public UsersToken getTokenFromLogin(String email, String password) {
        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                email, password);

        final Authentication authentication = this.authenticationManager.authenticate(credentials);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return new UsersToken(token);
    }
}
