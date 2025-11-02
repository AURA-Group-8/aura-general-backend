package com.aura8.general_backend.clean_arch.application.usecase.users.login;

public record LoginUsersCommand(
    String email,
    String password
) {
}
