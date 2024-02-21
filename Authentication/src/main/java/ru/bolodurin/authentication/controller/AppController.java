package ru.bolodurin.authentication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bolodurin.authentication.model.dto.AppMessage;

@RestController
@RequestMapping("/app")
public class AppController {
    @GetMapping
    public @ResponseBody ResponseEntity<AppMessage> test() {
        return ResponseEntity.ok(new AppMessage("Success"));
    }
}
