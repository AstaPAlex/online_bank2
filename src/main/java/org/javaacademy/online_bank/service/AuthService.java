package org.javaacademy.online_bank.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.online_bank.repository.AuthRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;

    public void addNewAuth(UUID uuid, String pinCode) {
        authRepository.add(uuid, pinCode);
    }

    //Аутентификация
    public boolean signIn(UUID uuid, String pinCode) {
        return authRepository.checkPinCode(uuid, pinCode);
    }
}
