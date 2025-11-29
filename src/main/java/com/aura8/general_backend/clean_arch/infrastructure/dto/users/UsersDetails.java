package com.aura8.general_backend.clean_arch.infrastructure.dto.users;

import com.aura8.general_backend.clean_arch.core.domain.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UsersDetails implements UserDetails{
    private final String username;
    private final String email;
    private final String password;
    private final List<String> authorities;

    public UsersDetails(Users users) {
        this.username = users.getUsername().get();
        this.email = users.getEmail().get();
        this.password = users.getPassword().get();
        this.authorities = List.of(users.getRole().getName());
    }

    public String getUsername() {
        return email;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities.stream().map(role -> (GrantedAuthority) () -> role).toList();
    }
}
