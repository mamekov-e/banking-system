package org.example.banking_system;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
public class ATM {
    private Bank bank;
    private int sessionAccountNumber;

    protected ATM(Bank bank) {
        this.bank = bank;
        this.sessionAccountNumber = -1;
    }

    public boolean loginToAccount(int acctNum) {
        Optional<Account> account = bank.findUserByAcctNum(acctNum);
        if (account.isPresent()) {
            sessionAccountNumber = acctNum;
            return true;
        }

        return false;
    }

    public boolean deposit(int amount) {
        boolean deposited = false;
        if (sessionAccountNumber != -1) {
            deposited = bank.updateAcctBal(sessionAccountNumber, amount);
        }

        return deposited;
    }

    public boolean withdraw(int amount) {
        boolean deposited = false;
        if (sessionAccountNumber != -1) {
            deposited = bank.updateAcctBal(sessionAccountNumber, -amount);
        }

        return deposited;
    }

    public int getBalance() {
        if (sessionAccountNumber == -1) {
            return 0;
        }
        Account account = bank.accessAcctInfo(sessionAccountNumber);
        return account.getAccountBalance();
    }

    public boolean logout() {
        if (sessionAccountNumber == -1) {
            return false;
        }
        sessionAccountNumber = -1;
        return true;
    }
}
