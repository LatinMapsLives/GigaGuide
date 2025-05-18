package ru.rogotovskiy.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.rogotovskiy.auth.dto.RegistrationUserDTO;
import ru.rogotovskiy.auth.entity.User;
import ru.rogotovskiy.auth.repository.UserRepository;

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

    public void createUser(RegistrationUserDTO registrationUserDTO) {
        userRepository.save(new User(
                null,
                registrationUserDTO.username(),
                registrationUserDTO.email(),
                passwordEncoder.encode(registrationUserDTO.password()),
                List.of(roleService.findByName("ROLE_USER"))
        ));
    }
}
