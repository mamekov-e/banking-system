package org.example.jdbc.service;

import org.example.exceptions.DataNotFoundException;
import org.example.jdbc.dao.AccountDao;
import org.example.jdbc.dao.AtmDao;
import org.example.jdbc.dao.BankDao;
import org.example.models.bank_only.ATM;
import org.example.models.bank_only.Account;
import org.example.models.bank_only.Bank;

import java.util.List;

public class BankService {
    private final AccountDao accountDao = new AccountDao();
    private final BankDao bankDao = new BankDao();
    private final AtmDao atmDao = new AtmDao();

    protected int createAccount(int bankId) {
        int lastAccountNumber = accountDao.selectLastAccountNumber();

        int newAccountNumber = lastAccountNumber + 1;
        int newAccountBalance = 0;
        Account account = new Account(newAccountNumber, newAccountBalance, bankId);
        accountDao.insert(account);
        return newAccountNumber;
    }

    protected ATM getAtm(int bankId) {
        ATM atmFound = new ATM();
        try {
            atmFound = atmDao.select(bankId);
            if (atmFound == null) {
                throw new DataNotFoundException("bankId", bankId);
            }
        } catch (DataNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return atmFound;
    }

    protected Account accessAcctInfo(int acctNum) {
        Account account = new Account();
        try {
            account = accountDao.select(acctNum);
            if (account == null) {
                throw new DataNotFoundException("account_number", acctNum);
            }
        } catch (DataNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return account;
    }

    protected boolean updateAcctBal(int acctNum, int diff) {
        Account accountToUpdate = accessAcctInfo(acctNum);

        int newBalance = accountToUpdate.getAccountBalance() + diff;
        accountToUpdate.setAccountBalance(newBalance);
        accountDao.update(accountToUpdate);

        return true;
    }

    public List<Bank> getAllBanks() {
        return bankDao.selectAll();
    }

}
