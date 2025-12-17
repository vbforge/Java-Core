package com.vbforge;

public class SavingsAccount extends BankAccount{

    private final double interestRate;        //annual interest rate (as percentage)
    private final int minimumBalance;         //minimum required balance

    public SavingsAccount(String accountNumber, String ownerName, double balance, double interestRate, int minimumBalance) {
        super(accountNumber, ownerName, balance);

        interestRateValidator(interestRate);

        if(minimumBalance < 0){
            throw new IllegalArgumentException("Minimum balance required and cannot be negative.");
        }
        this.interestRate = interestRate;
        this.minimumBalance = minimumBalance;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public int getMinimumBalance() {
        return minimumBalance;
    }

    //calculateMonthlyInterest() - calculates and returns monthly interest amount annual percentage
    public double calculateMonthlyInterest(){
        return getBalance() * (interestRate / 100) / 12;
    }

    //applyInterest() - adds monthly interest to balance
    public void applyInterest(){
        double monthlyInterest = calculateMonthlyInterest();
        deposit(monthlyInterest);
    }

    //Override withdraw() to ensure minimum balance is maintained
    @Override
    public boolean withdraw(double amount) {

        //already from super(!!!)
        if(amount <= 0){
            throw new IllegalArgumentException("Amount cannot be negative.");
        }

        double remainBalance = getBalance() - amount;

        if(remainBalance < minimumBalance){
            throw new IllegalArgumentException("Withdrawal would violate minimum balance.");
        }

        return super.withdraw(amount);

    }

    //helper method to validate interest rate between 0.1 and 15.0 where: double interestRate annual interest rate (as percentage)
    private void interestRateValidator (double interestRate){
        if(interestRate < 0.1 || interestRate > 15.0){
            throw new IllegalArgumentException("Interest rate must be between 0.1 and 15.0 in %");
        }
    }


}
