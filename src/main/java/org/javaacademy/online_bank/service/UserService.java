package org.javaacademy.online_bank.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.online_bank.config.TokenProperty;
import org.javaacademy.online_bank.dto.UserDto;
import org.javaacademy.online_bank.entity.User;
import org.javaacademy.online_bank.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final int MAX_LIMIT_GENERATOR_PIN_CODE = 10_000;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final TokenProperty tokenProperty;

    public String signUp(UserDto userDto) {
        User user = userRepository.add(convertToEntity(userDto));
        String pinCode = generatePinCode();
        authService.addNewAuth(user.getUuid(), pinCode);
        return pinCode;
    }

    private String generatePinCode() {
        return String.format("%04d", new Random().nextInt(MAX_LIMIT_GENERATOR_PIN_CODE));
    }

    private User convertToEntity(UserDto userDto) {
        return new User(userDto.getFullName(), userDto.getNumberPhone());
    }

    public String signIn(String numberPhone, String pinCode) {
        UUID uuid = userRepository.findUuidByNumberPhone(numberPhone);
        if (!authService.signIn(uuid, pinCode)) {
            throw new RuntimeException("Invalid PIN code!");
        }
        return generateToken(uuid);
    }

    private String generateToken(UUID uuid) {
        return tokenProperty.getPrefix() + uuid.toString() + tokenProperty.getPostfix();
    }

    public UUID decryptionToken(String token) {
        try {
            return UUID.fromString(token
                    .substring(
                            tokenProperty.getPrefix().length(),
                            token.length() - tokenProperty.getPostfix().length())
            );
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid token!");
        }
    }

    public User findUser(String token) {
        return userRepository
                .findUserByUuid(decryptionToken(token))
                .orElseThrow(() -> new RuntimeException("Such a user has not been found!"));
    }
}
