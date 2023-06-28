package org.example.app;

import org.example.models.bank_only.Bank;
import org.example.models.public_.Customer;

import java.util.Scanner;

import static org.example.constants.ValidationValues.CONDITION_FAILED_VALUE;

public class ConsoleAppJava {
    private static final Scanner input = new Scanner(System.in);

    public static void start() {
        System.out.println("CONSOLE APP ---- Banking System using Java");
        Bank bank = new Bank();
        Customer customer = new Customer(bank);

        boolean isAccountCreated = false;
        boolean exit = false;
        String userInput;
        while (!exit) {
            if (!isAccountCreated) {
                System.out.println("Welcome to our bank!\n" +
                        "Do you want to create account? (enter number)\n [1] yes [2] no");
                userInput = input.next();
                if (parseToInt(userInput) == 0) {
                    continue;
                }
                String noOption = "2";
                if (noOption.equals(userInput)) {
                    System.out.println("Okay, come whenever you want to create an account then. Bye bye!");
                    return;
                }
                customer.openAccount();
                isAccountCreated = true;
                System.out.println("The bank has ATM which provides several options to manage your money.");
            }
            System.out.println("Choose option (enter number):\n" +
                    " [1] get balance\n [2] deposit money\n [3] withdraw money\n [4] quit");
            userInput = input.next();

            exit = processUserInput(customer, userInput);
        }
    }

    private static boolean processUserInput(Customer customer, String userInputString) {
        int userInput = parseToInt(userInputString);
        if (userInput == CONDITION_FAILED_VALUE) {
            return false;
        }

        switch (userInput) {
            case 1:
                customer.checkBalance();
                break;
            case 2:
                System.out.print("\nEnter money you wanna to deposit: ");
                int moneyToDeposit = parseToInt(input.next());
                if (moneyToDeposit == CONDITION_FAILED_VALUE) {
                    return false;
                }
                customer.depositMoney(moneyToDeposit);
                break;
            case 3:
                System.out.print("\nEnter money you wanna to withdraw: ");
                int moneyToWithdraw = parseToInt(input.next());

                if (moneyToWithdraw == CONDITION_FAILED_VALUE) {
                    return false;
                }
                customer.withdrawMoney(moneyToWithdraw);
                break;
            case 4:
                System.out.println("\n***** Quiting...");
                return true;
            default:
                System.out.println("Choose one of the options provided!\n");
                break;
        }

        return false;
    }

    private static int parseToInt(String answer) {
        int toNumber = CONDITION_FAILED_VALUE;

        try {
            toNumber = Integer.parseInt(answer);
            if (toNumber < 0) {
                System.out.println("Enter positive number!\n");
                return CONDITION_FAILED_VALUE;
            }
        } catch (NumberFormatException e) {
            System.out.println("Enter number!\n");
        }
        return toNumber;
    }
}
