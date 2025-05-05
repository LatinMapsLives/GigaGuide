package ru.rogotovskiy.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.rogotovskiy.auth.dto.RegistrationUserDto;
import ru.rogotovskiy.auth.entity.User;
import ru.rogotovskiy.auth.repositoy.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void createUser(RegistrationUserDto registrationUserDto) {
        userRepository.save(new User(
                null,
                registrationUserDto.username(),
                registrationUserDto.email(),
                passwordEncoder.encode(registrationUserDto.password()),
                List.of(roleService.findByName("ROLE_USER"))
        ));
    }
}
