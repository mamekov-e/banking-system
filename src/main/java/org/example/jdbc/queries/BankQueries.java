package org.example.jdbc.queries;

public enum BankQueries {
    GET("SELECT * FROM bank, account WHERE id = ? and bank.id = account.bank_id;"),
    GET_ALL("SELECT * FROM bank;");

    public final String QUERY;

    BankQueries(String QUERY) {
        this.QUERY = QUERY;
    }
}
