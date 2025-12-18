package com.vbforge;

import java.util.Arrays;

public class Library {

    private final String libraryName;                   //name of the library
    private final Book[] books;                         //array of all library books
    private final LibraryMember[] members;              //array of all library members

    //Constructor with libraryName and capacities for books and members arrays
    public Library(String libraryName, int booksCapacity, int membersCapacity) {

        if (libraryName == null || libraryName.isBlank() || libraryName.length() < 3) {
            throw new IllegalArgumentException("libraryName must be at least 3 characters");
        }
        if (booksCapacity <= 0 || membersCapacity <= 0) {
            throw new IllegalArgumentException("capacities must be positive");
        }

        this.libraryName = libraryName;
        this.books = new Book[booksCapacity];
        this.members = new LibraryMember[membersCapacity];
    }

    /**core methods:*/
    //addBook(Book book) - adds book to library collection
    public void addBook(Book book){
        if (book == null) {
            throw new IllegalArgumentException("book cannot be null");
        }

        if (findBook(book.getIsbn()) != null) {
//            return; // ignore duplicates
            throw new IllegalStateException("Book with ISBN already exists");
        }

        for (int i = 0; i < books.length; i++) {
            if (books[i] == null) {
                books[i] = book;
                return;
            }
        }
        throw new IllegalStateException("Library book capacity exceeded");
    }

    //addMember(LibraryMember member) - adds member to library
    public void addMember(LibraryMember member){
        if (member == null) {
            throw new IllegalArgumentException("member cannot be null");
        }

        if (findMember(member.getMemberId()) != null) {
//            return; // ignore duplicates
            throw new IllegalStateException("LibraryMember already exists");
        }

        for (int i = 0; i < members.length; i++) {
            if (members[i] == null) {
                members[i] = member;
                return;
            }
        }
        throw new IllegalStateException("Library member capacity exceeded");
    }

    //findBook(String isbn) - returns book or null
    public Book findBook(String isbn){
        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("isbn cannot be null or blank");
        }

        for (Book book : books) {
            if (book != null && book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    //findMember(String memberId) - returns member or null
    public LibraryMember findMember(String memberId){
        if (memberId == null || memberId.isBlank()) {
            throw new IllegalArgumentException("memberId cannot be null or blank");
        }

        for (LibraryMember member : members) {
            if (member != null && member.getMemberId().equals(memberId)) {
                return member;
            }
        }
        return null;
    }

    /**
     * borrowing operations:
     */
    //borrowBook(String isbn, String memberId) - processes book borrowing
    //why choose this version: service layer reacts to domain outcomes; the domain layer owns the rules.
    public void borrowBook(String isbn, String memberId){
        /*Book book = findBook(isbn);
        LibraryMember member = findMember(memberId);
        if (book == null) {
            throw new IllegalStateException("Book not found: " + isbn);
        }
        if (member == null) {
            throw new IllegalStateException("Member not found: " + memberId);
        }
        if (!book.isAvailable()) {
            throw new IllegalStateException("Book is already borrowed");
        }
        book.borrowBook(memberId);
        member.addBorrowedBook(isbn);*/

        //fail-fast validation and maintaining transactional integrity

        Book book = findBook(isbn);
        LibraryMember member = findMember(memberId);

        if (book == null) {
            throw new IllegalStateException("Book not found: " + isbn);
        }
        if (member == null) {
            throw new IllegalStateException("Member not found: " + memberId);
        }
        if(!member.isActive()){
            throw new IllegalStateException("Member is suspended");
        }
        //check if member can borrow more books (if capacity allowed to get more)
        if(!member.canBorrowMore()){
            throw new IllegalStateException("Cannot borrow more books");
        }

        boolean borrowed = book.borrowBook(memberId);

        if (!borrowed) {
            throw new IllegalStateException("Book is already borrowed");
        }

        member.addBorrowedBook(isbn);

    }

    //returnBook(String isbn) - processes book return
    public void returnBook(String isbn){
        Book book = findBook(isbn);

        if (book == null) {
            throw new IllegalStateException("Book not found");
        }

        String borrowerId = book.getCurrentBorrower();
        boolean returned = book.returnBook();

        if (returned && borrowerId != null) {
            LibraryMember member = findMember(borrowerId);
            if (member != null) {
                member.removeBorrowedBook(isbn);
            }
        }

    }

    //getBorrowedBooksByMember(String memberId) - returns array of books borrowed by member
    public Book[] getBorrowedBooksByMember(String memberId){
        LibraryMember member = findMember(memberId);

        if (member == null) {
            throw new IllegalStateException("Member not found");
        }

        String[] borrowedIsbns = member.getBorrowedBooks();
        Book[] result = new Book[borrowedIsbns.length];
        int index = 0;

        for (String isbn : borrowedIsbns) {
            if (isbn != null) {
                Book book = findBook(isbn);
                if (book != null) {
                    result[index++] = book;
                }
            }
        }
        return Arrays.copyOf(result, index);
    }

    /**Search and Filter Methods:*/
    //findBooksByAuthor(Author author) - returns array of books by specific author
    public Book[] findBooksByAuthor(Author author){
        if (author == null) {
            throw new IllegalArgumentException("author cannot be null");
        }

        Book[] result = new Book[books.length];
        int index = 0;

        for (Book book : books) {
            if (book != null && book.getAuthor().equals(author)) {
                result[index++] = book;
            }
        }
        return Arrays.copyOf(result, index);
    }

    //findBooksByGenre(String genre) - returns array of books in specific genre
    public Book[] findBooksByGenre(String genre){
        if (genre == null || genre.isBlank()) {
            throw new IllegalArgumentException("genre cannot be null or blank");
        }

        Book[] result = new Book[books.length];
        int index = 0;

        for (Book book : books) {
            if (book != null && book.getGenre().equalsIgnoreCase(genre)) {
                result[index++] = book;
            }
        }
        return Arrays.copyOf(result, index);
    }

    //getAvailableBooks() - returns array of all available books
    public Book[] getAvailableBooks(){
        Book[] result = new Book[books.length];
        int index = 0;

        for (Book book : books) {
            if (book != null && book.isAvailable()) {
                result[index++] = book;
            }
        }
        return Arrays.copyOf(result, index);
    }

    //getOverdueBooks(int maxDays) - returns array of overdue books
    public Book[] getOverdueBooks(int maxDays) {
        if (maxDays < 0) {
            throw new IllegalArgumentException("maxDays cannot be negative");
        }
        throw new UnsupportedOperationException(
                "Overdue calculation requires borrow duration tracking, which is not implemented"
        );
    }

    /**Statistics Methods:*/
    //getTotalBooks() - returns total number of books
    public int getTotalBooks() {
        int count = 0;
        for (Book book : books) {
            if (book != null) {
                count++;
            }
        }
        return count;
    }

    //getAvailableBooksCount() - returns number of available books
    public int getAvailableBooksCount() {
        int count = 0;
        for (Book book : books) {
            if (book != null && book.isAvailable()) {
                count++;
            }
        }
        return count;
    }

    //getActiveMembersCount() - returns number of active members
    public int getActiveMembersCount() {
        int count = 0;
        for (LibraryMember member : members) {
            if (member != null && member.isActive()) {
                count++;
            }
        }
        return count;
    }

    //getMostPopularGenre() - returns genre with most borrowed books
    public String getMostPopularGenre() {
        String[] genres = new String[books.length];
        int[] counts = new int[books.length];
        int size = 0;

        for (Book book : books) {
            if (book != null && !book.isAvailable()) {
                String genre = book.getGenre();

                int index = -1;
                for (int i = 0; i < size; i++) {
                    if (genres[i].equals(genre)) {
                        index = i;
                        break;
                    }
                }

                if (index >= 0) {
                    counts[index]++;
                } else {
                    genres[size] = genre;
                    counts[size] = 1;
                    size++;
                }
            }
        }

        if (size == 0) {
            return null;
        }

        int maxIndex = 0;
        for (int i = 1; i < size; i++) {
            if (counts[i] > counts[maxIndex]) {
                maxIndex = i;
            }
        }
        return genres[maxIndex];
    }

    //getMemberWithMostBooks() - returns member who borrowed most books
    public LibraryMember getMemberWithMostBooks() {
        LibraryMember result = null;
        int maxBorrowed = 0;

        for (LibraryMember member : members) {
            if (member != null) {
                int borrowedCount = member.getBorrowedBookCount();
                if (borrowedCount > maxBorrowed) {
                    maxBorrowed = borrowedCount;
                    result = member;
                }
            }
        }
        return result;
    }

    /**Administrative Methods:*/
    //generateLibraryReport() - returns comprehensive library status report
    public String generateLibraryReport() {
        return """
            Library Report
            -------------------------
            Name: %s
            Total books: %d
            Available books: %d
            Active members: %d
            Most popular genre: %s
            Member with most borrowed books: %s
            """.formatted(
                libraryName,
                getTotalBooks(),
                getAvailableBooksCount(),
                getActiveMembersCount(),
                getMostPopularGenre(),
                getMemberWithMostBooks() != null
                        ? getMemberWithMostBooks().getMemberId()
                        : "N/A"
        );
    }

    //suspendMember(String memberId) - deactivates member account
    public void suspendMember(String memberId) {
        LibraryMember member = findMember(memberId);
        if (member == null) {
            throw new IllegalStateException("Member not found");
        }
        member.setActive(false);
    }

    //reactivateMember(String memberId) - reactivates member account
    public void reactivateMember(String memberId) {
        LibraryMember member = findMember(memberId);
        if (member == null) {
            throw new IllegalStateException("Member not found");
        }
        member.setActive(true);
    }
}
