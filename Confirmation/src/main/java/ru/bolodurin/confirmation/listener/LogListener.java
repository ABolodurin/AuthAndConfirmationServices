package ru.bolodurin.confirmation.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class LogListener {
    @KafkaListener(topics = "${kafka.topics.log}",
            containerFactory = "kafkaContainerFactory")
    public void handleLog(@Payload String message) {
        System.out.println("Log: " + message);
    }

}
