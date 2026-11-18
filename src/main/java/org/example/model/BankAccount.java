package org.example.model;

import exceptions.InvalidAmountException;

public class BankAccount {
    private String customerName;
    private Long userId;
    private String userPassword;
    private double accountBalance;

    public BankAccount(String customerName, Long userId, String userPassword) {
        this.customerName = customerName;
        this.userId = userId;
        this.userPassword = userPassword;
        this.accountBalance = 0.0;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException("Сума має бути більше 0");
        }
        this.accountBalance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException("Сума має бути більше 0");
        }
        if (amount > this.accountBalance) {
            throw new InvalidAmountException("Недостатньо коштів на рахунку");
        }
        this.accountBalance -= amount;
    }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getUserPassword() { return userPassword; }
    public void setUserPassword(String userPassword) { this.userPassword = userPassword; }
    public double getAccountBalance() { return accountBalance; }
    public void setAccountBalance(double accountBalance) { this.accountBalance = accountBalance; }
}