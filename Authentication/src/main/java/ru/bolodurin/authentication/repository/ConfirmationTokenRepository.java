package ru.bolodurin.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bolodurin.authentication.model.entity.ConfirmationToken;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByToken(String token);

    List<ConfirmationToken> findBySent(boolean status);

    void deleteAllByExpiresAtBefore(LocalDateTime time);

}
