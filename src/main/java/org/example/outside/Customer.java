package org.example.outside;

import org.example.banking_system.ATM;
import org.example.banking_system.Bank;

public class Customer {
    private final Bank bank;
    private ATM bankAtm;
    private int accountNumber = -1;

    public Customer(Bank bank) {
        this.bank = bank;
    }

    public void openAccount() {
        System.out.println("***** Creating account...");
        if (accountNumber == -1) {
            accountNumber = bank.createAccount();
            bankAtm = bank.getAtm();
            System.out.println("Account successfully created!");
        } else {
            System.out.println("Account already created!");
        }
    }

    public void depositMoney(int amount) {
        if (isAccountCreated())
            return;

        login();

        System.out.printf("***** Depositing money: %dtg...\n", amount);
        boolean deposited = bankAtm.deposit(amount);
        System.out.println(deposited ? "Deposited successfully!" : "Money was not deposited!");

        logout();
    }

    public void withdrawMoney(int amount) {
        if (isAccountCreated())
            return;

        login();

        System.out.printf("***** Withdrawing money: %dtg...\n", amount);
        boolean withdrawn = bankAtm.withdraw(amount);
        System.out.println(withdrawn ? "Withdrawn successfully!" : "Money was not withdrawn!");

        logout();
    }

    public int checkBalance() {
        if (isAccountCreated())
            return -1;

        login();

        System.out.println("***** Getting balance...");
        int accountBalance = bankAtm.getBalance();
        System.out.println("Balance now: " + accountBalance);

        logout();

        return accountBalance;
    }

    private boolean isAccountCreated() {
        if (bankAtm == null) {
            System.out.println("Create account first using method: openAccount()!");
            return true;
        }
        return false;
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
