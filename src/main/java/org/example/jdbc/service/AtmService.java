package org.example.jdbc.service;

import org.example.jdbc.dao.AtmDao;
import org.example.models.bank_only.ATM;
import org.example.models.bank_only.Account;

import static org.example.constants.ValidationValues.CONDITION_FAILED_VALUE;

public class AtmService {
    private final BankService bankService = new BankService();
    private final AtmDao atmDao = new AtmDao();

    private ATM getSessionAccountNumber(int acctNum) {
        Account account = bankService.accessAcctInfo(acctNum);
        if (account == null) {
            return null;
        }
        return bankService.getAtm(account.getBankId());
    }

    public boolean loginToAccount(int acctNum) {
        ATM atm = getSessionAccountNumber(acctNum);
        if (atm == null) {
            System.out.println("Account not found!");
            return false;
        }

        if (atm.getSessionAccountNumber() == CONDITION_FAILED_VALUE) {
            atm.setSessionAccountNumber(acctNum);
            return atmDao.update(atm);
        }

        return false;
    }

    public boolean deposit(int userAccountNumber, int amount) {
        ATM atm = getSessionAccountNumber(userAccountNumber);
        if (atm == null) {
            System.out.println("Account not found!");
            return false;
        }
        boolean deposited = false;
        if (atm.getSessionAccountNumber() != CONDITION_FAILED_VALUE) {
            deposited = bankService.updateAcctBal(atm.getSessionAccountNumber(), amount);
        }

        return deposited;
    }

    public boolean withdraw(int userAccountNumber, int amount) {
        ATM atm = getSessionAccountNumber(userAccountNumber);
        if (atm == null) {
            System.out.println("Account not found!");
            return false;
        }
        boolean deposited = false;
        if (atm.getSessionAccountNumber() != CONDITION_FAILED_VALUE) {
            deposited = bankService.updateAcctBal(atm.getSessionAccountNumber(), -amount);
        }

        return deposited;
    }

    public int getBalance(int userAccountNumber) {
        ATM atm = getSessionAccountNumber(userAccountNumber);
        if (atm == null) {
            System.out.println("Account not found!");
            return CONDITION_FAILED_VALUE;
        }
        if (atm.getSessionAccountNumber() == CONDITION_FAILED_VALUE) {
            return CONDITION_FAILED_VALUE;
        }
        Account account = bankService.accessAcctInfo(atm.getSessionAccountNumber());
        return account.getAccountBalance();
    }

    public boolean logout(int userAccountNumber) {
        ATM atm = getSessionAccountNumber(userAccountNumber);
        if (atm == null) {
            System.out.println("Account not found!");
            return false;
        }
        if (atm.getSessionAccountNumber() == CONDITION_FAILED_VALUE) {
            return false;
        }
        atm.setSessionAccountNumber(CONDITION_FAILED_VALUE);
        return atmDao.update(atm);
    }
}
