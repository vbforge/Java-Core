package com.vbforge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibraryCoreTest {

    private Library library;
    private Author author;
    private Book book1;
    private Book book2;
    private LibraryMember member1;
    private LibraryMember member2;

    @BeforeEach
    void setUp() {
        // Arrange
        library = new Library("Central Library", 10, 10);

        author = new Author("George", "Orwell", "British", 1903);

        book1 = new Book("123-4-56-123456-7", "1984", author, 1949, "Dystopian");
        book2 = new Book("321-1-11-654321-9", "Animal Farm", author, 1945, "Political");

        member1 = new LibraryMember("M001", "Alice", "alice@test.com");
        member2 = new LibraryMember("M002", "Bob", "bob@test.com");

        library.addBook(book1);
        library.addBook(book2);

        library.addMember(member1);
        library.addMember(member2);
    }

    // -------------------------------------------------
    // Constructor & basic setup
    // -------------------------------------------------

    @Test
    void constructor_validArguments_createsLibrary() {
        // Act
        Library lib = new Library("My Library", 5, 5);

        // Assert
        assertNotNull(lib);
    }

    // -------------------------------------------------
    // Core operations
    // -------------------------------------------------

    @Test
    void findBook_existingIsbn_returnsBook() {
        // Act
        Book found = library.findBook("123-4-56-123456-7");

        // Assert
        assertNotNull(found);
        assertEquals("1984", found.getTitle());
    }

    @Test
    void findMember_existingId_returnsMember() {
        // Act
        LibraryMember found = library.findMember("M001");

        // Assert
        assertNotNull(found);
        assertEquals("Alice", found.getName());
    }

    // -------------------------------------------------
    // Borrow / Return workflow
    // -------------------------------------------------

    @Test
    void borrowBook_validData_bookAndMemberUpdated() {
        // Act
        library.borrowBook("123-4-56-123456-7", "M001");

        // Assert
        assertFalse(book1.isAvailable());
        assertEquals("M001", book1.getCurrentBorrower());
        assertTrue(member1.hasBorrowedBook("123-4-56-123456-7"));
    }

    @Test
    void returnBook_validIsbn_bookBecomesAvailable() {
        // Arrange
        library.borrowBook("123-4-56-123456-7", "M001");

        // Act
        library.returnBook("123-4-56-123456-7");

        // Assert
        assertTrue(book1.isAvailable());
        assertNull(book1.getCurrentBorrower());
    }

    // -------------------------------------------------
    // Search & filter
    // -------------------------------------------------

    @Test
    void findBooksByAuthor_returnsCorrectBooks() {
        // Act
        Book[] result = library.findBooksByAuthor(author);

        // Assert
        assertEquals(2, countNotNull(result));
    }

    @Test
    void findBooksByGenre_returnsCorrectBooks() {
        // Act
        Book[] result = library.findBooksByGenre("Dystopian");

        // Assert
        assertEquals(1, countNotNull(result));
        assertEquals("1984", result[0].getTitle());
    }

    @Test
    void getAvailableBooks_returnsOnlyAvailableBooks() {
        // Arrange
        library.borrowBook("123-4-56-123456-7", "M001");

        // Act
        Book[] result = library.getAvailableBooks();

        // Assert
        assertEquals(1, countNotNull(result));
        assertEquals("Animal Farm", result[0].getTitle());
    }

    // -------------------------------------------------
    // Statistics
    // -------------------------------------------------

    @Test
    void getTotalBooks_returnsCorrectCount() {
        // Act & Assert
        assertEquals(2, library.getTotalBooks());
    }

    @Test
    void getAvailableBooksCount_returnsCorrectCount() {
        // Arrange
        library.borrowBook("123-4-56-123456-7", "M001");

        // Act & Assert
        assertEquals(1, library.getAvailableBooksCount());
    }

    @Test
    void getActiveMembersCount_returnsCorrectCount() {
        // Act
        int count = library.getActiveMembersCount();

        // Assert
        assertEquals(2, count);
    }

    @Test
    void getMostPopularGenre_returnsGenreWithMostBorrowedBooks() {
        // Arrange
        library.borrowBook("123-4-56-123456-7", "M001");

        // Act
        String genre = library.getMostPopularGenre();

        // Assert
        assertEquals("Dystopian", genre);
    }

    @Test
    void getMemberWithMostBooks_returnsCorrectMember() {
        // Arrange
        library.borrowBook("123-4-56-123456-7", "M001");

        // Act
        LibraryMember result = library.getMemberWithMostBooks();

        // Assert
        assertNotNull(result);
        assertEquals("M001", result.getMemberId());
    }

    // -------------------------------------------------
    // Administrative
    // -------------------------------------------------

    @Test
    void suspendAndReactivateMember_changesActiveStatus() {
        // Act
        library.suspendMember("M001");

        // Assert
        assertFalse(member1.isActive());

        // Act
        library.reactivateMember("M001");

        // Assert
        assertTrue(member1.isActive());
    }

    @Test
    void generateLibraryReport_containsExpectedData() {
        // Act
        String report = library.generateLibraryReport();

        // Assert
        assertTrue(report.contains("Central Library"));
        assertTrue(report.contains("Total books: 2"));
        assertTrue(report.contains("Available books"));
        assertTrue(report.contains("Active members: 2"));
    }

    @Test
    void memberCanBorrowMultipleBooks_untilLimit() {
        library.borrowBook(book1.getIsbn(), "M001");
        library.borrowBook(book2.getIsbn(), "M001");

        assertEquals(2, member1.getBorrowedBookCount());
    }

    @Test
    void borrowingUnavailableBook_fails() {
        library.borrowBook(book1.getIsbn(), "M001");

        assertThrows(IllegalStateException.class,
                () -> library.borrowBook(book1.getIsbn(), "M002"));
    }

    @Test
    void statistics_emptyLibrary_returnSafeDefaults() {
        Library emptyLibrary = new Library("Empty", 5, 5);

        assertEquals(0, emptyLibrary.getTotalBooks());
        assertEquals(0, emptyLibrary.getAvailableBooksCount());
        assertEquals(0, emptyLibrary.getActiveMembersCount());
        assertNull(emptyLibrary.getMostPopularGenre());
        assertNull(emptyLibrary.getMemberWithMostBooks());
    }

    @Test
    void bookAge_currentYear_isZero() {
        Book book = new Book(
                "111-1-11-111111-1",
                "New Book",
                author,
                2025,
                "Test"
        );

        assertEquals(0, book.getBookAge());
    }

    @Test
    void bookPublicationYear_inFuture_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Book(
                        "222-2-22-222222-2",
                        "Future Book",
                        author,
                        2026,
                        "Sci-Fi"
                ));
    }

    @Test
    void returnBook_removesBookFromMemberBorrowedList() {
        library.borrowBook("123-4-56-123456-7", "M001");
        library.returnBook("123-4-56-123456-7");

        assertFalse(member1.hasBorrowedBook("123-4-56-123456-7"));
    }


    // -------------------------------------------------
    // Overdue (explicitly unsupported)
    // -------------------------------------------------

    @Test
    void getOverdueBooks_throwsUnsupportedOperationException() {
        // Act & Assert
        assertThrows(UnsupportedOperationException.class,
                () -> library.getOverdueBooks(10));
    }

    // -------------------------------------------------
    // Helper
    // -------------------------------------------------

    private int countNotNull(Object[] array) {
        int count = 0;
        for (Object o : array) {
            if (o != null) {
                count++;
            }
        }
        return count;
    }


}