package ru.bolodurin.confirmation.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.bolodurin.confirmation.model.EmailModel;
import ru.bolodurin.confirmation.service.EmailSender;
import ru.bolodurin.services.authentication.kafka.message.EmailConfirmationMessage;

@Component
@RequiredArgsConstructor
public class EmailConfirmationListener {
    private final EmailSender emailSender;

    @KafkaListener(topics = "${kafka.topics.sendConfirmationEmail}",
            containerFactory = "kafkaContainerFactory")
    public void handle(@Payload EmailConfirmationMessage message) {
        emailSender.sendConfirmationEmail(new EmailModel(message.email(), message.token()));
    }

}
