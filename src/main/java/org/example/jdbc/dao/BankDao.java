package org.example.jdbc.dao;

import org.example.jdbc.config.HikariCPConnector;
import org.example.jdbc.queries.BankQueries;
import org.example.models.bank_only.Account;
import org.example.models.bank_only.Bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BankDao implements DaoBase<Bank, Integer> {

    @Override
    public Bank select(Integer bankId) {
        Bank bank = new Bank();
        bank.setId(-1);

        try (Connection conn = HikariCPConnector.getConnection();
             PreparedStatement getBankStmt = conn.prepareStatement(BankQueries.GET.QUERY)) {
            getBankStmt.setInt(1, bankId);

            try (ResultSet getBankRs = getBankStmt.executeQuery();) {
                AccountDao accountDao = new AccountDao();
                List<Account> accounts = accountDao.retrieveAccountsFromResultSet(getBankStmt);

                getBankRs.previous();
                int id = getBankRs.getInt("id");
                String bankName = getBankRs.getString("name");

                return new Bank(id, bankName, accounts);
            } catch (SQLException e) {
                System.out.println("Error when closing result set: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Error when getting bank: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Bank> selectAll() {
        try (Connection conn = HikariCPConnector.getConnection();
             PreparedStatement getAllBanksStmt = conn.prepareStatement(BankQueries.GET_ALL.QUERY);
             ResultSet banksRs = getAllBanksStmt.executeQuery()) {

            List<Bank> banks = new ArrayList<>();
            while (banksRs.next()) {
                int bankId = banksRs.getInt("id");
                String name = banksRs.getString("name");

                AccountDao accountDao = new AccountDao();
                List<Account> accounts = accountDao.selectAllBankAccounts(bankId);
                banks.add(new Bank(bankId, name, accounts));
            }
            return banks;
        } catch (SQLException e) {
            System.out.println("Error when selecting all banks: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insert(Bank user) {
        //TODO
        return false;
    }

    @Override
    public boolean delete(Integer username) {
        //TODO
        return false;
    }

    @Override
    public boolean update(Bank user) {
        //TODO
        return false;
    }
}
