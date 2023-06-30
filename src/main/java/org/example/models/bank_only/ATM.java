package org.example.models.bank_only;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ATM {
    private int id;
    private int sessionAccountNumber;
    private int bankId;
}
