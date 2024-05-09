package org.javaacademy.online_bank.repository;

import org.javaacademy.online_bank.entity.User;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserRepository {
    private final Map<UUID, User> users = new HashMap<>();

    public User add(User user) {
        users.values().stream()
                .map(User::getNumberPhone)
                .filter(number -> Objects.equals(number, user.getNumberPhone()))
                .findFirst()
                .ifPresent(number -> {
                    throw new RuntimeException("A user with this phone number already exists!");
                });
        users.put(generateUuid(user), user);
        return user;
    }

    private UUID generateUuid(User user) {
        UUID uuid = UUID.randomUUID();
        user.setUuid(uuid);
        return uuid;
    }

    public UUID findUuidByNumberPhone(String number) {
        return users.values().stream()
                .filter(user -> Objects.equals(user.getNumberPhone(), number))
                .findFirst()
                .map(User::getUuid)
                .orElseThrow(() -> new RuntimeException("There is no user with this number!"));
    }

    public Optional<User> findUserByUuid(UUID uuid) {
        return Optional.ofNullable(users.get(uuid));
    }
}
