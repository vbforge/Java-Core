package com.vbforge;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Bank Tests")
class BankTest {

    private Bank bank;
    private BankAccount account1;
    private BankAccount account2;
    private SavingsAccount savingsAccount1;
    private SavingsAccount savingsAccount2;

    @BeforeEach
    void setUp() {
        bank = new Bank(10);
        account1 = new BankAccount("ACC-123456", "John Doe", 1000.0);
        account2 = new BankAccount("ACC-654321", "Jane Doe", 2000.0);
        savingsAccount1 = new SavingsAccount("ACC-111111", "Bob Smith", 5000.0, 5.0, 500);
        savingsAccount2 = new SavingsAccount("ACC-222222", "Alice Johnson", 3000.0, 7.5, 1000);
    }

    @Test
    @DisplayName("Should create bank with specified capacity")
    void shouldCreateBankWithCapacity() {
        Bank newBank = new Bank(5);
        assertThat(newBank).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -10, -100})
    @DisplayName("Should reject non-positive capacity")
    void shouldRejectNonPositiveCapacity(int invalidCapacity) {
        assertThatThrownBy(() -> new Bank(invalidCapacity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Bank capacity must be greater than 0");
    }

    @Test
    @DisplayName("Should add account successfully")
    void shouldAddAccountSuccessfully() {
        boolean result = bank.addAccount(account1);

        assertThat(result).isTrue();
        assertThat(bank.findAccount("ACC-123456")).isEqualTo(account1);
    }

    @Test
    @DisplayName("Should add multiple accounts")
    void shouldAddMultipleAccounts() {
        assertThat(bank.addAccount(account1)).isTrue();
        assertThat(bank.addAccount(account2)).isTrue();
        assertThat(bank.addAccount(savingsAccount1)).isTrue();

        assertThat(bank.findAccount("ACC-123456")).isEqualTo(account1);
        assertThat(bank.findAccount("ACC-654321")).isEqualTo(account2);
        assertThat(bank.findAccount("ACC-111111")).isEqualTo(savingsAccount1);
    }

    @Test
    @DisplayName("Should reject null account")
    void shouldRejectNullAccount() {
        assertThatThrownBy(() -> bank.addAccount(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Bank account must not be null");
    }

    @Test
    @DisplayName("Should reject duplicate accounts")
    void shouldRejectDuplicateAccounts() {
        bank.addAccount(account1);

        BankAccount duplicate = new BankAccount("ACC-123456", "Different Owner", 5000.0);
        boolean result = bank.addAccount(duplicate);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Should reject adding account when bank is full")
    void shouldRejectWhenBankIsFull() {
        Bank smallBank = new Bank(2);

        assertThat(smallBank.addAccount(account1)).isTrue();
        assertThat(smallBank.addAccount(account2)).isTrue();
        assertThat(smallBank.addAccount(savingsAccount1)).isFalse();
    }

    @Test
    @DisplayName("Should find existing account by account number")
    void shouldFindExistingAccount() {
        bank.addAccount(account1);
        bank.addAccount(account2);

        BankAccount found = bank.findAccount("ACC-123456");

        assertThat(found).isEqualTo(account1);
        assertThat(found.getOwnerName()).isEqualTo("John Doe");
    }

    @Test
    @DisplayName("Should return null for non-existing account")
    void shouldReturnNullForNonExistingAccount() {
        bank.addAccount(account1);

        BankAccount found = bank.findAccount("ACC-999999");

        assertThat(found).isNull();
    }

    @Test
    @DisplayName("Should reject null account number in find")
    void shouldRejectNullAccountNumberInFind() {
        assertThatThrownBy(() -> bank.findAccount(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Account number cannot be null");
    }

    @Test
    @DisplayName("Should calculate total bank balance")
    void shouldCalculateTotalBankBalance() {
        bank.addAccount(account1);        // 1000.0
        bank.addAccount(account2);        // 2000.0
        bank.addAccount(savingsAccount1); // 5000.0

        double total = bank.getTotalBankBalance();

        assertThat(total).isEqualTo(8000.0);
    }

    @Test
    @DisplayName("Should return zero for empty bank")
    void shouldReturnZeroForEmptyBank() {
        double total = bank.getTotalBankBalance();

        assertThat(total).isEqualTo(0.0);
    }

    @Test
    @DisplayName("Should update total balance after transactions")
    void shouldUpdateTotalBalanceAfterTransactions() {
        bank.addAccount(account1);
        bank.addAccount(account2);

        double initialTotal = bank.getTotalBankBalance();

        account1.deposit(500.0);
        account2.withdraw(300.0);

        double newTotal = bank.getTotalBankBalance();

        assertThat(newTotal).isEqualTo(initialTotal + 500.0 - 300.0);
    }

    @Test
    @DisplayName("Should get accounts by owner name")
    void shouldGetAccountsByOwner() {
        BankAccount account3 = new BankAccount("ACC-333333", "John Doe", 500.0);

        bank.addAccount(account1);  // John Doe
        bank.addAccount(account2);  // Jane Doe
        bank.addAccount(account3);  // John Doe

        BankAccount[] johnAccounts = bank.getAccountsByOwner("John Doe");

        assertThat(johnAccounts).hasSize(2);
        assertThat(johnAccounts).containsExactlyInAnyOrder(account1, account3);
    }

    @Test
    @DisplayName("Should return empty array for owner with no accounts")
    void shouldReturnEmptyArrayForOwnerWithNoAccounts() {
        bank.addAccount(account1);

        BankAccount[] accounts = bank.getAccountsByOwner("Unknown Person");

        assertThat(accounts).isEmpty();
    }

    @Test
    @DisplayName("Should return empty array for null owner name")
    void shouldReturnEmptyArrayForNullOwnerName() {
        bank.addAccount(account1);

        BankAccount[] accounts = bank.getAccountsByOwner(null);

        assertThat(accounts).isEmpty();
    }

    @Test
    @DisplayName("Should return empty array for blank owner name")
    void shouldReturnEmptyArrayForBlankOwnerName() {
        bank.addAccount(account1);

        BankAccount[] accounts = bank.getAccountsByOwner("   ");

        assertThat(accounts).isEmpty();
    }

    @Test
    @DisplayName("Should find accounts with exact owner name match")
    void shouldFindAccountsWithExactMatch() {
        BankAccount johnAccount = new BankAccount("ACC-333333", "John", 500.0);
        BankAccount johnDoeAccount = new BankAccount("ACC-444444", "John Doe", 600.0);

        bank.addAccount(account1);      // John Doe
        bank.addAccount(johnAccount);   // John
        bank.addAccount(johnDoeAccount); // John Doe

        BankAccount[] johnDoeAccounts = bank.getAccountsByOwner("John Doe");

        assertThat(johnDoeAccounts).hasSize(2);
        assertThat(johnDoeAccounts).containsExactlyInAnyOrder(account1, johnDoeAccount);
    }

    @Test
    @DisplayName("Should process monthly interest for all savings accounts")
    void shouldProcessMonthlyInterestForAllSavings() {
        bank.addAccount(account1);        // Regular account
        bank.addAccount(savingsAccount1); // Savings with 5% rate
        bank.addAccount(savingsAccount2); // Savings with 7.5% rate

        double initialBalance1 = savingsAccount1.getBalance();
        double initialBalance2 = savingsAccount2.getBalance();

        bank.processMonthlyInterest();

        // Verify interest was applied to savings accounts
        assertThat(savingsAccount1.getBalance()).isGreaterThan(initialBalance1);
        assertThat(savingsAccount2.getBalance()).isGreaterThan(initialBalance2);

        // Verify regular account unchanged
        assertThat(account1.getBalance()).isEqualTo(1000.0);
    }

    @Test
    @DisplayName("Should not affect regular accounts during interest processing")
    void shouldNotAffectRegularAccountsDuringInterestProcessing() {
        bank.addAccount(account1);
        bank.addAccount(account2);

        double initialBalance1 = account1.getBalance();
        double initialBalance2 = account2.getBalance();

        bank.processMonthlyInterest();

        assertThat(account1.getBalance()).isEqualTo(initialBalance1);
        assertThat(account2.getBalance()).isEqualTo(initialBalance2);
    }

    @Test
    @DisplayName("Should handle empty bank during interest processing")
    void shouldHandleEmptyBankDuringInterestProcessing() {
        assertThatNoException().isThrownBy(() -> bank.processMonthlyInterest());
    }

    @Test
    @DisplayName("Should calculate correct interest amounts")
    void shouldCalculateCorrectInterestAmounts() {
        bank.addAccount(savingsAccount1); // 5000 at 5% = 20.83 per month

        double expectedInterest = savingsAccount1.calculateMonthlyInterest();
        double balanceBefore = savingsAccount1.getBalance();

        bank.processMonthlyInterest();

        assertThat(savingsAccount1.getBalance()).isCloseTo(
                balanceBefore + expectedInterest, within(0.01)
        );
    }

    @Test
    @DisplayName("Should count active accounts correctly")
    void shouldCountActiveAccountsCorrectly() {
        bank.addAccount(account1);
        bank.addAccount(account2);
        bank.addAccount(savingsAccount1);

        int activeCount = bank.getActiveAccountsCount();

        assertThat(activeCount).isEqualTo(3);
    }

    @Test
    @DisplayName("Should update count when accounts are deactivated")
    void shouldUpdateCountWhenAccountsDeactivated() {
        bank.addAccount(account1);
        bank.addAccount(account2);
        bank.addAccount(savingsAccount1);

        account1.deactivateAccount();
        savingsAccount1.deactivateAccount();

        int activeCount = bank.getActiveAccountsCount();

        assertThat(activeCount).isEqualTo(1);
    }

    @Test
    @DisplayName("Should return zero for empty bank active count")
    void shouldReturnZeroForEmptyBankActiveCount() {
        int activeCount = bank.getActiveAccountsCount();

        assertThat(activeCount).isEqualTo(0);
    }

    @Test
    @DisplayName("Should recount when accounts are reactivated")
    void shouldRecountWhenAccountsReactivated() {
        bank.addAccount(account1);
        bank.addAccount(account2);

        account1.deactivateAccount();
        assertThat(bank.getActiveAccountsCount()).isEqualTo(1);

        account1.activateAccount();
        assertThat(bank.getActiveAccountsCount()).isEqualTo(2);
    }

    @Test
    @DisplayName("Should generate account report with all information")
    void shouldGenerateAccountReportWithAllInformation() {
        bank.addAccount(account1);
        bank.addAccount(account2);
        bank.addAccount(savingsAccount1);

        String report = bank.generateAccountReport();

        assertThat(report)
                .contains("=== Bank Account Report ===")
                .contains("ACC-123456")
                .contains("ACC-654321")
                .contains("ACC-111111")
                .contains("John Doe")
                .contains("Jane Doe")
                .contains("Bob Smith")
                .contains("Total accounts: 3")
                .contains("Active accounts: 3")
                .contains("Total bank balance: 8000.00");
    }

    @Test
    @DisplayName("Should generate report for empty bank")
    void shouldGenerateReportForEmptyBank() {
        String report = bank.generateAccountReport();

        assertThat(report)
                .contains("=== Bank Account Report ===")
                .contains("Total accounts: 0")
                .contains("Active accounts: 0")
                .contains("Total bank balance: 0.00");
    }

    @Test
    @DisplayName("Should show correct active count in report")
    void shouldShowCorrectActiveCountInReport() {
        bank.addAccount(account1);
        bank.addAccount(account2);

        account1.deactivateAccount();

        String report = bank.generateAccountReport();

        assertThat(report)
                .contains("Total accounts: 2")
                .contains("Active accounts: 1");
    }

    @Test
    @DisplayName("Should format balance correctly in report")
    void shouldFormatBalanceCorrectlyInReport() {
        BankAccount account = new BankAccount("ACC-999999", "Test User", 1234.567);
        bank.addAccount(account);

        String report = bank.generateAccountReport();

        assertThat(report).contains("1234.57");
    }

    @Test
    @DisplayName("Should update report after transactions")
    void shouldUpdateReportAfterTransactions() {
        bank.addAccount(account1);
        bank.addAccount(account2);

        account1.deposit(500.0);
        account2.withdraw(300.0);

        String report = bank.generateAccountReport();

        assertThat(report).contains("Total bank balance: 3200.00");
    }

    @Test
    @DisplayName("Should handle large number of accounts")
    void shouldHandleLargeNumberOfAccounts() {
        Bank largeBank = new Bank(100);

        for (int i = 1; i <= 50; i++) {
            BankAccount account = new BankAccount(
                    String.format("ACC-%06d", i),
                    "Owner " + i,
                    i * 100.0
            );
            assertThat(largeBank.addAccount(account)).isTrue();
        }

        assertThat(largeBank.getTotalBankBalance()).isEqualTo(127500.0); // Sum of 100+200+...+5000
        assertThat(largeBank.getActiveAccountsCount()).isEqualTo(50);
    }

    @Test
    @DisplayName("Should maintain account references correctly")
    void shouldMaintainAccountReferencesCorrectly() {
        bank.addAccount(account1);

        BankAccount found = bank.findAccount("ACC-123456");
        found.deposit(500.0);

        // Changes should be reflected in original account
        assertThat(account1.getBalance()).isEqualTo(1500.0);
    }

    @Test
    @DisplayName("Should handle mixed account types in all operations")
    void shouldHandleMixedAccountTypesInAllOperations() {
        bank.addAccount(account1);
        bank.addAccount(savingsAccount1);

        // Test total balance
        assertThat(bank.getTotalBankBalance()).isEqualTo(6000.0);

        // Test interest processing (only affects savings)
        double savingsBalanceBefore = savingsAccount1.getBalance();
        bank.processMonthlyInterest();
        assertThat(savingsAccount1.getBalance()).isGreaterThan(savingsBalanceBefore);
        assertThat(account1.getBalance()).isEqualTo(1000.0);

        // Test active count
        assertThat(bank.getActiveAccountsCount()).isEqualTo(2);
    }

}