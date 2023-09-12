package ru.astondevs.moneytransfer.exception;

public class NotEnoughFundsException extends RuntimeException {
    public NotEnoughFundsException() {
        super("Недостаточно средств на счете.");
    }
}