package ru.astondevs.moneytransfer.service;

import ru.astondevs.moneytransfer.exception.InvalidPinException;
import ru.astondevs.moneytransfer.exception.NotEnoughFundsException;
import ru.astondevs.moneytransfer.exception.NotFoundException;
import ru.astondevs.moneytransfer.model.Account;
import ru.astondevs.moneytransfer.model.Operation;
import ru.astondevs.moneytransfer.model.Transaction;
import ru.astondevs.moneytransfer.repository.AccountRepository;
import ru.astondevs.moneytransfer.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    }