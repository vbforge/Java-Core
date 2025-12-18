package com.vbforge;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibraryMemberCoreTest {

    private static final String VALID_EMAIL = "john.doe@test.com";

    // ---------- Constructor ----------

    @Test
    void constructor_validInput_createsActiveMemberWithEmptyBorrowedBooks() {
        // Arrange & Act
        LibraryMember member = new LibraryMember("M-001", "John Doe", VALID_EMAIL);

        // Assert
        assertAll(
                () -> assertEquals("M-001", member.getMemberId()),
                () -> assertEquals("John Doe", member.getName()),
                () -> assertEquals(VALID_EMAIL, member.getEmail()),
                () -> assertTrue(member.isActive()),
                () -> assertEquals(0, member.getBorrowedBookCount())
        );
    }

    @Test
    void constructor_invalidEmail_throwsException() {
        // Arrange / Act / Assert
        assertThrows(IllegalArgumentException.class,
                () -> new LibraryMember("M-002", "Jane Doe", "invalidEmail"));
    }

    // ---------- addBorrowedBook ----------

    @Test
    void addBorrowedBook_activeMember_addsBookSuccessfully() {
        // Arrange
        LibraryMember member = new LibraryMember("M-003", "Alex", VALID_EMAIL);

        // Act
        member.addBorrowedBook("ISBN-1");

        // Assert
        assertAll(
                () -> assertEquals(1, member.getBorrowedBookCount()),
                () -> assertTrue(member.hasBorrowedBook("ISBN-1"))
        );
    }

    @Test
    void addBorrowedBook_whenCapacityExceeded_throwsException() {
        // Arrange
        LibraryMember member = new LibraryMember("M-004", "Max", VALID_EMAIL);
        for (int i = 0; i < LibraryMember.BORROWED_BOOKS_INIT_CAPACITY; i++) {
            member.addBorrowedBook("ISBN-" + i);
        }

        // Act & Assert
        assertThrows(IllegalStateException.class,
                () -> member.addBorrowedBook("ISBN-OVER"));
    }

    @Test
    void addBorrowedBook_inactiveMember_throwsException() {
        // Arrange
        LibraryMember member = new LibraryMember("M-005", "Inactive", VALID_EMAIL);
        // simulate package-level deactivation
        member.getClass(); // no-op to emphasize same-package access
        member.isActive(); // state check
        // direct field access not allowed, but assume library controls this

        // Act & Assert
        member.setActive(false); // same package
        assertThrows(IllegalStateException.class,
                () -> member.addBorrowedBook("ISBN-1"));
    }

    // ---------- removeBorrowedBook ----------

    @Test
    void removeBorrowedBook_existingBook_removesSuccessfully() {
        // Arrange
        LibraryMember member = new LibraryMember("M-006", "Eva", VALID_EMAIL);
        member.addBorrowedBook("ISBN-REMOVE");

        // Act
        member.removeBorrowedBook("ISBN-REMOVE");

        // Assert
        assertAll(
                () -> assertEquals(0, member.getBorrowedBookCount()),
                () -> assertFalse(member.hasBorrowedBook("ISBN-REMOVE"))
        );
    }

    @Test
    void removeBorrowedBook_nonExistingBook_throwsException() {
        // Arrange
        LibraryMember member = new LibraryMember("M-007", "Tom", VALID_EMAIL);

        // Act & Assert
        assertThrows(IllegalStateException.class,
                () -> member.removeBorrowedBook("ISBN-NOT-FOUND"));
    }

    // ---------- hasBorrowedBook ----------

    @Test
    void hasBorrowedBook_invalidIsbn_throwsException() {
        // Arrange
        LibraryMember member = new LibraryMember("M-008", "Kate", VALID_EMAIL);

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> member.hasBorrowedBook(" "));
    }

    // ---------- equals & hashCode ----------

    @Test
    void equals_sameMemberId_returnsTrue() {
        // Arrange
        LibraryMember m1 = new LibraryMember("M-009", "Alice", VALID_EMAIL);
        LibraryMember m2 = new LibraryMember("M-009", "Bob", "other@test.com");

        // Act & Assert
        assertEquals(m1, m2);
        assertEquals(m1.hashCode(), m2.hashCode());
    }

    @Test
    void equals_differentMemberId_returnsFalse() {
        // Arrange
        LibraryMember m1 = new LibraryMember("M-010", "A", VALID_EMAIL);
        LibraryMember m2 = new LibraryMember("M-011", "A", VALID_EMAIL);

        // Act & Assert
        assertNotEquals(m1, m2);
    }

}