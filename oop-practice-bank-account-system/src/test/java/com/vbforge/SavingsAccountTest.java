package com.vbforge;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

@DisplayName("SavingsAccount Tests")
class SavingsAccountTest {

    @Test
    @DisplayName("Should create savings account with valid parameters")
    void shouldCreateSavingsAccountWithValidParameters() {
        SavingsAccount account = new SavingsAccount(
                "ACC-123456", "John Doe", 1000.0, 5.0, 100
        );

        assertThat(account.getAccountNumber()).isEqualTo("ACC-123456");
        assertThat(account.getOwnerName()).isEqualTo("John Doe");
        assertThat(account.getBalance()).isEqualTo(1000.0);
        assertThat(account.getInterestRate()).isEqualTo(5.0);
        assertThat(account.getMinimumBalance()).isEqualTo(100);
        assertThat(account.isActive()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, 0.09, -0.1, -1.0, 15.1, 20.0, 100.0})
    @DisplayName("Should reject invalid interest rates")
    void shouldRejectInvalidInterestRates(double invalidRate) {
        assertThatThrownBy(() -> new SavingsAccount(
                "ACC-123456", "John Doe", 1000.0, invalidRate, 100
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Interest rate must be between 0.1 and 15.0");
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 1.0, 5.0, 10.0, 15.0})
    @DisplayName("Should accept valid interest rates")
    void shouldAcceptValidInterestRates(double validRate) {
        assertThatNoException().isThrownBy(() -> new SavingsAccount(
                "ACC-123456", "John Doe", 1000.0, validRate, 100
        ));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -10, -100})
    @DisplayName("Should reject negative minimum balance")
    void shouldRejectNegativeMinimumBalance(int negativeMinBalance) {
        assertThatThrownBy(() -> new SavingsAccount(
                "ACC-123456", "John Doe", 1000.0, 5.0, negativeMinBalance
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Minimum balance required and cannot be negative");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 100, 500, 1000})
    @DisplayName("Should accept valid minimum balance")
    void shouldAcceptValidMinimumBalance(int validMinBalance) {
        assertThatNoException().isThrownBy(() -> new SavingsAccount(
                "ACC-123456", "John Doe", 1000.0, 5.0, validMinBalance
        ));
    }

    @ParameterizedTest
    @CsvSource({
            "1000.0, 12.0, 10.0",      // 1000 * 12% / 12 months
            "2000.0, 6.0, 10.0",       // 2000 * 6% / 12 months
            "500.0, 3.6, 1.5",         // 500 * 3.6% / 12 months
            "10000.0, 1.2, 10.0",      // 10000 * 1.2% / 12 months
            "5000.0, 15.0, 62.5"       // 5000 * 15% / 12 months
    })
    @DisplayName("Should calculate monthly interest correctly")
    void shouldCalculateMonthlyInterest(double balance, double interestRate,
                                        double expectedMonthlyInterest) {
        SavingsAccount account = new SavingsAccount(
                "ACC-123456", "John Doe", balance, interestRate, 0
        );

        double monthlyInterest = account.calculateMonthlyInterest();

        assertThat(monthlyInterest).isCloseTo(expectedMonthlyInterest, within(0.01));
    }

    @Test
    @DisplayName("Should apply interest to balance")
    void shouldApplyInterest() {
        SavingsAccount account = new SavingsAccount(
                "ACC-123456", "John Doe", 1000.0, 12.0, 0
        );

        double initialBalance = account.getBalance();
        double expectedInterest = account.calculateMonthlyInterest();

        account.applyInterest();

        assertThat(account.getBalance()).isCloseTo(
                initialBalance + expectedInterest, within(0.01)
        );
    }

    @Test
    @DisplayName("Should apply interest multiple times correctly")
    void shouldApplyInterestMultipleTimes() {
        SavingsAccount account = new SavingsAccount(
                "ACC-123456", "John Doe", 1000.0, 12.0, 0
        );

        // Apply interest for 3 months
        account.applyInterest(); // Month 1
        account.applyInterest(); // Month 2
        account.applyInterest(); // Month 3

        // After 3 months with compound interest
        // Month 1: 1000 + 10 = 1010
        // Month 2: 1010 + 10.10 = 1020.10
        // Month 3: 1020.10 + 10.201 = 1030.301
        assertThat(account.getBalance()).isCloseTo(1030.30, within(0.01));
    }

    @ParameterizedTest
    @CsvSource({
            "1000.0, 100, 500.0, 500.0",   // Withdraw within limits
            "1000.0, 100, 900.0, 100.0",   // Withdraw to minimum balance
            "500.0, 0, 500.0, 0.0",        // Withdraw all when min is 0
            "1000.0, 250, 750.0, 250.0"    // Withdraw exactly to minimum
    })
    @DisplayName("Should allow withdrawal maintaining minimum balance")
    void shouldAllowWithdrawalMaintainingMinimumBalance(double balance, int minBalance,
                                                        double withdrawAmount,
                                                        double expectedBalance) {
        SavingsAccount account = new SavingsAccount(
                "ACC-123456", "John Doe", balance, 5.0, minBalance
        );

        boolean result = account.withdraw(withdrawAmount);

        assertThat(result).isTrue();
        assertThat(account.getBalance()).isEqualTo(expectedBalance);
    }

    @ParameterizedTest
    @CsvSource({
            "1000.0, 100, 900.01",  // Would go below minimum
            "1000.0, 100, 950.0",   // Would go below minimum
            "500.0, 100, 401.0",    // Would go below minimum
            "1000.0, 500, 501.0"    // Would go below minimum
    })
    @DisplayName("Should reject withdrawal violating minimum balance")
    void shouldRejectWithdrawalViolatingMinimumBalance(double balance, int minBalance,
                                                       double withdrawAmount) {
        SavingsAccount account = new SavingsAccount(
                "ACC-123456", "John Doe", balance, 5.0, minBalance
        );

        assertThatThrownBy(() -> account.withdraw(withdrawAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Withdrawal would violate minimum balance");
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, -0.01, -100.0})
    @DisplayName("Should reject non-positive withdrawal amounts")
    void shouldRejectNonPositiveWithdrawalAmounts(double invalidAmount) {
        SavingsAccount account = new SavingsAccount(
                "ACC-123456", "John Doe", 1000.0, 5.0, 100
        );

        assertThatThrownBy(() -> account.withdraw(invalidAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Amount cannot be negative");
    }

    @Test
    @DisplayName("Should inherit deposit functionality from BankAccount")
    void shouldInheritDepositFunctionality() {
        SavingsAccount account = new SavingsAccount(
                "ACC-123456", "John Doe", 1000.0, 5.0, 100
        );

        boolean result = account.deposit(500.0);

        assertThat(result).isTrue();
        assertThat(account.getBalance()).isEqualTo(1500.0);
    }

    @Test
    @DisplayName("Should inherit transfer functionality from BankAccount")
    void shouldInheritTransferFunctionality() {
        SavingsAccount source = new SavingsAccount(
                "ACC-123456", "John Doe", 1000.0, 5.0, 100
        );
        BankAccount target = new BankAccount("ACC-654321", "Jane Doe", 500.0);

        source.transfer(target, 400.0);

        assertThat(source.getBalance()).isEqualTo(600.0);
        assertThat(target.getBalance()).isEqualTo(900.0);
    }

    @Test
    @DisplayName("Should respect minimum balance during transfer")
    void shouldRespectMinimumBalanceDuringTransfer() {
        SavingsAccount source = new SavingsAccount(
                "ACC-123456", "John Doe", 1000.0, 5.0, 100
        );
        BankAccount target = new BankAccount("ACC-654321", "Jane Doe", 500.0);

        // This should work: 1000 - 900 = 100 (exactly at minimum)
        assertThatNoException().isThrownBy(() -> source.transfer(target, 900.0));

        // Verify the transfer actually happened
        assertThat(source.getBalance()).isEqualTo(100.0);
        assertThat(target.getBalance()).isEqualTo(1400.0);
    }

    @Test
    @DisplayName("Should reject transfer that violates minimum balance")
    void shouldRejectTransferViolatingMinimumBalance() {
        SavingsAccount source = new SavingsAccount(
                "ACC-123456", "John Doe", 1000.0, 5.0, 100
        );
        BankAccount target = new BankAccount("ACC-654321", "Jane Doe", 500.0);

        // This should fail: 1000 - 900.01 = 99.99 (below minimum)
        assertThatThrownBy(() -> source.transfer(target, 900.01))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Withdrawal would violate minimum balance");

        // Verify balances unchanged after failed transfer
        assertThat(source.getBalance()).isEqualTo(1000.0);
        assertThat(target.getBalance()).isEqualTo(500.0);
    }

    @Test
    @DisplayName("Should inherit account activation/deactivation")
    void shouldInheritAccountActivationDeactivation() {
        SavingsAccount account = new SavingsAccount(
                "ACC-123456", "John Doe", 1000.0, 5.0, 100
        );

        assertThat(account.isActive()).isTrue();

        account.deactivateAccount();
        assertThat(account.isActive()).isFalse();

        account.activateAccount();
        assertThat(account.isActive()).isTrue();
    }

    @Test
    @DisplayName("Should inherit formatted balance functionality")
    void shouldInheritFormattedBalance() {
        SavingsAccount account = new SavingsAccount(
                "ACC-123456", "John Doe", 1234.567, 5.0, 100
        );

        assertThat(account.getFormattedBalance()).isEqualTo("1234.57");
    }

    @Test
    @DisplayName("Should use parent equals and hashCode")
    void shouldUseParentEqualsAndHashCode() {
        SavingsAccount account1 = new SavingsAccount(
                "ACC-123456", "John Doe", 1000.0, 5.0, 100
        );
        SavingsAccount account2 = new SavingsAccount(
                "ACC-123456", "Jane Doe", 500.0, 10.0, 200
        );
        BankAccount account3 = new BankAccount("ACC-123456", "Bob", 2000.0);

        // Same account number means equal
        assertThat(account1).isEqualTo(account2);
        assertThat(account1).isEqualTo(account3);
        assertThat(account1.hashCode()).isEqualTo(account2.hashCode());
        assertThat(account1.hashCode()).isEqualTo(account3.hashCode());
    }

    @Test
    @DisplayName("Should validate all parent constructor validations")
    void shouldValidateParentConstructorValidations() {
        // Invalid account number
        assertThatThrownBy(() -> new SavingsAccount(
                "INVALID", "John Doe", 1000.0, 5.0, 100
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Account number must match pattern");

        // Invalid owner name
        assertThatThrownBy(() -> new SavingsAccount(
                "ACC-123456", "", 1000.0, 5.0, 100
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Owner name cannot be null or empty");

        // Negative balance
        assertThatThrownBy(() -> new SavingsAccount(
                "ACC-123456", "John Doe", -1.0, 5.0, 100
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Balance cannot be negative");
    }

}