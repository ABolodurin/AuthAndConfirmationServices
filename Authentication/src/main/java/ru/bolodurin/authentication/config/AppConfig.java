package ru.bolodurin.authentication.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.bolodurin.authentication.service.UserService;

@Configuration
@ComponentScan
@EnableScheduling
@RequiredArgsConstructor
public class AppConfig {
    private final UserService userService;

    @Bean
    public UserDetailsService userDetailsService() {
        return userService::findByUsername;
    }

}
