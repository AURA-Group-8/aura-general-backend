package com.aura8.general_backend.clean_arch.core.gateway;

import com.aura8.general_backend.clean_arch.core.domain.UsersToken;

public interface SecurityGateway {
    UsersToken getTokenFromLogin(String email, String password);
}
