package com.aura8.general_backend.clean_arch.application.usecase.message.resetpassword;

public record ChangePasswordInfos(
        String token,
        Integer userId
) {
}
