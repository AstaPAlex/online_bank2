package org.javaacademy.online_bank.repository;

import org.javaacademy.online_bank.entity.User;
import org.javaacademy.online_bank.exception.AlreadyExistsUserException;
import org.javaacademy.online_bank.exception.NotFoundUserException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserRepository {
    private final HashMap<UUID, User> users = new HashMap<>();

    public User add(User user) {
        users.values().stream()
                .map(User::getNumberPhone)
                .filter(number -> Objects.equals(number, user.getNumberPhone()))
                .findFirst()
                .ifPresent(number -> {
                    throw new AlreadyExistsUserException();
                });
        return generateUuidAndAdd(user);
    }

    private User generateUuidAndAdd(User user) {
        UUID uuid = UUID.randomUUID();
        user.setUuid(uuid);
        users.put(uuid, user);
        return user;
    }

    public UUID findUuidByNumber(String number) {
        return users.values().stream()
                .filter(user -> Objects.equals(user.getNumberPhone(), number))
                .findFirst()
                .map(User::getUuid)
                .orElseThrow(NotFoundUserException::new);
    }

    public Optional<User> findUserByUuid(UUID uuid) {
        return Optional.ofNullable(users.get(uuid));
    }
}
