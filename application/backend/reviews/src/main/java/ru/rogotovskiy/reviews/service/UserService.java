package ru.rogotovskiy.reviews.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rogotovskiy.reviews.entity.User;
import ru.rogotovskiy.reviews.repository.UserRepository;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Пользователь не существует")
        );
    }

    public String getUsername(Integer id) {
        return getUserById(id).getUsername();
    }
}
