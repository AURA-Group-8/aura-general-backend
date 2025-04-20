package com.aura8.general_backend.dtos.message;

import jakarta.validation.constraints.NotBlank;

public class MessageRequestDto {
    @NotBlank
    private String assunto;
    @NotBlank
    private String mensagem;

    public @NotBlank String getAssunto() {
        return assunto;
    }

    public void setAssunto(@NotBlank String assunto) {
        this.assunto = assunto;
    }

    public @NotBlank String getMensagem() {
        return mensagem;
    }

    public void setMensagem(@NotBlank String mensagem) {
        this.mensagem = mensagem;
    }
}
