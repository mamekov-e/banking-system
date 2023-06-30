package org.example.jdbc.queries;

public enum AccountQueries {
    GET("SELECT * FROM account WHERE account_number = ?;"),
    GET_LAST_ACCOUNT_NUM("SELECT account_number FROM account ORDER BY id DESC LIMIT 1;"),
    GET_ALL("SELECT * FROM account;"),
    GET_ALL_BY_BANK_ID("SELECT * FROM account where bank_id = ?;"),
    INSERT("INSERT INTO account (account_number,account_balance,bank_id) VALUES (?,?,?) RETURNING id;"),
    DELETE("DELETE FROM account WHERE id = ? RETURNING id;"),
    UPDATE("UPDATE account SET account_number = ?, account_balance = ?  WHERE id = ? RETURNING id;");

    public final String QUERY;

    AccountQueries(String QUERY) {
        this.QUERY = QUERY;
    }
}
