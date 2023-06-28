package org.example.models.bank_only;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

import static org.example.constants.ValidationValues.CONDITION_FAILED_VALUE;

@Data
@NoArgsConstructor
public class ATM {
    private Bank bank;
    private int sessionAccountNumber;

    protected ATM(Bank bank) {
        this.bank = bank;
        this.sessionAccountNumber = CONDITION_FAILED_VALUE;
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
        if (sessionAccountNumber != CONDITION_FAILED_VALUE) {
            deposited = bank.updateAcctBal(sessionAccountNumber, amount);
        }

        return deposited;
    }

    public boolean withdraw(int amount) {
        boolean deposited = false;
        if (sessionAccountNumber != CONDITION_FAILED_VALUE) {
            deposited = bank.updateAcctBal(sessionAccountNumber, -amount);
        }

        return deposited;
    }

    public int getBalance() {
        if (sessionAccountNumber == CONDITION_FAILED_VALUE) {
            return 0;
        }
        Account account = bank.accessAcctInfo(sessionAccountNumber);
        return account.getAccountBalance();
    }

    public boolean logout() {
        if (sessionAccountNumber == CONDITION_FAILED_VALUE) {
            return false;
        }
        sessionAccountNumber = CONDITION_FAILED_VALUE;
        return true;
    }
}
