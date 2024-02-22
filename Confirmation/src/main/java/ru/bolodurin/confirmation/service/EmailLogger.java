package ru.bolodurin.confirmation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.bolodurin.confirmation.model.EmailModel;

@Service
@Log4j2
@RequiredArgsConstructor
public class EmailLogger implements EmailSender {
    @Override
    public void sendConfirmationEmail(EmailModel model) {
        log.warn("Sending token " + model.body() + " to " + model.email());
    }

}
