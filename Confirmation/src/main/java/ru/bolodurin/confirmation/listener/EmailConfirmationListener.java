package ru.bolodurin.confirmation.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.bolodurin.confirmation.model.EmailModel;
import ru.bolodurin.confirmation.service.EmailSender;

@Component
@RequiredArgsConstructor
public class EmailConfirmationListener {
    private final EmailSender emailSender;

    @KafkaListener(topics = "${kafka.topics.sendConfirmationEmail}",
            containerFactory = "kafkaListenerContainerFactory")
    public void handle(@Payload Object message) {
        emailSender.sendConfirmationEmail(new EmailModel(message.toString()));
    }

}
