package org.example.model;

import exceptions.InvalidAmountException;

public class CheckingAccount extends BankAccount {
    private double dailyWithdrawalLimit = 300;

    public CheckingAccount(String customerName, Long userId, String userPassword) {
        super(customerName, userId, userPassword);
    }

    @Override
    public void deposit(double amount) {
        super.deposit(amount);
    }

    @Override
    public void withdraw(double amount) {
        if (amount > dailyWithdrawalLimit) {
            throw new InvalidAmountException("Перевищено денний ліміт зняття");
        }
        super.withdraw(amount);
    }

    public double getDailyWithdrawalLimit() { return dailyWithdrawalLimit; }
    public void setDailyWithdrawalLimit(double dailyWithdrawalLimit) { this.dailyWithdrawalLimit = dailyWithdrawalLimit; }
}