package com.autoever.backend.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // simple in-memory token store
    private final Map<String, Long> tokenStore = new ConcurrentHashMap<>();

    public User signup(User user) {
        if (userRepository.existsByAccount(user.getAccount())) {
            throw new IllegalArgumentException("account exists");
        }
        if (userRepository.existsByRegNo(user.getRegNo())) {
            throw new IllegalArgumentException("regNo exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public String login(String account, String password) {
        User user = userRepository.findByAccount(account)
                .orElseThrow(() -> new IllegalArgumentException("invalid credentials"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("invalid credentials");
        }
        String token = UUID.randomUUID().toString();
        tokenStore.put(token, user.getId());
        return token;
    }

    public Optional<User> findByToken(String token) {
        Long id = tokenStore.get(token);
        if (id == null) return Optional.empty();
        return userRepository.findById(id);
    }

    public User updateUser(Long id, String password, String address) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("user not found"));
        if (password != null) {
            user.setPassword(passwordEncoder.encode(password));
        }
        if (address != null) {
            user.setAddress(address);
        }
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> findAll(int page, int size) {
        return userRepository.findAll(org.springframework.data.domain.PageRequest.of(page, size)).getContent();
    }
}
