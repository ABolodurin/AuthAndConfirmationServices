package ru.bolodurin.confirmation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.bolodurin.services.authentication.loging.KafkaAppender;

@Configuration
@ComponentScan
public class AppConfig {
}
