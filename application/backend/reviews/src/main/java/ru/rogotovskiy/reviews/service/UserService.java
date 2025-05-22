package ru.rogotovskiy.reviews.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rogotovskiy.reviews.entity.User;
import ru.rogotovskiy.reviews.repository.UserRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new NoSuchElementException("Пользователь с именем %s не найден".formatted(username))
        );
    }

    public User getById(Integer id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Пользователь с id = %d не найден".formatted(id))
        );
    }

    public Integer getUserIdByUsername(String username) {
        return getUserByUsername(username).getId();
    }
}
