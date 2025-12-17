# Bank Account System (Middle Level)

**Focus:** Inheritance, more complex business logic, validation patterns  
**Key concepts:** Class inheritance, method overriding, formatted output, complex validation  
**Estimated time:** 45 minutes

#### This task designed to give practice with:

- Class design and relationships
- Inheritance and method overriding
- Input validation strategies
- Business logic implementation
- Error handling patterns

## Description

Create a banking system with different types of accounts and transaction management.

### Task Requirements:

#### Develop `BankAccount` class with the following content:

- **Private fields:**
    - `String accountNumber` - unique account identifier
    - `String ownerName` - account owner's name
    - `double balance` - current account balance
    - `boolean isActive` - account status

- **Constructors:**
    - Constructor with accountNumber, ownerName, and initial balance
    - Constructor with accountNumber and ownerName (sets balance to 0.0)
    - Should throw `IllegalArgumentException` if:
        - accountNumber is null, empty, or doesn't match pattern "ACC-XXXXXX" (where X is digit)
        - ownerName is null or empty
        - initial balance is negative

- **Methods:**
    - Getter methods for all fields
    - `deposit(double amount)` - adds money to balance, returns true if successful
    - `withdraw(double amount)` - removes money from balance, returns true if sufficient funds
    - `transfer(BankAccount targetAccount, double amount)` - transfers money between accounts
    - `deactivateAccount()` - sets account as inactive
    - `activateAccount()` - sets account as active
    - `getFormattedBalance()` - returns balance formatted as currency string
    - Override `equals()`, `hashCode()`, and `toString()` methods

#### Develop `SavingsAccount` class that extends `BankAccount`:

- **Additional private fields:**
    - `double interestRate` - annual interest rate (as percentage)
    - `int minimumBalance` - minimum required balance

- **Constructor:**
    - Constructor with all parameters including interestRate and minimumBalance
    - Should validate that interestRate is between 0.1 and 15.0
    - Should validate that minimumBalance is non-negative

- **Additional methods:**
    - `calculateMonthlyInterest()` - calculates and returns monthly interest amount
    - `applyInterest()` - adds monthly interest to balance
    - Override `withdraw()` to ensure minimum balance is maintained

#### Develop `Bank` class with the following content:

- **Private field:**
    - `BankAccount[] accounts` - array to store all bank accounts

- **Constructor:**
    - Constructor that creates empty array with specified capacity

- **Methods:**
    - `addAccount(BankAccount account)` - adds account to bank
    - `findAccount(String accountNumber)` - returns account or null
    - `getTotalBankBalance()` - returns sum of all account balances
    - `getAccountsByOwner(String ownerName)` - returns array of accounts for given owner
    - `processMonthlyInterest()` - applies interest to all savings accounts
    - `getActiveAccountsCount()` - returns number of active accounts
    - `generateAccountReport()` - returns formatted string with all account information
