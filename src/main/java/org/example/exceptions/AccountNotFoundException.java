package org.example.exceptions;

public class AccountNotFoundException extends Exception {
    public AccountNotFoundException(int accNum) {
        super(String.format("Account with account number %d not found!", accNum));
    }
}
