package com.vbforge;

public class Bank {

    private final BankAccount[] accounts;                   //array to store all bank accounts
    private int size;                                       //number of stored accounts

    public Bank(int capacity) {
        if(capacity <= 0) {
            throw new IllegalArgumentException("Bank capacity must be greater than 0.");
        }

        this.accounts = new BankAccount[capacity];
        this.size = 0;
    }

    //addAccount(BankAccount account) - adds account to bank
    //no duplicates allowed
    public boolean addAccount(BankAccount account) {
        if(account == null) {
            throw new IllegalArgumentException("Bank account must not be null.");
        }
        if(size == accounts.length) {
            return false; //bank is full
        }
        if(findAccount(account.getAccountNumber()) != null) {
            return false; //no duplicates
        }
        accounts[size++] = account;
        return true;
    }

    //findAccount(String accountNumber) - returns account or null
    public BankAccount findAccount(String accountNumber) {
        if(accountNumber == null) {
            throw new IllegalArgumentException("Account number cannot be null.");
        }
        for(int i = 0; i < size; i++) {
            if(accounts[i].getAccountNumber().equals(accountNumber)) {
                return accounts[i];
            }
        }
        return null;
    }

    //getTotalBankBalance() - returns sum of all account balances
    public double getTotalBankBalance(){
        double total = 0;
        for(int i = 0; i < size; i++) {
            total += accounts[i].getBalance();
        }
        return total;
    }

    //getAccountsByOwner(String ownerName) - returns array of accounts for given owner
    public BankAccount[] getAccountsByOwner(String ownerName){
        if(ownerName == null || ownerName.isBlank()) {
            return new BankAccount[0]; //this avoids NullPointerException for callers — best practice
        }

        int count = 0;
        for (int i = 0; i < size; i++) {
            if(accounts[i].getOwnerName().equals(ownerName)) {
                count++;
            }
        }

        BankAccount[] result = new BankAccount[count];
        int index = 0;
        for(int i = 0; i < size; i++) {
            if(accounts[i].getOwnerName().equals(ownerName)) {
                result[index++] = accounts[i];
            }
        }
        return result;
    }

    //processMonthlyInterest() - applies interest to all savings accounts
    public void processMonthlyInterest(){
        for (int i = 0; i < size; i++) {
            if(accounts[i] instanceof SavingsAccount) {
                ((SavingsAccount)accounts[i]).applyInterest();
            }
        }
    }

    //getActiveAccountsCount() - returns number of active accounts
    public int getActiveAccountsCount(){
        int count = 0;
        for(int i = 0; i < size; i++) {
           if(accounts[i].isActive()){
               count++;
           }
        }
        return count;
    }

    //generateAccountReport() - returns formatted string with all account information
    public String generateAccountReport(){StringBuilder sb = new StringBuilder();
        sb.append("=== Bank Account Report ===\n");

        for (int i = 0; i < size; i++) {
            sb.append(accounts[i]).append("\n");
        }

        sb.append("---------------------------\n");
        sb.append("Total accounts: ").append(size).append("\n");
        sb.append("Active accounts: ").append(getActiveAccountsCount()).append("\n");
        sb.append("Total bank balance: ")
                .append(String.format("%.2f", getTotalBankBalance()))
                .append("\n");

        return sb.toString();
    }

}















