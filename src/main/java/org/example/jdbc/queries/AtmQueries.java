package org.example.jdbc.queries;

public enum AtmQueries {
    GET("SELECT * FROM atm WHERE bank_id = ?;"),
    UPDATE("UPDATE atm SET session_account_number = ?  WHERE id = ? RETURNING id;");

    public final String QUERY;

    AtmQueries(String QUERY) {
        this.QUERY = QUERY;
    }
}
