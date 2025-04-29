package com.aura8.general_backend.dtos;

import com.aura8.general_backend.entities.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UsersDetailsDto implements UserDetails{
    private final String username;
    private final String email;
    private final String password;

    public UsersDetailsDto(Users users) {
        this.username = users.getUsername();
        this.email = users.getEmail();
        this.password = users.getPassword();
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
        return null;
    }
}
