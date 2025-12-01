package com.aura8.general_backend.clean_arch.infrastructure.security;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class LoginService {
    private final StringRedisTemplate redis;

    public LoginService(StringRedisTemplate redis) {
        this.redis = redis;
    }

    public String tentarLogin(String usuario, String senhaDigitada) {
        String key = "tentativas" + usuario;

        String tentativas = redis.opsForValue().get(key);
        int qtdTentativas = tentativas != null ? Integer.parseInt(tentativas) : 0;

        if (qtdTentativas >= 5) {
            return "Usuário Bloqueado tente novamente mais tarde!";
        }

        String senhaCorreta = "123";

        if (!senhaDigitada.equals(senhaCorreta)) {
            redis.opsForValue().set(
                    key,
                    String.valueOf(qtdTentativas + 1),
                    60,
                    TimeUnit.SECONDS
            );
            return "Senha incorreta!";
        }

        redis.delete(key);
        return "Login realizado com sucesso!";
    }

}
