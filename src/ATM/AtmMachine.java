package ATM;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class AtmMachine {
    private ArrayList<User> users;
    private User currentUser;
    private int maxAttempts = 3;
    private double dailyWithdrawLimit = 500.0;
    private double amountWithdrawnToday = 0.0;

    Scanner input = new Scanner(System.in);

    public AtmMachine() {
        users = new ArrayList<>();
    }

    public void register() {
        System.out.println("==== REGISTER ====");
        System.out.print("Enter a username: ");
        String username = input.nextLine().trim();
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                System.out.println("Username already exists. Please try again.");
                return;
            }
        }
        int newPin;
        while (true) {
            System.out.print("Set your PIN (4 digits): ");
            try {
                newPin = input.nextInt();
                input.nextLine();
                if (String.valueOf(newPin).length() == 4) {
                    break;
                } else {
                    System.out.println("Invalid PIN. It must be exactly 4 digits.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a 4-digit number.");
                input.nextLine();
            }
        }
        User newUser = new User(username, newPin);
        users.add(newUser);
        System.out.println("Registration successful! Please log in.");
        login();
    }

    public void login() {
        System.out.println("==== LOGIN ====");
        System.out.print("Enter your username: ");
        String username = input.nextLine();
        currentUser = findUser(username);
        if (currentUser == null) {
            System.out.println("Account not found. Please register first.");
            return;
        }
        for (int attempts = 0; attempts < maxAttempts; attempts++) {
            System.out.print("Enter your PIN: ");
            int enteredPin = input.nextInt();
            if (currentUser.verifyPin(enteredPin)) {
                System.out.println("Login successful! Welcome, " + currentUser.getUsername() + "!");
                menu();
                return;
            } else {
                System.out.println("Incorrect PIN. Try again.");
            }
        }
        System.out.println("Too many failed attempts. Please try again later.");
    }

    private User findUser(String username) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    public void logout() {
        System.out.println("You have been logged out. Goodbye!");
        currentUser = null;
        start();
    }

    public void menu() {
        System.out.println("=============================");
        System.out.println("ENTER YOUR CHOICE:");
        System.out.println("1. CHECK A/C BALANCE");
        System.out.println("2. WITHDRAW MONEY");
        System.out.println("3. DEPOSIT MONEY");
        System.out.println("4. CHANGE PIN");
        System.out.println("5. VIEW TRANSACTION HISTORY");
        System.out.println("6. LOGOUT");
        System.out.println("=============================");

        int option = input.nextInt();
        switch (option) {
            case 1:
                checkBalance();
                return;
            case 2:
                withdrawMoney();
                return;
            case 3:
                depositMoney();
                return;
            case 4:
                changePin();
                return;
            case 5:
                viewTransactionHistory();
                return;
            case 6:
                logout();
                return;
            default:
                System.out.println("Invalid choice. Please select a valid option.");
                menu();
        }
    }

    public void checkBalance() {
        System.out.println("Your current balance is: $" + currentUser.getBalance());
        menu();
    }

    public void withdrawMoney() {
        System.out.println("Enter amount to withdraw: ");
        double amount = input.nextDouble();
        if (amount > currentUser.getBalance()) {
            System.out.println("INSUFFICIENT BALANCE!");
        } else if ((amountWithdrawnToday + amount) > dailyWithdrawLimit) {
            System.out.println("Daily withdrawal limit exceeded!");
        } else {
            currentUser.setBalance(currentUser.getBalance() - amount);
            amountWithdrawnToday += amount;
            currentUser.addTransaction("Withdrawn: $" + amount);
            System.out.println("Withdrawal successful! New balance: $" + currentUser.getBalance());
        }
        menu();
    }

    public void depositMoney() {
        System.out.println("Enter amount to deposit: ");
        double amount = input.nextDouble();
        currentUser.setBalance(currentUser.getBalance() + amount);
        currentUser.addTransaction("Deposited: $" + amount);
        System.out.println("Deposit successful! New balance: $" + currentUser.getBalance());
        menu();
    }

    public void changePin() {
        System.out.print("Enter your current PIN: ");
        int enteredPin = input.nextInt();
        if (currentUser.verifyPin(enteredPin)) {
            System.out.print("Enter your new PIN: ");
            int newPin = input.nextInt();
            currentUser.changePin(newPin);
            System.out.println("PIN changed successfully!");
        } else {
            System.out.println("Incorrect PIN! Try again.");
        }
        menu();
    }

    public void viewTransactionHistory() {
        System.out.println("Transaction History:");
        if (currentUser.getTransactionHistory().isEmpty()) {
            System.out.println("No transactions available.");
        } else {
            for (String transaction : currentUser.getTransactionHistory()) {
                System.out.println(transaction);
            }
        }
        menu();
    }

    public void start() {
        System.out.println("==== WELCOME TO THE ATM ====");
        System.out.println("1. REGISTER");
        System.out.println("2. LOGIN");
        System.out.println("3. EXIT");
        System.out.println("=============================");

        int choice = input.nextInt();
        input.nextLine();
        switch (choice) {
            case 1:
                register();
                break;
            case 2:
                login();
                break;
            case 3:
                System.out.println("Thank you for using the ATM. Goodbye!");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                start();
        }
    }

    public static void main(String[] args) {
        AtmMachine atm = new AtmMachine();
        atm.start();
    }
}
