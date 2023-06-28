package org.example.models.bank_only;

import lombok.Data;
import org.example.exceptions.AccountNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class Bank {
    private List<Account> accounts;
    private ATM atm;

    public Bank() {
        this.atm = new ATM(this);
        this.accounts = new ArrayList<>();
    }

    public int createAccount() {
        int newAccountNumber = accounts.size() + 1;
        int newAccountBalance = 0;
        accounts.add(new Account(newAccountNumber, newAccountBalance));
        return newAccountNumber;
    }

    protected Account accessAcctInfo(int acctNum) {
        Optional<Account> accountOptional = findUserByAcctNum(acctNum);
        return accountOptional.orElse(new Account());
    }

    protected boolean updateAcctBal(int acctNum, int diff) {
        Optional<Account> accountToUpdateOptional = findUserByAcctNum(acctNum);

        accountToUpdateOptional.ifPresent(acc -> {
            int newBalance = acc.getAccountBalance() + diff;
            acc.setAccountBalance(newBalance);
        });

        return true;
    }

    protected Optional<Account> findUserByAcctNum(int acctNum) {
        Optional<Account> account = Optional.empty();
        try {
            account = Optional.of(accounts.stream()
                    .filter(acc -> acc.getAccountNumber() == acctNum)
                    .findFirst()
                    .orElseThrow(() -> new AccountNotFoundException(acctNum)));
        } catch (AccountNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return account;
    }
}
