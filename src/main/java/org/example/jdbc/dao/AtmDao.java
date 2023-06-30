package org.example.jdbc.dao;

import org.example.jdbc.config.HikariCPConnector;
import org.example.jdbc.queries.AtmQueries;
import org.example.models.bank_only.ATM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AtmDao implements DaoBase<ATM, Integer> {

    @Override
    public ATM select(Integer bankId) {
        ATM atm = new ATM();

        try (Connection conn = HikariCPConnector.getConnection();
             PreparedStatement getAtmStmt = conn.prepareStatement(AtmQueries.GET.QUERY)) {
            getAtmStmt.setInt(1, bankId);

            try (ResultSet getAtmRs = getAtmStmt.executeQuery()) {
                if (getAtmRs.next()) {
                    int id = getAtmRs.getInt("id");
                    int sessionAccountNumber = getAtmRs.getInt("session_account_number");
                    int bankIdFromDb = getAtmRs.getInt("bank_id");
                    atm = new ATM(id, sessionAccountNumber, bankIdFromDb);
                }
            } catch (SQLException e) {
                System.out.println("Error when closing result set: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Error when getting ATM: " + e.getMessage());
            e.printStackTrace();
        }

        return atm;
    }

    @Override
    public List<ATM> selectAll() {
        //TODO
        return null;
    }

    @Override
    public boolean insert(ATM atm) {
        //TODO
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        //TODO
        return false;
    }

    @Override
    public boolean update(ATM atm) {
        boolean result = false;

        try (Connection conn = HikariCPConnector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(AtmQueries.UPDATE.QUERY)) {
            preparedStatement.setInt(1, atm.getSessionAccountNumber());
            preparedStatement.setInt(2, atm.getId());
            try (ResultSet updateAtmRs = preparedStatement.executeQuery()) {
                result = updateAtmRs.next();

            } catch (SQLException e) {
                System.out.println("Error when closing result set: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Error when updating atm: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}
