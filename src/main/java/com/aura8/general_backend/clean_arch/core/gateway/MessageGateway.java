package com.aura8.general_backend.clean_arch.core.gateway;

import com.aura8.general_backend.clean_arch.core.domain.valueobject.Email;
import com.aura8.general_backend.clean_arch.core.domain.valueobject.Phone;

public interface MessageGateway {
    void sendMessageWhatsapp(Phone phone, String assunto, String mensagem);
    void sendMessageEmail(Email from, Email to, String subject, String text);
}
