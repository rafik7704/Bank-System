package com.bank;

public class User {
    private String fullName;
    private double balance;
    private String accountNumber;

    public User(String fullName, double balance, String accountNumber) {
        this.fullName = fullName;
        this.balance = balance;
        this.accountNumber = accountNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
