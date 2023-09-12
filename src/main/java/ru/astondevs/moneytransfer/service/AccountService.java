package ru.astondevs.moneytransfer.service;


import ru.astondevs.moneytransfer.model.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountService {
    Optional<Account> createAccount(String name, String pin);
    List<Account> getAllAccounts();
    Optional<Account> getAccountByAccountNumber(String accountNumber);
    void deposit (String accountNumber, BigDecimal amount);

    void transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount, String pin);

    void withdraw(String accountNumber, BigDecimal amount, String pin);
}
