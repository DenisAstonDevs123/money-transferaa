package ru.astondevs.moneytransfer.service;

import ru.astondevs.moneytransfer.model.Transaction;
import ru.astondevs.moneytransfer.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> getAllTransactionsByAccountNumber(String accountNumber) {
        return transactionRepository.findTransactionsByAccountNumberFrom(accountNumber);
    }
}
