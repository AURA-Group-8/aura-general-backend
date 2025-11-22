package com.aura8.general_backend.clean_arch.infrastructure.integration.rabbitmq;

import com.aura8.general_backend.clean_arch.core.domain.attribute.Email;
import com.aura8.general_backend.clean_arch.core.domain.attribute.Phone;
import com.aura8.general_backend.clean_arch.core.gateway.MessageGateway;
import com.aura8.general_backend.clean_arch.infrastructure.event.ScheduleCreateEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageSender implements MessageGateway {

    private final ObjectMapper objectMapper;
    private final AmqpTemplate amqpTemplate;

    public MessageSender(ObjectMapper objectMapper, AmqpTemplate amqpTemplate) {
        this.objectMapper = objectMapper;
        this.amqpTemplate = amqpTemplate;
    }

    private static final String QUEUE_NAME = "SendMessageQueue";
    private static final String EMAIL_QUEUE_NAME = "SendEmailQueue";

    @Override
    public void sendMessageWhatsapp(Phone phone, String assunto, String mensagem) {
        WhatsappMessage payload = new WhatsappMessage(
                phone.get(),
                assunto,
                mensagem
        );
        sendJson(payload, QUEUE_NAME);
    }

    @Override
    public void sendMessageEmail(Email from, Email to, String subject, String text) {
        EmailMessage payload = new EmailMessage(
                from.get(),
                to.get(),
                subject,
                text
        );
        sendJson(payload, EMAIL_QUEUE_NAME);
    }

    private void sendJson(Object payload, String queueName) {
        try {
            byte[] json = objectMapper.writeValueAsBytes(payload);
            MessageProperties props = new MessageProperties();
            props.setContentType(MessageProperties.CONTENT_TYPE_JSON);
            Message amqpMessage = new Message(json, props);
            amqpTemplate.send(queueName, amqpMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize payload to JSON", e);
        }
    }

    @EventListener
    public void onSchedulingCreated(ScheduleCreateEvent event) {
        LocalDateTime localDateTime = event.getSchedule().getStartDatetime();
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
        sendMessageWhatsapp(event.getUser().getPhone(), "Novo Atendimento", mensagem);
    }
}
