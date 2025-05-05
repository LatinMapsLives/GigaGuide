package ru.rogotovskiy.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.rogotovskiy.userservice.dto.UpdateUserDto;
import ru.rogotovskiy.userservice.dto.UserDto;
import ru.rogotovskiy.userservice.entity.User;
import ru.rogotovskiy.userservice.mapper.UserMapper;
import ru.rogotovskiy.userservice.repository.UserRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDto getByUsername(String username) {
        return userMapper.toDto(getUserByUsername(username));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new NoSuchElementException("Пользователь с именем %s не найден".formatted(username))
        );
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Пользователь с id %d не найден".formatted(id))
        );
    }

    public void updateUser(Integer id, UpdateUserDto dto) {
        User user = getUserById(id);

        if (dto.username() != null && !dto.username().isEmpty()) {
            user.setUsername(dto.username());
        }

        if (dto.email() != null && !dto.email().isEmpty()) {
            if (userRepository.existsByEmail(dto.email())) {
                throw new IllegalArgumentException("Пользователь с таким адресом электронной почты уже существует");
            }
            user.setEmail(dto.email());
        }

        if (dto.oldPassword() != null && dto.newPassword() != null && !dto.newPassword().isEmpty()) {

            if (!bCryptPasswordEncoder.encode(dto.oldPassword()).equals(user.getPassword())) {
                throw new IllegalArgumentException("Пароль неверный");
            }

            user.setPassword(bCryptPasswordEncoder.encode(dto.newPassword()));
        }
        userRepository.save(user);

    }

    public Integer getUserIdByUsername(String username) {
        return getUserByUsername(username).getId();
    }

    public UserDto getById(Integer userId) {
        return userMapper.toDto(getUserById(userId));
    }
}
