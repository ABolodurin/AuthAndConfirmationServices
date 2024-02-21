package ru.bolodurin.confirmation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bolodurin.confirmation.model.EmailModel;

@Service
@RequiredArgsConstructor
public class ConsoleLogger implements EmailSender {
    @Override
    @Transactional
    public void sendConfirmationEmail(EmailModel model) {
        System.out.println(model.email());
    }

}
