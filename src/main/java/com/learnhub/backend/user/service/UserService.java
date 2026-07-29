package com.learnhub.backend.user.service;

import com.learnhub.backend.user.entity.User;
import com.learnhub.backend.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * UserService — Business logic for User Profile Management.
 *
 * Implemented in pure Java with explicit constructor dependency injection (no Lombok).
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    // Explicit constructor for dependency injection (no Lombok @RequiredArgsConstructor)
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
