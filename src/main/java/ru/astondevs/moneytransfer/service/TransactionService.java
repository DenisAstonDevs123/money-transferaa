package ru.astondevs.moneytransfer.service;

import ru.astondevs.moneytransfer.model.Transaction;

import java.util.List;

public interface TransactionService {

    List<Transaction> getAllTransactions();
    List<Transaction> getAllTransactionsByAccountNumber(String accountNumber);
}
