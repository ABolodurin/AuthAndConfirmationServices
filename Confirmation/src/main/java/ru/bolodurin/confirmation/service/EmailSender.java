package ru.bolodurin.confirmation.service;

import ru.bolodurin.confirmation.model.EmailModel;

public interface EmailSender {
    void sendConfirmationEmail(EmailModel model);

}
