package ru.bolodurin.authentication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bolodurin.authentication.model.entity.User;
import ru.bolodurin.authentication.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository
                .findById(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public boolean isExist(String username) {
        return userRepository.existsById(username);
    }

    @Transactional
    public void add(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void enable(User user) {
        userRepository.enable(user);
    }

}
