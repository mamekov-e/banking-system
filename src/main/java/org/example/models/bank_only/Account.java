package org.example.models.bank_only;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private int id;
    private int accountNumber;
    private int accountBalance;
    private int bankId;

    public Account(int accountNumber, int accountBalance, int bankId) {
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
        this.bankId = bankId;
    }
}
