package org.example.jdbc.service;

import static org.example.constants.ValidationValues.CONDITION_FAILED_VALUE;

public class CustomerService {
    private final BankService bankService = new BankService();
    private final AtmService atmService = new AtmService();
    private int accountNumber = CONDITION_FAILED_VALUE;

    public void openAccount(int bankId) {
        System.out.println("***** Creating account...");
        if (accountNumber == CONDITION_FAILED_VALUE) {
            accountNumber = bankService.createAccount(bankId);
            System.out.printf("Account successfully created!\nYour account number is %s.\n", accountNumber);
        }
    }

    public void depositMoney(int amount) {
        System.out.printf("***** Depositing money: %dtg...\n", amount);
        boolean deposited = atmService.deposit(accountNumber, amount);
        System.out.println(deposited ? "Deposited successfully!" : "Money was not deposited!");
    }

    public void withdrawMoney(int amount) {
        int balance = atmService.getBalance(accountNumber);
        if (balance == CONDITION_FAILED_VALUE) {
            System.out.println("Session number expired!");
            return;
        }
        if (amount > balance) {
            System.out.println("Not enough money in balance!");
            return;
        }

        System.out.printf("***** Withdrawing money: %dtg...\n", amount);
        boolean withdrawn = atmService.withdraw(accountNumber, amount);
        System.out.println(withdrawn ? "Withdrawn successfully!" : "Money was not withdrawn!");
    }

    public int checkBalance() {
        int accountBalance = atmService.getBalance(accountNumber);
        if (accountBalance == CONDITION_FAILED_VALUE) {
            System.out.println("Session number expired!");
            return CONDITION_FAILED_VALUE;
        }
        System.out.println("***** Getting balance...");
        System.out.println("Balance now: " + accountBalance);
        return accountBalance;
    }

    public boolean login(int accountNumberLogin) {
        System.out.println("***** Logging in...");
        boolean loggedIn = atmService.loginToAccount(accountNumberLogin);
        if (loggedIn) {
            accountNumber = accountNumberLogin;
            System.out.println("Welcome to bank's ATM!");
        } else {
            System.out.println("Login failed!\n" +
                    "| May be atm is busy or account number is incorrect |");
        }
        return loggedIn;
    }

    public void logout() {
        System.out.println("***** Logging out...");
        boolean loggedOut = atmService.logout(accountNumber);
        if (loggedOut) {
            System.out.println("Logged out successfully");
            accountNumber = CONDITION_FAILED_VALUE;
        } else {
            System.out.println("You are not in system to logout!");
        }
    }
}
