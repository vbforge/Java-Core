package com.vbforge;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.*;

@DisplayName("BankAccount Tests")
class BankAccountTest {

    @Test
    @DisplayName("Should create account with valid parameters")
    void shouldCreateAccountWithValidParameters() {
        BankAccount account = new BankAccount("ACC-123456", "John Doe", 1000.0);

        assertThat(account.getAccountNumber()).isEqualTo("ACC-123456");
        assertThat(account.getOwnerName()).isEqualTo("John Doe");
        assertThat(account.getBalance()).isEqualTo(1000.0);
        assertThat(account.isActive()).isTrue();
    }

    @Test
    @DisplayName("Should create account with zero balance constructor")
    void shouldCreateAccountWithZeroBalance() {
        BankAccount account = new BankAccount("ACC-123456", "Jane Doe");

        assertThat(account.getBalance()).isEqualTo(0.0);
        assertThat(account.isActive()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "ACC-12345",     // too few digits
            "ACC-1234567",   // too many digits
            "ACC-12345A",    // contains letter
            "AC-123456",     // wrong prefix
            "ACC123456",     // missing dash
            "acc-123456"     // lowercase
    })
    @DisplayName("Should reject invalid account number patterns")
    void shouldRejectInvalidAccountNumbers(String invalidAccountNumber) {
        assertThatThrownBy(() -> new BankAccount(invalidAccountNumber, "John Doe", 100.0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Account number must match pattern");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t", "\n"})
    @DisplayName("Should reject null or blank account numbers")
    void shouldRejectNullOrBlankAccountNumbers(String invalidAccountNumber) {
        assertThatThrownBy(() -> new BankAccount(invalidAccountNumber, "John Doe", 100.0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t", "\n"})
    @DisplayName("Should reject null or blank owner names")
    void shouldRejectNullOrBlankOwnerNames(String invalidOwnerName) {
        assertThatThrownBy(() -> new BankAccount("ACC-123456", invalidOwnerName, 100.0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Owner name cannot be null or empty");
    }

    @ParameterizedTest
    @ValueSource(doubles = {-0.01, -1.0, -100.0, -1000.0})
    @DisplayName("Should reject negative initial balance")
    void shouldRejectNegativeInitialBalance(double negativeBalance) {
        assertThatThrownBy(() -> new BankAccount("ACC-123456", "John Doe", negativeBalance))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Balance cannot be negative");
    }

    @ParameterizedTest
    @CsvSource({
            "100.0, 100.0, 200.0",
            "500.0, 250.5, 750.5",
            "0.0, 1000.0, 1000.0",
            "1.5, 0.5, 2.0"
    })
    @DisplayName("Should deposit amount successfully")
    void shouldDepositAmount(double initialBalance, double depositAmount, double expectedBalance) {
        BankAccount account = new BankAccount("ACC-123456", "John Doe", initialBalance);

        boolean result = account.deposit(depositAmount);

        assertThat(result).isTrue();
        assertThat(account.getBalance()).isEqualTo(expectedBalance);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, -0.01, -1.0, -100.0})
    @DisplayName("Should reject non-positive deposit amounts")
    void shouldRejectNonPositiveDeposits(double invalidAmount) {
        BankAccount account = new BankAccount("ACC-123456", "John Doe", 100.0);

        assertThatThrownBy(() -> account.deposit(invalidAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Deposit amount must be positive");
    }

    @ParameterizedTest
    @CsvSource({
            "1000.0, 500.0, 500.0",
            "1000.0, 1000.0, 0.0",
            "100.5, 50.25, 50.25",
            "1.0, 0.5, 0.5"
    })
    @DisplayName("Should withdraw amount successfully")
    void shouldWithdrawAmount(double initialBalance, double withdrawAmount, double expectedBalance) {
        BankAccount account = new BankAccount("ACC-123456", "John Doe", initialBalance);

        boolean result = account.withdraw(withdrawAmount);

        assertThat(result).isTrue();
        assertThat(account.getBalance()).isEqualTo(expectedBalance);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, -0.01, -1.0})
    @DisplayName("Should reject non-positive withdrawal amounts")
    void shouldRejectNonPositiveWithdrawals(double invalidAmount) {
        BankAccount account = new BankAccount("ACC-123456", "John Doe", 100.0);

        assertThatThrownBy(() -> account.withdraw(invalidAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Amount must be positive.");
    }

    @Test
    @DisplayName("Should reject withdrawal exceeding balance")
    void shouldRejectWithdrawalExceedingBalance() {
        BankAccount account = new BankAccount("ACC-123456", "John Doe", 100.0);

        assertThatThrownBy(() -> account.withdraw(100.01))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Amount cannot be greater than balance");
    }

    @ParameterizedTest
    @CsvSource({
            "1000.0, 500.0, 0.0, 500.0, 500.0",
            "1000.0, 1000.0, 500.0, 0.0, 1500.0",
            "250.5, 100.25, 0.0, 150.25, 100.25"
    })
    @DisplayName("Should transfer amount between accounts")
    void shouldTransferBetweenAccounts(double sourceBalance, double amount,
                                       double targetBalance, double expectedSource,
                                       double expectedTarget) {
        BankAccount source = new BankAccount("ACC-123456", "John Doe", sourceBalance);
        BankAccount target = new BankAccount("ACC-654321", "Jane Doe", targetBalance);

        source.transfer(target, amount);

        assertThat(source.getBalance()).isEqualTo(expectedSource);
        assertThat(target.getBalance()).isEqualTo(expectedTarget);
    }

    @Test
    @DisplayName("Should reject transfer to null account")
    void shouldRejectTransferToNullAccount() {
        BankAccount account = new BankAccount("ACC-123456", "John Doe", 1000.0);

        assertThatThrownBy(() -> account.transfer(null, 500.0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Target account cannot be null");
    }

    @Test
    @DisplayName("Should reject transfer to same account")
    void shouldRejectTransferToSameAccount() {
        BankAccount account = new BankAccount("ACC-123456", "John Doe", 1000.0);

        assertThatThrownBy(() -> account.transfer(account, 500.0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Cannot transfer to the same account");
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, -0.01, -100.0})
    @DisplayName("Should reject non-positive transfer amounts")
    void shouldRejectNonPositiveTransferAmounts(double invalidAmount) {
        BankAccount source = new BankAccount("ACC-123456", "John Doe", 1000.0);
        BankAccount target = new BankAccount("ACC-654321", "Jane Doe", 0.0);

        assertThatThrownBy(() -> source.transfer(target, invalidAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Transfer amount must be positive");
    }

    @Test
    @DisplayName("Should reject transfer with insufficient funds")
    void shouldRejectTransferWithInsufficientFunds() {
        BankAccount source = new BankAccount("ACC-123456", "John Doe", 100.0);
        BankAccount target = new BankAccount("ACC-654321", "Jane Doe", 0.0);

        assertThatThrownBy(() -> source.transfer(target, 100.01))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Insufficient funds");
    }

    @Test
    @DisplayName("Should deactivate account")
    void shouldDeactivateAccount() {
        BankAccount account = new BankAccount("ACC-123456", "John Doe", 1000.0);

        account.deactivateAccount();

        assertThat(account.isActive()).isFalse();
    }

    @Test
    @DisplayName("Should activate account")
    void shouldActivateAccount() {
        BankAccount account = new BankAccount("ACC-123456", "John Doe", 1000.0);
        account.deactivateAccount();

        account.activateAccount();

        assertThat(account.isActive()).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            "1000.0, '1000.00'",
            "1000.5, '1000.50'",
            "0.0, '0.00'",
            "1.234, '1.23'",
            "999.999, '1000.00'"
    })
    @DisplayName("Should format balance correctly")
    void shouldFormatBalance(double balance, String expectedFormat) {
        BankAccount account = new BankAccount("ACC-123456", "John Doe", balance);

        assertThat(account.getFormattedBalance()).isEqualTo(expectedFormat);
    }

    @SuppressWarnings("EqualsWithItself")
    @Test
    @DisplayName("Should implement equals correctly")
    void shouldImplementEqualsCorrectly() {
        BankAccount account1 = new BankAccount("ACC-123456", "John Doe", 1000.0);
        BankAccount account2 = new BankAccount("ACC-123456", "Jane Doe", 500.0);
        BankAccount account3 = new BankAccount("ACC-654321", "John Doe", 1000.0);

        assertThat(account1).isEqualTo(account2); // Same account number
        assertThat(account1).isNotEqualTo(account3); // Different account number
        assertThat(account1).isEqualTo(account1); // Reflexive ( x.equals(x) must return true )
        assertThat(account1.equals(account1)).isTrue(); //or we can perform Reflexive ( x.equals(x) must return true ) like this; no need @SuppressWarnings("EqualsWithItself")
        assertThat(account1).isNotEqualTo(null); // Not equal to null
        assertThat(account1).isNotEqualTo("ACC-123456");      // Not equal to different type
        assertThat(account1).isNotEqualTo(123);               // Not equal to different type
        assertThat(account1).isNotEqualTo(new Object());            // Not equal to different type
    }

    @Test
    @DisplayName("Should implement hashCode correctly")
    void shouldImplementHashCodeCorrectly() {
        BankAccount account1 = new BankAccount("ACC-123456", "John Doe", 1000.0);
        BankAccount account2 = new BankAccount("ACC-123456", "Jane Doe", 500.0);

        assertThat(account1.hashCode()).isEqualTo(account2.hashCode());
    }

    @Test
    @DisplayName("Should implement toString correctly")
    void shouldImplementToStringCorrectly() {
        BankAccount account = new BankAccount("ACC-123456", "John Doe", 1000.0);

        String result = account.toString();

        assertThat(result)
                .contains("ACC-123456")
                .contains("John Doe")
                .contains("1000.0")
                .contains("true");
    }

}