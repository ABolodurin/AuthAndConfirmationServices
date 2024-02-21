package ru.bolodurin.authentication.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bolodurin.authentication.model.dto.AppMessage;
import ru.bolodurin.authentication.model.dto.LoginRequest;
import ru.bolodurin.authentication.model.dto.RegisterRequest;
import ru.bolodurin.authentication.model.dto.TokenRequest;
import ru.bolodurin.authentication.model.dto.TokenResponse;
import ru.bolodurin.authentication.model.entity.ConfirmationToken;
import ru.bolodurin.authentication.model.entity.User;
import ru.bolodurin.authentication.security.JwtService;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final Validator validator;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final ConfirmationTokenService confirmationTokenService;

    @Transactional
    public AppMessage register(RegisterRequest request) {
        if (userService.isExist(request.username())) throw new RuntimeException(
                "user " + request.username() + " is already exist");

        User user = User
                .builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(User.Role.USER)
                .build();

        userService.add(user);

        user = userService.findByUsername(user.getUsername());
        confirmationTokenService.createNewTokenForUser(user);

        return new AppMessage("Check email to confirm registration!");
    }

    public TokenResponse auth(LoginRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(), request.password()));

        return getResponseFor(userService.findByUsername(request.username()));
    }

    private TokenResponse getResponseFor(User user) {
        String token = jwtService.generateToken(user);

        return new TokenResponse(token);
    }

    @Transactional
    public TokenResponse confirmRegister(TokenRequest tokenRequest) {
        Set<ConstraintViolation<TokenRequest>> violations = validator.validate(tokenRequest);
        if (!violations.isEmpty()) throw new ConstraintViolationException(violations);

        ConfirmationToken token = confirmationTokenService.getToken(tokenRequest.token());

        if (token.isExpired()) throw new RuntimeException("Token is expired: " + token);

        User user = token.getUser();
        userService.enable(user);

        return getResponseFor(user);
    }
}
