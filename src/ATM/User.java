
package ATM;

import java.util.ArrayList;

public class User {
    private String username;
    private int pin;
    private double balance;
    private ArrayList<String> transactionHistory;

    public User(String username, int pin) {
        this.username = username;
        this.pin = pin;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public double getBalance() {
        return balance;
    }

    public ArrayList<String> getTransactionHistory() {
        return transactionHistory;
    }

    public boolean verifyPin(int enteredPin) {
        return this.pin == enteredPin;
    }

    public void changePin(int newPin) {
        this.pin = newPin;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void addTransaction(String transaction) {
        transactionHistory.add(transaction);
    }
}
