package com.vbforge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the Library Management System
 * Tests the interaction between Library, Book, Author, and LibraryMember classes
 */
class LibraryIntegrationTest {

    private Library library;
    private Author georgeOrwell;
    private Author janeAusten;
    private Author isaacAsimov;

    @BeforeEach
    void setUp() {
        library = new Library("Central City Library", 20, 10);

        georgeOrwell = new Author("George", "Orwell", "British", 1903);
        janeAusten = new Author("Jane", "Austen", "British", 1975);
        isaacAsimov = new Author("Isaac", "Asimov", "American", 1920);
    }

    // ========================================
    // Complete Borrow-Return Workflow Tests
    // ========================================

    @Test
    @DisplayName("Complete workflow: Add book and member, borrow, verify state, return, verify final state")
    void completeBorrowReturnWorkflow() {
        // Arrange
        Book book = new Book("978-0-12-345678-9", "1984", georgeOrwell, 1949, "Dystopian");
        LibraryMember member = new LibraryMember("M001", "Alice Johnson", "alice@library.com");

        library.addBook(book);
        library.addMember(member);

        // Act - Borrow
        library.borrowBook("978-0-12-345678-9", "M001");

        // Assert - After borrowing
        assertAll("After borrowing",
                () -> assertFalse(book.isAvailable(), "Book should not be available"),
                () -> assertEquals("M001", book.getCurrentBorrower(), "Current borrower should be M001"),
                () -> assertEquals(1, member.getBorrowedBookCount(), "Member should have 1 borrowed book"),
                () -> assertTrue(member.hasBorrowedBook("978-0-12-345678-9"), "Member should have this book"),
                () -> assertEquals(0, library.getAvailableBooksCount(), "Library should have 0 available books")
        );

        // Act - Return
        library.returnBook("978-0-12-345678-9");

        // Assert - After returning
        assertAll("After returning",
                () -> assertTrue(book.isAvailable(), "Book should be available"),
                () -> assertNull(book.getCurrentBorrower(), "Current borrower should be null"),
                () -> assertEquals(0, member.getBorrowedBookCount(), "Member should have 0 borrowed books"),
                () -> assertFalse(member.hasBorrowedBook("978-0-12-345678-9"), "Member should not have this book"),
                () -> assertEquals(1, library.getAvailableBooksCount(), "Library should have 1 available book")
        );
    }

    @Test
    @DisplayName("Member borrows multiple books and returns them one by one")
    void multipleBooksWorkflow() {
        // Arrange
        Book book1 = new Book("978-0-12-345678-9", "1984", georgeOrwell, 1949, "Dystopian");
        Book book2 = new Book("978-0-98-765432-1", "Animal Farm", georgeOrwell, 1945, "Political Fiction");
        Book book3 = new Book("978-0-11-111111-1", "Pride and Prejudice", janeAusten, 1813, "Romance");
        LibraryMember member = new LibraryMember("M001", "Bob Smith", "bob@library.com");

        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        library.addMember(member);

        // Act - Borrow three books
        library.borrowBook("978-0-12-345678-9", "M001");
        library.borrowBook("978-0-98-765432-1", "M001");
        library.borrowBook("978-0-11-111111-1", "M001");

        // Assert - After borrowing
        assertEquals(3, member.getBorrowedBookCount(), "Member should have 3 borrowed books");
        assertEquals(0, library.getAvailableBooksCount(), "No books should be available");

        // Act - Return first book
        library.returnBook("978-0-12-345678-9");

        // Assert - After first return
        assertEquals(2, member.getBorrowedBookCount(), "Member should have 2 borrowed books");
        assertEquals(1, library.getAvailableBooksCount(), "1 book should be available");
        assertFalse(member.hasBorrowedBook("978-0-12-345678-9"), "Member should not have book1");
        assertTrue(member.hasBorrowedBook("978-0-98-765432-1"), "Member should still have book2");

        // Act - Return remaining books
        library.returnBook("978-0-98-765432-1");
        library.returnBook("978-0-11-111111-1");

        // Assert - All returned
        assertEquals(0, member.getBorrowedBookCount(), "Member should have 0 borrowed books");
        assertEquals(3, library.getAvailableBooksCount(), "All books should be available");
    }

    // ========================================
    // Error Handling & Edge Cases
    // ========================================

    @Test
    @DisplayName("Cannot borrow book that is already borrowed")
    void cannotBorrowAlreadyBorrowedBook() {
        // Arrange
        Book book = new Book("978-0-12-345678-9", "1984", georgeOrwell, 1949, "Dystopian");
        LibraryMember member1 = new LibraryMember("M001", "Alice", "alice@library.com");
        LibraryMember member2 = new LibraryMember("M002", "Bob", "bob@library.com");

        library.addBook(book);
        library.addMember(member1);
        library.addMember(member2);

        library.borrowBook("978-0-12-345678-9", "M001");

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> library.borrowBook("978-0-12-345678-9", "M002"));

        assertEquals("Book is already borrowed", exception.getMessage());
        assertEquals(1, member1.getBorrowedBookCount(), "Member1 should still have the book");
        assertEquals(0, member2.getBorrowedBookCount(), "Member2 should have 0 books");
    }

    @Test
    @DisplayName("Suspended member cannot borrow books")
    void suspendedMemberCannotBorrow() {
        // Arrange
        Book book = new Book("978-0-12-345678-9", "1984", georgeOrwell, 1949, "Dystopian");
        LibraryMember member = new LibraryMember("M001", "Charlie", "charlie@library.com");

        library.addBook(book);
        library.addMember(member);
        library.suspendMember("M001");

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> library.borrowBook("978-0-12-345678-9", "M001"));

        assertEquals("Member is suspended", exception.getMessage());
        assertTrue(book.isAvailable(), "Book should still be available");
    }

    @Test
    @DisplayName("Member can borrow after reactivation")
    void reactivatedMemberCanBorrow() {
        // Arrange
        Book book = new Book("978-0-12-345678-9", "1984", georgeOrwell, 1949, "Dystopian");
        LibraryMember member = new LibraryMember("M001", "Diana", "diana@library.com");

        library.addBook(book);
        library.addMember(member);
        library.suspendMember("M001");
        library.reactivateMember("M001");

        // Act
        library.borrowBook("978-0-12-345678-9", "M001");

        // Assert
        assertFalse(book.isAvailable(), "Book should be borrowed");
        assertEquals(1, member.getBorrowedBookCount(), "Member should have 1 book");
    }


    //fail-fast validation and maintaining transactional integrity
    //1. ✅ Check if book exists
    //2. ✅ Check if member exists
    //3. ✅ Check if member is active
    //4. ✅ Check if member can borrow more (NEW!)
    //5. ✅ Attempt to borrow book
    //6. ✅ Add book to member's list
    @Test
    @DisplayName("Member cannot borrow more than 5 books")
    void cannotBorrowMoreThanMaximum() {
        // Arrange
        LibraryMember member = new LibraryMember("M001", "Eve", "eve@library.com");
        library.addMember(member);

        for (int i = 1; i <= 5; i++) {
            String isbn = String.format("978-0-12-34567%d-%d", i, i);
            Book book = new Book(isbn, "Book " + i, georgeOrwell, 2020, "Fiction");
            library.addBook(book);
            library.borrowBook(isbn, "M001");
        }

        Book sixthBook = new Book("978-0-12-999999-9", "Book 6", georgeOrwell, 2020, "Fiction");
        library.addBook(sixthBook);

        // Act & Assert
        assertThrows(IllegalStateException.class,
                () -> library.borrowBook("978-0-12-999999-9", "M001"));

        assertEquals(5, member.getBorrowedBookCount(), "Member should have 5 books");
        assertTrue(sixthBook.isAvailable(), "Sixth book should still be available");
    }

    @Test
    @DisplayName("Cannot add duplicate books")
    void cannotAddDuplicateBooks() {
        // Arrange
        Book book1 = new Book("978-0-12-345678-9", "1984", georgeOrwell, 1949, "Dystopian");
        Book book2 = new Book("978-0-12-345678-9", "Different Title", janeAusten, 2000, "Romance");

        library.addBook(book1);

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> library.addBook(book2));

        assertEquals("Book with ISBN already exists", exception.getMessage());
        assertEquals(1, library.getTotalBooks(), "Should only have 1 book");
    }

    @Test
    @DisplayName("Cannot add duplicate members")
    void cannotAddDuplicateMembers() {
        // Arrange
        LibraryMember member1 = new LibraryMember("M001", "Alice", "alice@library.com");
        LibraryMember member2 = new LibraryMember("M001", "Bob", "bob@library.com");

        library.addMember(member1);

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> library.addMember(member2));

        assertEquals("LibraryMember already exists", exception.getMessage());
    }

    // ========================================
    // Search and Filter Integration Tests
    // ========================================

    @Test
    @DisplayName("Find books by author across multiple books")
    void findBooksByAuthorIntegration() {
        // Arrange
        Book book1 = new Book("978-0-12-345678-9", "1984", georgeOrwell, 1949, "Dystopian");
        Book book2 = new Book("978-0-98-765432-1", "Animal Farm", georgeOrwell, 1945, "Political Fiction");
        Book book3 = new Book("978-0-11-111111-1", "Pride and Prejudice", janeAusten, 1813, "Romance");

        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);

        // Act
        Book[] orwellBooks = library.findBooksByAuthor(georgeOrwell);
        Book[] austenBooks = library.findBooksByAuthor(janeAusten);

        // Assert
        assertEquals(2, orwellBooks.length, "Should find 2 Orwell books");
        assertEquals(1, austenBooks.length, "Should find 1 Austen book");
        assertTrue(containsBook(orwellBooks, "1984"));
        assertTrue(containsBook(orwellBooks, "Animal Farm"));
    }

    @Test
    @DisplayName("Get available books updates after borrowing and returning")
    void availableBooksDynamicUpdate() {
        // Arrange
        Book book1 = new Book("978-0-12-345678-9", "1984", georgeOrwell, 1949, "Dystopian");
        Book book2 = new Book("978-0-98-765432-1", "Animal Farm", georgeOrwell, 1945, "Political Fiction");
        LibraryMember member = new LibraryMember("M001", "Frank", "frank@library.com");

        library.addBook(book1);
        library.addBook(book2);
        library.addMember(member);

        // Initially all available
        assertEquals(2, library.getAvailableBooks().length);

        // Borrow one
        library.borrowBook("978-0-12-345678-9", "M001");
        assertEquals(1, library.getAvailableBooks().length);

        // Borrow second
        library.borrowBook("978-0-98-765432-1", "M001");
        assertEquals(0, library.getAvailableBooks().length);

        // Return one
        library.returnBook("978-0-12-345678-9");
        assertEquals(1, library.getAvailableBooks().length);

        // Return second
        library.returnBook("978-0-98-765432-1");
        assertEquals(2, library.getAvailableBooks().length);
    }

    @Test
    @DisplayName("Get borrowed books by member returns correct books")
    void getBorrowedBooksByMemberIntegration() {
        // Arrange
        Book book1 = new Book("978-0-12-345678-9", "1984", georgeOrwell, 1949, "Dystopian");
        Book book2 = new Book("978-0-98-765432-1", "Foundation", isaacAsimov, 1951, "Science Fiction");
        LibraryMember member = new LibraryMember("M001", "Grace", "grace@library.com");

        library.addBook(book1);
        library.addBook(book2);
        library.addMember(member);

        library.borrowBook("978-0-12-345678-9", "M001");
        library.borrowBook("978-0-98-765432-1", "M001");

        // Act
        Book[] borrowedBooks = library.getBorrowedBooksByMember("M001");

        // Assert
        assertEquals(2, borrowedBooks.length, "Member should have 2 borrowed books");
        assertTrue(containsBook(borrowedBooks, "1984"));
        assertTrue(containsBook(borrowedBooks, "Foundation"));
    }

    // ========================================
    // Statistics Integration Tests
    // ========================================

    @Test
    @DisplayName("Most popular genre updates correctly")
    void mostPopularGenreIntegration() {
        // Arrange
        Book book1 = new Book("978-0-12-345678-9", "1984", georgeOrwell, 1949, "Dystopian");
        Book book2 = new Book("978-0-98-765432-1", "Animal Farm", georgeOrwell, 1945, "Dystopian");
        Book book3 = new Book("978-0-11-111111-1", "Foundation", isaacAsimov, 1951, "Science Fiction");

        LibraryMember member1 = new LibraryMember("M001", "Henry", "henry@library.com");
        LibraryMember member2 = new LibraryMember("M002", "Iris", "iris@library.com");

        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        library.addMember(member1);
        library.addMember(member2);

        // Act - Borrow 2 dystopian, 1 sci-fi
        library.borrowBook("978-0-12-345678-9", "M001");
        library.borrowBook("978-0-98-765432-1", "M002");
        library.borrowBook("978-0-11-111111-1", "M001");

        // Assert
        assertEquals("Dystopian", library.getMostPopularGenre(), "Dystopian should be most popular");
    }

    @Test
    @DisplayName("Member with most books updates correctly")
    void memberWithMostBooksIntegration() {
        // Arrange
        Book book1 = new Book("978-0-12-345678-9", "Book 1", georgeOrwell, 1949, "Fiction");
        Book book2 = new Book("978-0-98-765432-1", "Book 2", georgeOrwell, 1945, "Fiction");
        Book book3 = new Book("978-0-11-111111-1", "Book 3", isaacAsimov, 1951, "Fiction");

        LibraryMember member1 = new LibraryMember("M001", "Jack", "jack@library.com");
        LibraryMember member2 = new LibraryMember("M002", "Kate", "kate@library.com");

        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        library.addMember(member1);
        library.addMember(member2);

        // Act - Member1 borrows 2, Member2 borrows 1
        library.borrowBook("978-0-12-345678-9", "M001");
        library.borrowBook("978-0-98-765432-1", "M001");
        library.borrowBook("978-0-11-111111-1", "M002");

        // Assert
        LibraryMember topMember = library.getMemberWithMostBooks();
        assertEquals("M001", topMember.getMemberId(), "Member M001 should have most books");
    }

    @Test
    @DisplayName("Library report shows accurate system state")
    void libraryReportIntegration() {
        // Arrange
        Book book1 = new Book("978-0-12-345678-9", "1984", georgeOrwell, 1949, "Dystopian");
        Book book2 = new Book("978-0-98-765432-1", "Animal Farm", georgeOrwell, 1945, "Dystopian");
        LibraryMember member = new LibraryMember("M001", "Laura", "laura@library.com");

        library.addBook(book1);
        library.addBook(book2);
        library.addMember(member);
        library.borrowBook("978-0-12-345678-9", "M001");

        // Act
        String report = library.generateLibraryReport();

        // Assert
        assertAll("Report contains correct information",
                () -> assertTrue(report.contains("Central City Library"), "Contains library name"),
                () -> assertTrue(report.contains("Total books: 2"), "Contains total books"),
                () -> assertTrue(report.contains("Available books: 1"), "Contains available books"),
                () -> assertTrue(report.contains("Active members: 1"), "Contains active members"),
                () -> assertTrue(report.contains("Most popular genre: Dystopian"), "Contains popular genre"),
                () -> assertTrue(report.contains("Member with most borrowed books: M001"), "Contains top member")
        );
    }

    // ========================================
    // Complex Scenario Tests
    // ========================================

    @Test
    @DisplayName("Multiple members borrowing and returning different books simultaneously")
    void complexMultiMemberScenario() {
        // Arrange
        Book[] books = new Book[10];
        String[] isbns = new String[10];

        for (int i = 0; i < 10; i++) {
            isbns[i] = String.format("978-0-12-34567%d-%d", i, i);
            books[i] = new Book(isbns[i], "Book " + i, georgeOrwell, 2020, "Fiction");
            library.addBook(books[i]);
        }

        LibraryMember[] members = new LibraryMember[3];
        members[0] = new LibraryMember("M001", "Member 1", "m1@library.com");
        members[1] = new LibraryMember("M002", "Member 2", "m2@library.com");
        members[2] = new LibraryMember("M003", "Member 3", "m3@library.com");

        for (LibraryMember member : members) {
            library.addMember(member);
        }

        // Act - Complex borrowing pattern
        library.borrowBook(isbns[0], "M001");
        library.borrowBook(isbns[1], "M001");
        library.borrowBook(isbns[2], "M002");
        library.borrowBook(isbns[3], "M002");
        library.borrowBook(isbns[4], "M002");
        library.borrowBook(isbns[5], "M003");

        // Assert intermediate state
        assertEquals(4, library.getAvailableBooksCount(), "4 books should be available");
        assertEquals(2, members[0].getBorrowedBookCount(), "M001 has 2 books");
        assertEquals(3, members[1].getBorrowedBookCount(), "M002 has 3 books");
        assertEquals(1, members[2].getBorrowedBookCount(), "M003 has 1 book");

        // Act - Returns
        library.returnBook(isbns[0]);
        library.returnBook(isbns[2]);

        // Assert final state
        assertEquals(6, library.getAvailableBooksCount(), "6 books should be available");
        assertEquals(1, members[0].getBorrowedBookCount(), "M001 has 1 book");
        assertEquals(2, members[1].getBorrowedBookCount(), "M002 has 2 books");
        assertFalse(members[0].hasBorrowedBook(isbns[0]), "M001 returned book 0");
        assertFalse(members[1].hasBorrowedBook(isbns[2]), "M002 returned book 2");
    }

    @Test
    @DisplayName("ISBN with whitespace is handled correctly")
    void isbnWhitespaceHandling() {
        // Arrange
        String isbnWithSpaces = "  978-0-12-345678-9  ";
        Book book = new Book(isbnWithSpaces, "1984", georgeOrwell, 1949, "Dystopian");
        LibraryMember member = new LibraryMember("M001", "Mike", "mike@library.com");

        library.addBook(book);
        library.addMember(member);

        // Act - Use trimmed ISBN to find and borrow
        Book foundBook = library.findBook("978-0-12-345678-9");
        library.borrowBook("978-0-12-345678-9", "M001");

        // Assert
        assertNotNull(foundBook, "Book should be found with trimmed ISBN");
        assertEquals("978-0-12-345678-9", book.getIsbn(), "Stored ISBN should be trimmed");
        assertFalse(book.isAvailable(), "Book should be borrowed");
    }

    // ========================================
    // Helper Methods
    // ========================================

    private boolean containsBook(Book[] books, String title) {
        for (Book book : books) {
            if (book != null && book.getTitle().equals(title)) {
                return true;
            }
        }
        return false;
    }
}