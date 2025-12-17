package com.vbforge;

import java.util.Objects;

public class BankAccount {

    private final String accountNumber;     //unique account identifier
    private final String ownerName;         //account owner's name
    private double balance;                 //current account balance
    private boolean isActive;               //account status

    //Constructor with accountNumber, ownerName, and initial balance
    public BankAccount(String accountNumber, String ownerName, double balance) {
        if(!matchAccountNumberPattern(accountNumber)) {
            throw new IllegalArgumentException(
                    "Account number must match pattern ACC-XXXXXX (6 digits)."
            );
        }
        if(ownerName == null || ownerName.isBlank()) {
            throw new IllegalArgumentException("Owner name cannot be null or empty.");
        }
        if(balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative.");
        }
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
        this.isActive = true;
    }

    //Constructor with accountNumber and ownerName (sets balance to 0.0)
    public BankAccount(String accountNumber, String ownerName){
        if(!matchAccountNumberPattern(accountNumber)) {
            throw new IllegalArgumentException(
                    "Account number must match pattern ACC-XXXXXX (6 digits)."
            );
        }
        if(ownerName == null || ownerName.isBlank()) {
            throw new IllegalArgumentException("Owner name cannot be null or empty.");
        }
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = 0.0;
        this.isActive = true;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public double getBalance() {
        return balance;
    }

    public boolean isActive() {
        return isActive;
    }

    //deposit(double amount) - adds money to balance, returns true if successful
    public boolean deposit(double amount) {
        if(amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        this.balance += amount;
        return true;
    }

    //withdraw(double amount) - removes money from balance, returns true if sufficient funds
    public boolean withdraw(double amount) {
        if(amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive.");
        }
        if(amount > this.balance) {
            throw new IllegalArgumentException("Amount cannot be greater than balance.");
        }
        this.balance -= amount;
        return true;
    }

    //transfer(BankAccount targetAccount, double amount) - transfers money between accounts
    public void transfer(BankAccount targetAccount, double amount){
        validateTransfer(targetAccount, amount);

        /*this.balance -= amount;
        targetAccount.balance += amount;*/

        this.withdraw(amount);  // Use withdraw() instead of direct balance modification
        targetAccount.deposit(amount);  // Use deposit() for consistency
    }

    //deactivateAccount() - sets account as inactive
    public void deactivateAccount() {
        this.isActive = false;
    }

    //activateAccount() - sets account as active
    public void activateAccount() {
        this.isActive = true;
    }

    //getFormattedBalance() - returns balance formatted as currency string
    public String getFormattedBalance(){
        return String.format("%.2f", this.balance);
    }

    //Override equals(), hashCode(), and toString() methods
    // for equals and hashcode only immutable identity fields
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BankAccount that)) return false;
        return Objects.equals(accountNumber, that.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "accountNumber='" + accountNumber + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", balance=" + balance +
                ", isActive=" + isActive +
                '}';
    }

    //helper method to match pattern "ACC-XXXXXX" (where X is digit)
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")                              //for avoid warning from IDE about inverting
    private boolean matchAccountNumberPattern(String accountNumber){
        return accountNumber != null && accountNumber.matches("^ACC-\\d{6}$");
    }

    //helper method to validate transfer:
    private void validateTransfer(BankAccount targetAccount, double amount) {
        if (targetAccount == null) {
            throw new IllegalArgumentException("Target account cannot be null.");
        }
        if (this == targetAccount) {
            throw new IllegalArgumentException("Cannot transfer to the same account.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive.");
        }
        if (amount > this.balance) {
            throw new IllegalArgumentException("Insufficient funds.");
        }
    }

}
