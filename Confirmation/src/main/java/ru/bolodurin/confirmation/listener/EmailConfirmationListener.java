package ru.bolodurin.confirmation.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.bolodurin.confirmation.model.EmailModel;
import ru.bolodurin.confirmation.service.EmailSender;
import ru.bolodurin.services.authentication.kafka.message.EmailConfirmationMessage;
import ru.bolodurin.services.authentication.loging.KafkaAppender;

@Component
@Log4j2
@RequiredArgsConstructor
public class EmailConfirmationListener {
    private final EmailSender emailSender;

    @KafkaListener(topics = "${kafka.topics.sendConfirmationEmail}",
            containerFactory = "kafkaListenerContainerFactory")
    public void handle(@Payload ConsumerRecord<String, EmailConfirmationMessage> message) {
        EmailConfirmationMessage kafkaMessage = message.value(); //TODO: Class cast exception!

        emailSender.sendConfirmationEmail(new EmailModel(kafkaMessage.email(), kafkaMessage.token()));
    }

    @KafkaListener(topics = "Log",
            containerFactory = "kafkaListenerContainerFactory")
    public void handleLog(@Payload Object message) {
        System.out.println("Log: " + message);
    }

}
