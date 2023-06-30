package org.example.jdbc.dao;

import org.example.jdbc.config.HikariCPConnector;
import org.example.jdbc.queries.AccountQueries;
import org.example.models.bank_only.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDao implements DaoBase<Account, Integer> {

    @Override
    public Account select(Integer acctNum) {
        try (Connection conn = HikariCPConnector.getConnection();
             PreparedStatement getAccountStmt = conn.prepareStatement(AccountQueries.GET.QUERY)) {
            getAccountStmt.setInt(1, acctNum);

            try (ResultSet getAccountRs = getAccountStmt.executeQuery();) {
                if (getAccountRs.next()) {
                    int id = getAccountRs.getInt("id");
                    int accountNumber = getAccountRs.getInt("account_number");
                    int accountBalance = getAccountRs.getInt("account_balance");
                    int bankId = getAccountRs.getInt("bank_id");
                    return new Account(id, accountNumber, accountBalance, bankId);
                }
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

    public int selectLastAccountNumber() {
        int lastAccountNumber = 0;
        try (Connection conn = HikariCPConnector.getConnection();
             PreparedStatement getLastAccountStmt = conn.prepareStatement(AccountQueries.GET_LAST_ACCOUNT_NUM.QUERY)) {

            try (ResultSet getLastAccountRs = getLastAccountStmt.executeQuery()) {
                if (getLastAccountRs.next()) {
                    lastAccountNumber = getLastAccountRs.getInt("account_number");
                }
            } catch (SQLException e) {
                System.out.println("Error when closing result set: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Error when getting bank: " + e.getMessage());
            e.printStackTrace();
        }

        return lastAccountNumber;
    }

    @Override
    public List<Account> selectAll() {
        try (Connection conn = HikariCPConnector.getConnection();
             PreparedStatement getAllAccountsStmt = conn.prepareStatement(AccountQueries.GET_ALL.QUERY)) {

            return retrieveAccountsFromResultSet(getAllAccountsStmt);
        } catch (SQLException e) {
            System.out.println("Error when selecting all banks: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<Account> selectAllBankAccounts(int bankId) {
        try (Connection conn = HikariCPConnector.getConnection();
             PreparedStatement getBankAccountsStmt = conn.prepareStatement(AccountQueries.GET_ALL_BY_BANK_ID.QUERY)) {
            getBankAccountsStmt.setInt(1, bankId);

            return retrieveAccountsFromResultSet(getBankAccountsStmt);
        } catch (SQLException e) {
            System.out.println("Error when selecting all banks: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    protected List<Account> retrieveAccountsFromResultSet(PreparedStatement statement) throws SQLException {
        List<Account> accounts = new ArrayList<>();
        try (ResultSet accountsRs = statement.executeQuery();) {
            while (accountsRs.next()) {
                int id = accountsRs.getInt("id");
                int accountNumber = accountsRs.getInt("account_number");
                int accountBalance = accountsRs.getInt("account_balance");
                accounts.add(new Account(id, accountNumber, accountBalance));
            }
        } catch (SQLException e) {
            System.out.println("Error when closing result set: " + e.getMessage());
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public boolean insert(Account account) {
        boolean result = false;

        try (Connection conn = HikariCPConnector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(AccountQueries.INSERT.QUERY)) {
            preparedStatement.setInt(1, account.getAccountNumber());
            preparedStatement.setInt(2, account.getAccountBalance());
            preparedStatement.setInt(3, account.getBankId());

            try (ResultSet insertAccountRs = preparedStatement.executeQuery()) {
                result = insertAccountRs.next();

            } catch (SQLException e) {
                System.out.println("Error when closing result set: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Error when inserting account: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean delete(Integer username) {
        // TODO
        return false;
    }

    @Override
    public boolean update(Account account) {
        boolean result = false;

        try (Connection conn = HikariCPConnector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(AccountQueries.UPDATE.QUERY)) {
            preparedStatement.setInt(1, account.getAccountNumber());
            preparedStatement.setInt(2, account.getAccountBalance());
            preparedStatement.setInt(3, account.getId());
            try (ResultSet updateAccountRs = preparedStatement.executeQuery()) {
                result = updateAccountRs.next();

            } catch (SQLException e) {
                System.out.println("Error when closing result set: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Error when updating account: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}
