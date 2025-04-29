package com.aura8.general_backend.service;

import com.aura8.general_backend.config.GerenciadorTokenJwt;
import com.aura8.general_backend.dtos.UsersMapper;
import com.aura8.general_backend.dtos.UsersTokenDto;
import com.aura8.general_backend.entities.Users;
import com.aura8.general_backend.exception.ElementAlreadyExists;
import com.aura8.general_backend.exception.ElementNotFoundException;
import com.aura8.general_backend.exception.UnauthorizedUserException;
import com.aura8.general_backend.repository.UsersRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService{

    @Autowired
    private UsersRepository repository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Users register(Users user, Integer roleId) {
        user.setRole(roleService.getRoleById(roleId));
        Optional<Users> existingUser = repository.findByEmailAndDeletedFalse(user.getEmail());
        if (existingUser.isPresent()) {
            throw new ElementAlreadyExists();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return repository.save(user);
    }

    public UsersTokenDto login(Users userInfo) {
        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                userInfo.getEmail(), userInfo.getPassword());

        var user = repository.findByEmailAndDeletedFalse(userInfo.getEmail());
        final Authentication authentication = this.authenticationManager.authenticate(credentials);
        Users usuarioAutenticado = user.orElseThrow(
                () -> new UnauthorizedUserException("Senha ou Email Invalidos")
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return UsersMapper.toTokenDto(usuarioAutenticado, token);
    }

    public List<Users> getAllUsers() {
        return repository.findAllByDeletedFalse();
    }

    public Users getUserById(Integer id) {
        return repository.findByIdAndDeletedFalse(id)
                .orElseThrow(
                        () -> new ElementNotFoundException("Usuario de ID: %d não foi encontrado".formatted(id)) // 404
        );
    }

    public Users updateUser(Integer roleId, Users userToUpdate) {

            Users user = getUserById(userToUpdate.getId());
            if (roleId == null) roleId = user.getRole().getId();
            userToUpdate.setRole(roleService.getRoleById(roleId));

            if (userToUpdate.getUsername() == null) userToUpdate.setUsername(user.getUsername());
            if (userToUpdate.getEmail() == null) userToUpdate.setEmail(user.getEmail());
            if (userToUpdate.getPassword() == null) userToUpdate.setPassword(user.getPassword());
            if (userToUpdate.getPhone() == null) userToUpdate.setPhone(user.getPhone());
            if (userToUpdate.getDateOfBirth() == null) userToUpdate.setDateOfBirth(user.getDateOfBirth());
            userToUpdate.setDeleted(user.getDeleted());
            userToUpdate.setCreatedAt(user.getCreatedAt());
            userToUpdate.setModifiedAt(LocalDateTime.now());
            Users updatedUser = repository.save(userToUpdate);
            return updatedUser;
    }

    public void deleteUser(Integer id) {
        Users userToModify = getUserById(id);
        userToModify.setDeleted(true);
        repository.save(userToModify);
    }

}
