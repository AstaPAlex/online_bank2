package org.javaacademy.online_bank.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.online_bank.dto.OperationDtoRs;
import org.javaacademy.online_bank.entity.Account;
import org.javaacademy.online_bank.entity.Operation;
import org.javaacademy.online_bank.entity.User;
import org.javaacademy.online_bank.repository.OperationRepository;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OperationService {
    private final OperationRepository operationRepository;
    private final AccountService accountService;
    private final UserService userService;

    public TreeSet<OperationDtoRs> getAllOperationsByToken(String token) {
        User user = userService.findUser(token);
        return getAllOperationsByUser(user).stream()
                .map(this::convertToDto)
                .collect(Collectors.toCollection(
                () -> new TreeSet<>(Comparator.comparing(OperationDtoRs::getDateTime).reversed()))
        );
    }

    public void addOperation(Operation operation) {
        operationRepository.add(operation);
    }

    public TreeSet<Operation> getAllOperationsByUser(User user) {
        Set<Account> accounts = accountService.findAllAccountsByUser(user);
        return operationRepository.getAllOperationsByAccounts(accounts);
    }

    private OperationDtoRs convertToDto(Operation operation) {
        return new OperationDtoRs(operation.getUuid(), operation.getDateTime(), operation.getNumberAccount(),
                operation.getType(), operation.getAmount(), operation.getDescription());
    }
}
