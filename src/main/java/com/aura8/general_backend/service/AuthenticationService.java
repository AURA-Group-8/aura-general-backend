package com.aura8.general_backend.service;

import com.aura8.general_backend.dtos.users.UsersDetailsDto;
import com.aura8.general_backend.entities.Users;
import com.aura8.general_backend.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UsersRepository usuarioRepository;

    // Método da interface implementada
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Users> usuarioOpt = usuarioRepository.findByEmailAndDeletedFalse(username);

        if (usuarioOpt.isEmpty()) {

            throw new UsernameNotFoundException(String.format("usuario: %s nao encontrado", username));
        }

        return new UsersDetailsDto(usuarioOpt.get());
    }
}