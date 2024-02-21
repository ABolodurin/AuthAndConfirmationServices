package ru.bolodurin.authentication.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bolodurin.authentication.model.dto.AppMessage;
import ru.bolodurin.authentication.model.dto.LoginRequest;
import ru.bolodurin.authentication.model.dto.RegisterRequest;
import ru.bolodurin.authentication.model.dto.TokenRequest;
import ru.bolodurin.authentication.model.dto.TokenResponse;
import ru.bolodurin.authentication.service.AuthenticationService;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authService;

    @PostMapping("/register")
    public @ResponseBody ResponseEntity<AppMessage> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(201).body(authService.register(request));
    }

    @PostMapping("/login")
    public @ResponseBody ResponseEntity<TokenResponse> auth(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.auth(request));
    }

    @GetMapping("/register")
    public @ResponseBody ResponseEntity<TokenResponse> confirmRegistration(@RequestParam(name = "t") String token) {
        return ResponseEntity.ok(authService.confirmRegister(new TokenRequest(token)));
    }

}
