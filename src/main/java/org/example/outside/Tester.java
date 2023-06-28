package org.example.outside;

import org.example.banking_system.Bank;

public class Tester {
    public static void main(String[] args) {
        Bank bank = new Bank();
        Customer customer = new Customer(bank);

        customer.openAccount();
        customer.depositMoney(1500);
        customer.withdrawMoney(1450);
        int balanceNow = customer.checkBalance();
    }
}
