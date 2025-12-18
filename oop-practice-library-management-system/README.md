# Library Management System (Advanced Level)

**Focus:** Multiple interrelated classes, complex operations, comprehensive system design  
**Key concepts:** Object relationships, complex searching/filtering, statistical calculations, data integrity  
**Estimated time:** 60 minutes

#### The tasks are designed to give practice with:

- Challenges: Multi-class systems design, Complex relationships
- Array manipulation at different complexity levels
- Input validation strategies
- Method overriding and inheritance
- Complex business logic implementation
- Error handling patterns
- Comprehensive unit testing
- Integration testing to show the system work all together

## Description

Create a comprehensive library management system with books, authors, borrowers, and complex operations.

### Task Requirements:

#### 🚀 Develop `Author` class with the following content:

- **Private fields:**
    - `String firstName` - author's first name
    - `String lastName` - author's last name
    - `String nationality` - author's nationality
    - `int birthYear` - author's birth year

- **Constructor and validation:**
    - Constructor with all parameters
    - Validate that names are not null/empty and birthYear is reasonable (1900-2025)

- **Methods:**
    - Getter methods, `getFullName()`, `getAge()` (assuming current year is 2025)
    - Override `equals()`, `hashCode()`, and `toString()` methods

#### 🚀 Develop `Book` class with the following content:

- **Private fields:**
    - `String isbn` - International Standard Book Number
    - `String title` - book title
    - `Author author` - book author
    - `int publicationYear` - year book was published
    - `String genre` - book genre
    - `boolean isAvailable` - availability status
    - `String currentBorrower` - ID of current borrower (null if available)

- **Constructor and validation:**
    - Constructor with all parameters except isAvailable and currentBorrower (set defaults)
    - Validate ISBN format: "XXX-X-XX-XXXXXX-X" where X is digit
    - Validate publication year is not in the future

- **Methods:**
    - Getter methods and appropriate setters
    - `borrowBook(String borrowerId)` - marks book as borrowed, returns success status
    - `returnBook()` - marks book as available, clears borrower
    - `isOverdue(int daysBorrowed, int maxDays)` - checks if book is overdue
    - `getBookAge()` - returns how many years since publication
    - Override `equals()` (based on ISBN), `hashCode()`, and `toString()` methods

#### 🚀 Develop `LibraryMember` class with the following content:

- **Private fields:**
    - `String memberId` - unique member identifier
    - `String name` - member's name
    - `String email` - member's email address
    - `String[] borrowedBooks` - array of borrowed book ISBNs
    - `boolean isActive` - membership status

- **Constructor and validation:**
    - Constructor with memberId, name, and email
    - Validate email format (contains @ and .)
    - Initialize borrowedBooks array with capacity of 5

- **Methods:**
    - Getter methods and appropriate setters
    - `addBorrowedBook(String isbn)` - adds book to borrowed list
    - `removeBorrowedBook(String isbn)` - removes book from borrowed list
    - `getBorrowedBookCount()` - returns number of currently borrowed books
    - `canBorrowMore()` - returns true if member can borrow more books (max 5)
    - `hasBorrowedBook(String isbn)` - checks if member has specific book
    - Override `equals()`, `hashCode()`, and `toString()` methods

#### 🚀 Develop `Library` class with the following content:

- **Private fields:**
    - `Book[] books` - array of all library books
    - `LibraryMember[] members` - array of all library members
    - `String libraryName` - name of the library

- **Constructor:**
    - Constructor with libraryName and capacities for books and members arrays

- **Core Methods:**
    - `addBook(Book book)` - adds book to library collection
    - `addMember(LibraryMember member)` - adds member to library
    - `findBook(String isbn)` - returns book or null
    - `findMember(String memberId)` - returns member or null

- **Borrowing Operations:**
    - `borrowBook(String isbn, String memberId)` - processes book borrowing
    - `returnBook(String isbn)` - processes book return
    - `getBorrowedBooksByMember(String memberId)` - returns array of books borrowed by member

- **Search and Filter Methods:**
    - `findBooksByAuthor(Author author)` - returns array of books by specific author
    - `findBooksByGenre(String genre)` - returns array of books in specific genre
    - `getAvailableBooks()` - returns array of all available books
    - `getOverdueBooks(int maxDays)` - returns array of overdue books

- **Statistics Methods:**
    - `getTotalBooks()` - returns total number of books
    - `getAvailableBooksCount()` - returns number of available books
    - `getActiveMembersCount()` - returns number of active members
    - `getMostPopularGenre()` - returns genre with most borrowed books
    - `getMemberWithMostBooks()` - returns member who borrowed most books

- **Administrative Methods:**
    - `generateLibraryReport()` - returns comprehensive library status report
    - `suspendMember(String memberId)` - deactivates member account
    - `reactivateMember(String memberId)` - reactivates member account

### Additional Requirements:

- **Error Handling:** All methods should handle null parameters appropriately
- **Data Integrity:** Ensure borrowing operations maintain data consistency
- **Edge Cases:** Handle scenarios like borrowing unavailable books, returning non-borrowed books
- **Performance:** Consider efficiency when searching through arrays
- **Validation:** Implement comprehensive input validation throughout the system

### Testing Considerations:

Your implementation should handle:
- Multiple books by the same author
- Members borrowing and returning multiple books
- Attempting to borrow unavailable books
- Searching with various criteria
- Statistical calculations with empty or partial data
- Edge cases in date calculations and validations