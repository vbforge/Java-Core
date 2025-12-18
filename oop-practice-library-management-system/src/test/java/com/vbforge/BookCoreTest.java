package com.vbforge;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookCoreTest {

    private Author createAuthor() {
        return new Author("George", "Orwell", "British", 1903);
    }

    private Book createValidBook() {
        return new Book(
                "978-1-23-123456-7",
                "1984",
                createAuthor(),
                1949,
                "Dystopian"
        );
    }

    // ---------- Constructor validation ----------

    @Test
    @DisplayName("Constructor should create Book with valid data")
    void constructor_validData_createsBook() {
        // Arrange & Act
        Book book = createValidBook();

        // Assert
        assertAll(
                () -> assertEquals("978-1-23-123456-7", book.getIsbn()),
                () -> assertEquals("1984", book.getTitle()),
                () -> assertEquals("Dystopian", book.getGenre()),
                () -> assertEquals(1949, book.getPublicationYear()),
                () -> assertNotNull(book.getAuthor()),
                () -> assertTrue(book.isAvailable()),
                () -> assertNull(book.getCurrentBorrower())
        );
    }

    @Test
    @DisplayName("Constructor should accept ISBN with leading/trailing whitespace")
    void constructor_isbnWithWhitespace_trimmedAndAccepted() {
        // Arrange
        Author author = createAuthor();
        String isbnWithSpaces = "  978-1-23-123456-7  ";

        // Act
        Book book = new Book(isbnWithSpaces, "1984", author, 1949, "Dystopian");

        // Assert
        assertEquals("978-1-23-123456-7", book.getIsbn()); // No spaces!
    }

    @Test
    @DisplayName("Constructor should throw exception when ISBN is null")
    void constructor_nullIsbn_throwsException() {
        // Arrange
        Author author = createAuthor();

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Book(null, "1984", author, 1949, "Dystopian"));
    }

    @Test
    @DisplayName("Constructor should throw exception for invalid ISBN format")
    void constructor_invalidIsbnFormat_throwsException() {
        // Arrange
        Author author = createAuthor();

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Book("123-INVALID", "1984", author, 1949, "Dystopian"));
    }

    @Test
    @DisplayName("Constructor should throw exception when title is blank")
    void constructor_blankTitle_throwsException() {
        // Arrange
        Author author = createAuthor();

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Book("978-1-23-123456-7", "   ", author, 1949, "Dystopian"));
    }

    @Test
    @DisplayName("Constructor should throw exception when author is null")
    void constructor_nullAuthor_throwsException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Book("978-1-23-123456-7", "1984", null, 1949, "Dystopian"));
    }

    @Test
    @DisplayName("Constructor should throw exception when publication year is in the future")
    void constructor_futurePublicationYear_throwsException() {
        // Arrange
        Author author = createAuthor();

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Book("978-1-23-123456-7", "1984", author, 3000, "Dystopian"));
    }

    @Test
    @DisplayName("Constructor should throw exception when genre is blank")
    void constructor_blankGenre_throwsException() {
        // Arrange
        Author author = createAuthor();

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Book("978-1-23-123456-7", "1984", author, 1949, " "));
    }

    // ---------- Borrowing logic ----------

    @Test
    @DisplayName("Borrowing available book should succeed")
    void borrowBook_availableBook_returnsTrue() {
        // Arrange
        Book book = createValidBook();

        // Act
        boolean result = book.borrowBook("MEMBER-1");

        // Assert
        assertAll(
                () -> assertTrue(result),
                () -> assertFalse(book.isAvailable()),
                () -> assertEquals("MEMBER-1", book.getCurrentBorrower())
        );
    }

    @Test
    @DisplayName("Borrowing unavailable book should fail")
    void borrowBook_unavailableBook_returnsFalse() {
        // Arrange
        Book book = createValidBook();
        book.borrowBook("MEMBER-1");

        // Act
        boolean result = book.borrowBook("MEMBER-2");

        // Assert
        assertFalse(result);
    }

    @Test
    @DisplayName("Borrowing with invalid borrower ID should throw exception")
    void borrowBook_invalidBorrower_throwsException() {
        // Arrange
        Book book = createValidBook();

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> book.borrowBook(" "));
    }

    // ---------- Return logic ----------

    @Test
    @DisplayName("Returning borrowed book should succeed")
    void returnBook_borrowedBook_returnsTrue() {
        // Arrange
        Book book = createValidBook();
        book.borrowBook("MEMBER-1");

        // Act
        boolean result = book.returnBook();

        // Assert
        assertAll(
                () -> assertTrue(result),
                () -> assertTrue(book.isAvailable()),
                () -> assertNull(book.getCurrentBorrower())
        );
    }

    @Test
    @DisplayName("Returning available book should fail")
    void returnBook_availableBook_returnsFalse() {
        // Arrange
        Book book = createValidBook();

        // Act
        boolean result = book.returnBook();

        // Assert
        assertFalse(result);
    }

    // ---------- Business logic ----------

    @Test
    @DisplayName("isOverdue should return true when days borrowed exceed max days")
    void isOverdue_daysExceeded_returnsTrue() {
        // Arrange
        Book book = createValidBook();

        // Act & Assert
        assertTrue(book.isOverdue(15, 14));
    }

    @Test
    @DisplayName("isOverdue should return false when days borrowed within limit")
    void isOverdue_withinLimit_returnsFalse() {
        // Arrange
        Book book = createValidBook();

        // Act & Assert
        assertFalse(book.isOverdue(10, 14));
    }

    @Test
    @DisplayName("isOverdue should throw exception for negative values")
    void isOverdue_negativeValues_throwsException() {
        // Arrange
        Book book = createValidBook();

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> book.isOverdue(-1, 10));
    }

    @Test
    @DisplayName("getBookAge should return correct book age")
    void getBookAge_returnsCorrectAge() {
        // Arrange
        Book book = createValidBook();

        // Act
        int age = book.getBookAge();

        // Assert
        assertEquals(2025 - 1949, age);
    }

    // ---------- equals & hashCode ----------

    @Test
    @DisplayName("Books with same ISBN should be equal")
    void equals_sameIsbn_returnsTrue() {
        // Arrange
        Book book1 = createValidBook();
        Book book2 = new Book(
                "978-1-23-123456-7",
                "Different Title",
                createAuthor(),
                1950,
                "Drama"
        );

        // Act & Assert
        assertEquals(book1, book2);
        assertEquals(book1.hashCode(), book2.hashCode());
    }

    @Test
    @DisplayName("Books with different ISBN should not be equal")
    void equals_differentIsbn_returnsFalse() {
        // Arrange
        Book book1 = createValidBook();
        Book book2 = new Book(
                "978-1-23-654321-0",
                "1984",
                createAuthor(),
                1949,
                "Dystopian"
        );

        // Act & Assert
        assertNotEquals(book1, book2);
    }

    @Test
    @DisplayName("equals should return false when compared with null or different type")
    void equals_nullOrDifferentType_returnsFalse() {
        // Arrange
        Book book = createValidBook();

        // Act & Assert
        assertNotEquals(null, book);
        assertNotEquals(new Object(), book);
    }


}