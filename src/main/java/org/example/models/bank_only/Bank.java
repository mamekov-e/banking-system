package org.example.models.bank_only;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Bank {
    private int id;
    private List<Account> accounts;
    private ATM atm;

    private String name;

    public Bank(int id, String name, List<Account> accounts) {
        this.id = id;
        this.name = name;
        this.accounts = accounts;
        this.atm = new ATM();
    }
}
