package ru.bolodurin.authentication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.bolodurin.authentication.model.entity.ConfirmationToken;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class KafkaSender {
    private final ConfirmationTokenService service;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${application.kafka.topics.sendConfirmationEmail}")
    private String kafkaTopic;

    @Scheduled(fixedRate = 10L, initialDelay = 20L, timeUnit = TimeUnit.SECONDS)
    public void processConfirmation() {
        List<ConfirmationToken> tokens = service.getUnsent();

        if (!tokens.isEmpty()) tokens.forEach(t -> {
            kafkaTemplate.send(kafkaTopic, "mailto: " + t.getEmail() + " /register?t=" + t.getToken());
            service.changeStatusToSent(t);
        });
    }

    @Scheduled(
            fixedRateString = "${application.email-confirmation-token.expiration-minutes}",
            initialDelayString = "${application.email-confirmation-token.expiration-minutes}",
            timeUnit = TimeUnit.MINUTES)
    public void clearExpired() {
        service.deleteExpired();
    }

}
