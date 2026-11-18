package org.example;

import exceptions.AccountAlreadyExistsException;
import exceptions.AccountNotFoundException;
import exceptions.InvalidAmountException;
import exceptions.InvalidPasswordException;
import org.example.model.BankAccount;
import org.example.model.CheckingAccount;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientInterface {

    public static void main(String[] args) throws IOException {
        ClientInterface clientInterface = new ClientInterface();
        clientInterface.start();
    }

    private final List<BankAccount> accounts = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    private List<String> options = List.of(
            "1 - Create account",
            "2 - Delete account",
            "3 - Make account deposit",
            "4 - Make account withdrawal",
            "5 - Print account info",
            "6 - Exit"
    );

    public void start() throws IOException {
        String option;
        while (true) {
            printMainMenu();
            option = scanner.next();

            try {
                switch (Integer.parseInt(option)) {
                    case 1:
                        addNewAccount();
                        break;
                    case 2:
                        deleteAccount();
                        break;
                    case 3:
                        deposit();
                        break;
                    case 4:
                        withdraw();
                        break;
                    case 5:
                        printInfo();
                        break;
                    case 6:
                        return;
                    default:
                        System.err.println("Please enter a valid choice (1 thru 6)\n");
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage() + "\n");
            }
        }
    }

    private void printMainMenu() throws IOException {
        System.out.println("\n****************");
        System.out.println("Banking system: ");
        System.out.println("****************");
        options.forEach(System.out::println);
        System.out.print("\n>>> Choose your option: ");
    }

    private BankAccount findAccount(String userIdStr, String password) {
        Long id = Long.parseLong(userIdStr);
        for (BankAccount acc : accounts) {
            if (acc.getUserId().equals(id)) {
                if (!acc.getUserPassword().equals(password)) {
                    throw new InvalidPasswordException("Невірний пароль");
                }
                return acc;
            }
        }
        throw new AccountNotFoundException("Акаунт не знайдено");
    }

    private void addNewAccount() {
        System.out.println("\n****************");
        System.out.println("Creating new account: ");
        System.out.println("****************");

        System.out.print(">>> Enter customer name: ");
        var name = scanner.next();
        System.out.print(">>> Enter user id: ");
        var userId = scanner.next();
        System.out.print(">>> Enter user password: ");
        var password = scanner.next();
        System.out.print(">>> Confirm password: ");
        var confirmPassword = scanner.next();

        Long id = Long.parseLong(userId);
        for (BankAccount acc : accounts) {
            if (acc.getUserId().equals(id)) {
                throw new AccountAlreadyExistsException("Акаунт з таким ID вже існує");
            }
        }

        if (password.length() < 8 || !password.matches(".*\\d.*")) {
            throw new InvalidPasswordException("Пароль має містити мінімум 8 символів і хоча б одну цифру");
        }
        if (!password.equals(confirmPassword)) {
            throw new InvalidPasswordException("Паролі не співпадають");
        }

        accounts.add(new CheckingAccount(name, id, password));
        System.out.println("Account created successfully!");
    }

    private void deleteAccount() {
        System.out.println("\n****************");
        System.out.println("Deleting account: ");
        System.out.println("****************");

        System.out.print(">>> Enter user id: ");
        var userId = scanner.next();
        System.out.print(">>> Enter user password: ");
        var password = scanner.next();

        BankAccount acc = findAccount(userId, password);
        if (acc.getAccountBalance() != 0) {
            throw new InvalidAmountException("Для видалення баланс має бути 0");
        }
        accounts.remove(acc);
        System.out.println("Account deleted successfully!");
    }

    private void deposit() {
        System.out.println("\n****************");
        System.out.println("Making a deposit: ");
        System.out.println("****************");

        System.out.print(">>> Enter user id: ");
        var userId = scanner.next();
        System.out.print(">>> Enter user password: ");
        var password = scanner.next();
        System.out.print(">>> Enter amount: ");
        var amount = scanner.next();

        BankAccount acc = findAccount(userId, password);
        acc.deposit(Double.parseDouble(amount));
        System.out.println("Deposit successful!");
    }

    private void withdraw() {
        System.out.println("\n****************");
        System.out.println("Making a withdrawal: ");
        System.out.println("****************");

        System.out.print(">>> Enter user id: ");
        var userId = scanner.next();
        System.out.print(">>> Enter user password: ");
        var password = scanner.next();
        System.out.print(">>> Enter amount: ");
        var amount = scanner.next();

        BankAccount acc = findAccount(userId, password);
        acc.withdraw(Double.parseDouble(amount));
        System.out.println("Withdrawal successful!");
    }

    private void printInfo() {
        System.out.println("\n****************");
        System.out.println("Printing account info: ");
        System.out.println("****************");

        System.out.print(">>> Enter user id: ");
        var userId = scanner.next();
        System.out.print(">>> Enter user password: ");
        var password = scanner.next();

        CheckingAccount acc = (CheckingAccount) findAccount(userId, password);

        System.out.println("\nAccount info: ");
        System.out.println(" - customer name: " + acc.getCustomerName());
        System.out.println(" - account balance: " + acc.getAccountBalance());
        System.out.println(" - account type: Checking");
        System.out.println(" - account daily withdrawal limit: " + acc.getDailyWithdrawalLimit());

        System.out.print("\nPress Enter to return to main menu...");

        try {
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}