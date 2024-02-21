package ru.bolodurin.confirmation.service;

import org.springframework.transaction.annotation.Transactional;
import ru.bolodurin.confirmation.model.EmailModel;

public interface EmailSender {
    @Transactional
    void sendConfirmationEmail(EmailModel model);

}
