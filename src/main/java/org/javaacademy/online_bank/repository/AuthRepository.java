package org.javaacademy.online_bank.repository;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Component
public class AuthRepository {
    private final Map<UUID, String> pinCodes = new HashMap<>();

    public void add(UUID uuid, String pinCode) {
        pinCodes.put(uuid, pinCode);
    }

    public boolean checkPinCode(UUID uuid, String pinCode) {
        return Objects.equals(pinCodes.get(uuid), pinCode);
    }
}
