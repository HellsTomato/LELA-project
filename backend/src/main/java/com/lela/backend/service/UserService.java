package com.lela.backend.service;

import com.lela.backend.entity.User;
import com.lela.backend.repository.ProgressRepository;
import com.lela.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ProgressRepository progressRepository;

    public UserService(UserRepository userRepository, ProgressRepository progressRepository) {
        this.userRepository = userRepository;
        this.progressRepository = progressRepository;
    }

    @Transactional
    public void resetUserProgress(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + userId));

        progressRepository.deleteByUserId(userId);

        user.setPoints(0);

        user.getUnlockedRewards().clear();

        userRepository.save(user);
    }
}
