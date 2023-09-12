package ru.astondevs.moneytransfer.service;

import ru.astondevs.moneytransfer.exception.InvalidPinException;
import ru.astondevs.moneytransfer.exception.NotEnoughFundsException;
import ru.astondevs.moneytransfer.exception.NotFoundException;
import ru.astondevs.moneytransfer.model.Account;
import ru.astondevs.moneytransfer.model.Operation;
import ru.astondevs.moneytransfer.model.Transaction;
import ru.astondevs.moneytransfer.repository.AccountRepository;
import ru.astondevs.moneytransfer.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public Optional<Account> createAccount(String name, String pin) {

        if ((name == null) || (pin.length() != 4)) {
            throw new InvalidPinException("Invalid value");
        }

        var account = new Account(name, pin);

        accountRepository.save(account);

        return Optional.of(account);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Optional<Account> getAccountByAccountNumber(String accountNumber) {
        return accountRepository.findById(accountNumber);
    }

    @Override
    @Transactional
    public void deposit(String accountNumber, BigDecimal amount) {

        var byId = accountRepository.findById(accountNumber);

        if (byId.isEmpty()) {
            throw new NotFoundException("Аккаунт с данным номером не найден");
        }

        var account = byId.get();
        var transaction = new Transaction(accountNumber, accountNumber, LocalTime.now(), amount, Operation.DEPOSIT);

        account.setAmount(account.getAmount().add(amount));

        accountRepository.save(account);
        transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public void transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount, String pin) {
        var fromAccount = accountRepository.findById(fromAccountNumber);
        var toAccount = accountRepository.findById(toAccountNumber);

        if ((fromAccount.isEmpty()) || (toAccount.isEmpty())) {
            throw new NotFoundException("Ошибка перевода");
        }
        if (!(fromAccount.get().getPin().equals(pin))) {
            throw new InvalidPinException("Неверный пин код");
        }
        if (fromAccount.get().getAmount().compareTo(amount) < 0) {
            throw new NotEnoughFundsException();
        }

        fromAccount.get().setAmount(fromAccount.get().getAmount().subtract(amount));
        toAccount.get().setAmount(toAccount.get().getAmount().add(amount));

        var transaction = new Transaction(fromAccountNumber, toAccountNumber, LocalTime.now(), amount, Operation.TRANSFER);

        accountRepository.save(fromAccount.get());
        accountRepository.save(toAccount.get());
        transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public void withdraw(String accountNumber, BigDecimal amount, String pin) {

        var byId = accountRepository.findById(accountNumber);

        if (byId.isEmpty()) {
            throw new NotFoundException("Аккаунт с данным номером не найден");
        }
        if (byId.get().getAmount().compareTo(amount) < 0) {
            throw new NotEnoughFundsException();
        }
        if (!(byId.get().getPin().equals(pin))) {
            throw new InvalidPinException("Неверный пин код");
        }

        var account = byId.get();
        var transaction = new Transaction(accountNumber, accountNumber, LocalTime.now(), amount, Operation.WITHDRAW);

        account.setAmount(account.getAmount().subtract(amount));

        accountRepository.save(account);
        transactionRepository.save(transaction);
    }


}
