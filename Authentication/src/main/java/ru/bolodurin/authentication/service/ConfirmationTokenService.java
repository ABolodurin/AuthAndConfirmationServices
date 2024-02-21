package ru.bolodurin.authentication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bolodurin.authentication.model.entity.ConfirmationToken;
import ru.bolodurin.authentication.model.entity.User;
import ru.bolodurin.authentication.repository.ConfirmationTokenRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository repository;

    @Value("${application.email-confirmation-token.expiration-minutes}")
    private int tokenExpirationMinutes;

    @Transactional
    public void createNewTokenForUser(User user) {
        LocalDateTime now = LocalDateTime.now();

        String token = UUID.nameUUIDFromBytes(
                        (user.getEmail() + now).getBytes())
                .toString();

        ConfirmationToken confirmationToken = ConfirmationToken.builder()
                .token(token)
                .email(user.getEmail())
                .user(user)
                .createdAt(now)
                .expiresAt(now.plusMinutes(tokenExpirationMinutes))
                .build();

        repository.save(confirmationToken);
    }

    public void changeStatusToSent(ConfirmationToken token) {
        token.setSent(true);
        repository.save(token);
    }

    public List<ConfirmationToken> getUnsent() {
        return repository.findBySent(false);
    }

    public void deleteExpired() {
        repository.deleteAllByExpiresAtBefore(LocalDateTime.now());
    }

    public ConfirmationToken getToken(String token) {
        return repository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Confirmation Token expired or not exists: " + token));
    }

}
