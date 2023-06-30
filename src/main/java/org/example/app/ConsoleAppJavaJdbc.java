package org.example.app;

import lombok.SneakyThrows;
import org.example.constants.UserInputOptions;
import org.example.jdbc.config.HikariCPConnector;
import org.example.jdbc.service.BankService;
import org.example.jdbc.service.CustomerService;
import org.example.models.bank_only.Bank;

import java.util.List;
import java.util.Scanner;

import static org.example.constants.ValidationValues.*;

public class ConsoleAppJavaJdbc {
    private static final Scanner input = new Scanner(System.in);
    private static Bank bank;
    private static BankService bankService;
    private static CustomerService customerService;

    @SneakyThrows
    public static void start() {
        HikariCPConnector.getConnection();
        bankService = new BankService();
        customerService = new CustomerService();
        System.out.println("CONSOLE APP ---- Banking System using Java JDBC");

        boolean isAccountCreated = false;
        boolean isLoggedIn = false;
        String userInput;
        while (true) {
            if (!isLoggedIn) {
                if (bank == null) {
                    Bank bankSelected = selectBankOption();
                    if (bankSelected == null) {
                        continue;
                    } else {
                        if (bankSelected.getId() == INDEX_NO_BANKS_IN_DB_VALUE) {
                            System.out.println("There are no available banks yet. Come later!");
                            return;
                        }
                        if (bankSelected.getId() == INDEX_TO_QUIT_SYSTEM_VALUE) {
                            System.out.println("Thanks for using banking system, bye!");
                            return;
                        }
                        bank = bankSelected;
                        System.out.printf("\nWelcome to bank: %s! \n", bank.getName());
                    }
                }

                String bankName = bank.getName();
                System.out.printf("\nThe bank '%s' has ATM which provides several options to manage your money.", bankName);
                System.out.println("\nATM SCREEN (enter number):\n [1] login\n [2] create account\n [3] quit");
                userInput = input.next();
                if (parseToInt(userInput) == CONDITION_FAILED_VALUE) {
                    continue;
                }

                switch (userInput) {
                    case UserInputOptions.LOGIN:
                        System.out.print("Enter your account number: ");
                        int userAccountNumberLogin = parseToInt(input.next());
                        if (userAccountNumberLogin == CONDITION_FAILED_VALUE) {
                            continue;
                        }
                        isLoggedIn = customerService.login(userAccountNumberLogin);
                        if (!isLoggedIn) {
                            continue;
                        }
                        break;
                    case UserInputOptions.CREATE_ACCOUNT:
                        if (isAccountCreated) {
                            System.out.println("Account already created!");
                        } else {
                            customerService.openAccount(bank.getId());
                            isAccountCreated = true;
                        }
                        break;
                    case UserInputOptions.QUIT:
                        System.out.printf("Thanks for visiting %s, bye!\n", bankName);
                        bank = null;
                        isAccountCreated = false;
                        break;
                    default:
                        System.out.println("Enter one of the options!");
                        break;
                }
            } else {
                boolean isLogoutSelected = selectAtmActionsOption();
                if (isLogoutSelected) {
                    isLoggedIn = false;
                }
            }
        }
    }

    private static Bank selectBankOption() {
        System.out.println("\nBanks available (enter 0 to exit):");
        List<Bank> banks = bankService.getAllBanks();
        Bank bank = new Bank();

        if (banks.isEmpty()) {
            bank.setId(INDEX_TO_QUIT_SYSTEM_VALUE);
            return bank;
        }

        for (int i = 0; i < banks.size(); i++) {
            int optionNumber = i + 1;
            System.out.printf(" [%d] %s\n", optionNumber, banks.get(i).getName());
        }

        System.out.print("Choose one bank (enter number): ");
        int userInput = parseToInt(input.next());

        if (userInput == CONDITION_FAILED_VALUE) {
            return null;
        }
        if (userInput < 0 || userInput > banks.size()) {
            System.out.println("Choose one of the banks!");
            return null;
        }
        int exitOption = 0;
        if (userInput == exitOption) {
            bank.setId(INDEX_TO_QUIT_SYSTEM_VALUE);
            return bank;
        }

        bank = banks.get(userInput - 1);

        return bank;
    }

    private static boolean selectAtmActionsOption() {
        System.out.println("\nChoose option (enter number):\n" +
                " [1] get balance\n [2] deposit money\n [3] withdraw money\n [4] logout");
        String userInputString = input.next();

        int userInput = parseToInt(userInputString);
        if (userInput == CONDITION_FAILED_VALUE) {
            return false;
        }

        switch (userInput) {
            case UserInputOptions.GET_BALANCE:
                int balanceTaken = customerService.checkBalance();
                if (balanceTaken == CONDITION_FAILED_VALUE) {
                    return false;
                }
                break;
            case UserInputOptions.DEPOSIT_MONEY:
                System.out.print("\nEnter money you wanna to deposit: ");
                int moneyToDeposit = parseToInt(input.next());
                if (moneyToDeposit == CONDITION_FAILED_VALUE) {
                    return false;
                }
                customerService.depositMoney(moneyToDeposit);
                break;
            case UserInputOptions.WITHDRAW_MONEY:
                System.out.print("\nEnter money you wanna to withdraw: ");
                int moneyToWithdraw = parseToInt(input.next());

                if (moneyToWithdraw == CONDITION_FAILED_VALUE) {
                    return false;
                }
                customerService.withdrawMoney(moneyToWithdraw);
                break;
            case UserInputOptions.LOGOUT:
                customerService.logout();
                return true;
            default:
                System.out.println("Choose one of the options provided!");
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
            System.out.println("Enter number!");
        }
        return toNumber;
    }
}
