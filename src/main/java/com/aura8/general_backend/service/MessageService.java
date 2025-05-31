package com.aura8.general_backend.service;

import com.aura8.general_backend.config.MailConfig;
import com.aura8.general_backend.entities.Users;
import com.aura8.general_backend.event.SchedulingCreateEvent;
import com.aura8.general_backend.exception.ElementNotFoundException;
import com.aura8.general_backend.exception.EmailFailedException;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    private final UsersService usersService;
    private final MailConfig mailConfig;

    public MessageService(UsersService usersService, MailConfig mailConfig) {
        this.usersService = usersService;
        this.mailConfig = mailConfig;
    }

    public void sendToAllUsersWhatsapp(String assunto, String mensagem){
        List<Users> usersList = usersService.getAllUsers();
        if(usersList.isEmpty()) return;
        usersList.forEach(users -> {
            TwilioService.sendWhatsappMessage(users.getPhone(), assunto, mensagem);
        });
    }

    @EventListener
    public void onSchedulingCreated(SchedulingCreateEvent event) {
        LocalDateTime localDateTime = event.getScheduling().getStartDatetime();
        String minutoString;
        if (localDateTime.getMinute() < 10) {
            minutoString = "0" + localDateTime.getMinute();
        } else {
            minutoString = "%d".formatted(localDateTime.getMinute());
        }
        String mensagem = "👋 Olá!\n" +
                "\n" +
                "        Esperamos que esta mensagem o(a) encontre bem. 😊 \n" +
                "        É com grande satisfação que informamos que um **novo atendimento foi " +
                "        agendado com sucesso** em nosso sistema. 🎉✅\n" +
                "\n" +
                "        📌 Abaixo seguem todos os detalhes do agendamento para sua referência:\n" +
                "\n" +
                "        📆**Data do Atendimento:** *%d/%d*  \n" +
                "        🕛**Horário:** *%d:%s*  \n" +
                "        📍**Local:** conforme combinado anteriormente ou conforme informações registradas em sua conta.\n" +
                "\n" +
                "        ✨ Este atendimento é exclusivo e foi cuidadosamente reservado para você.  \n" +
                "        Pedimos que, se possível, esteja disponível com alguns minutos de antecedência" +
                "        ⏳ para garantir o melhor aproveitamento do seu tempo e do nosso serviço.\n" +
                "\n" +
                "        ❌Caso haja qualquer imprevisto, solicitamos que nos avise com a maior antecedência possível." +
                "        Assim, poderemos reagendar ou ajustar conforme sua necessidade, " +
                "        evitando transtornos e liberando o horário para outras pessoas. \uD83D\uDDD3\uD83D\uDD04\n" +
                "\n" +
                "        ✅Nosso compromisso é com a qualidade, o respeito ao seu tempo e a excelência no atendimento.  \n" +
                "        Por isso, estamos sempre disponíveis para esclarecer dúvidas, dar suporte e " +
                "        garantir que sua experiência conosco seja positiva do início ao fim. \uD83D\uDCAC\uD83D\uDE4C\n" +
                "\n" +
                "        📞Em caso de necessidade, entre em contato com nossa equipe pelos canais " +
                "        de atendimento já conhecidos. Estaremos prontos para ajudar da melhor forma possível!\n" +
                "\n" +
                "        💼Atenciosamente,  \n   " +
                "        Equipe de Atendimento🌟".formatted(
                localDateTime.getDayOfMonth(),
                localDateTime.getMonthValue(),
                localDateTime.getHour(),
                minutoString
        );
        if (event.getAdminScheduling())
            TwilioService.sendWhatsappMessage(event.getUser().getPhone(), "Novo Atendimento", mensagem);
    }

    public String sendToken(String to) {
        if(!usersService.existsByEmail(to)){
            throw new ElementNotFoundException("Usuario de email: %s não foi encontrado".formatted(to));
        }

        Integer token = (int) Math.ceil(Math.random() * 89999) + 10000;
        String responseToken = token.toString();
        try {
            JavaMailSender emailSender = mailConfig.getJavaMailSender();
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("aura.noreply@gmail.com");
            message.setTo(to);
            message.setSubject("AURA - Mudar senha");
            message.setText("O codigo para mudar sua senha é: %s".formatted(token));
            emailSender.send(message);
        } catch (Exception e){
            e.printStackTrace();
            throw new EmailFailedException("Erro ao enviar e-mail, tente verificar as credênciais");
        }
        return responseToken;
    }

    public void sendToAuraEmail(String mensagem) {
        try {
            JavaMailSender emailSender = mailConfig.getJavaMailSender();
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("");
            message.setTo("");
            message.setSubject("Mensagem para Aura");
            message.setText(mensagem);
            emailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new EmailFailedException("Erro ao enviar e-mail, tente verificar as credênciais");
        }
    }
}
