package org.example.models.public_;

import org.example.models.bank_only.ATM;
import org.example.models.bank_only.Bank;

import static org.example.constants.ValidationValues.CONDITION_FAILED_VALUE;

public class Customer {
    private final Bank bank;
    private ATM bankAtm;
    private int accountNumber = CONDITION_FAILED_VALUE;

    public Customer(Bank bank) {
        this.bank = bank;
    }

    public void openAccount() {
        System.out.println("***** Creating account...");
        if (accountNumber == CONDITION_FAILED_VALUE) {
            accountNumber = bank.createAccount();
            bankAtm = bank.getAtm();
            System.out.println("Account successfully created!");
        } else {
            System.out.println("Account already created!");
        }
    }

    public void depositMoney(int amount) {
        login();

        System.out.printf("***** Depositing money: %dtg...\n", amount);
        boolean deposited = bankAtm.deposit(amount);
        System.out.println(deposited ? "Deposited successfully!" : "Money was not deposited!");

        logout();
    }

    public void withdrawMoney(int amount) {
        login();

        int balance = bankAtm.getBalance();
        if (amount > balance) {
            System.out.println("Not enough money in account!");
            logout();
            return;
        }

        System.out.printf("***** Withdrawing money: %dtg...\n", amount);
        boolean withdrawn = bankAtm.withdraw(amount);
        System.out.println(withdrawn ? "Withdrawn successfully!" : "Money was not withdrawn!");

        logout();
    }

    public int checkBalance() {
        login();

        System.out.println("***** Getting balance...");
        int accountBalance = bankAtm.getBalance();
        System.out.println("Balance now: " + accountBalance);

        logout();

        return accountBalance;
    }

    private void login() {
        System.out.println("***** Logging in...");
        boolean loggedIn = bankAtm.loginToAccount(accountNumber);
        System.out.println(loggedIn ? "Welcome to bank's ATM!" : "Login failed!");
    }

    private void logout() {
        System.out.println("***** Logging out...");
        boolean loggedOut = bankAtm.logout();
        System.out.println(loggedOut ? "Logged out successfully" : "You are not in system to logout!");
    }
}
