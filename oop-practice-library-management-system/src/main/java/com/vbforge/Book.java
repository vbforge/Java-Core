package com.vbforge;

import java.util.Objects;


public class Book {

    private static final int CURRENT_YEAR = 2025;

    private final String isbn;                    //International Standard Book Number
    private final String title;                   //book title
    private final Author author;                  //book author
    private final int publicationYear;            //year book was published
    private final String genre;                   //book genre
    private boolean isAvailable;                  //availability status
    private String currentBorrower;               //ID of current borrower (null if available)

    /*Constructor with all parameters except isAvailable and currentBorrower (set defaults)
    Validate ISBN format: "XXX-X-XX-XXXXXX-X" where X is digit
    Validate publication year is not in the future*/
    public Book(String isbn, String title, Author author, int publicationYear, String genre) {

        // Sanitize input first
        //trim the ISBN first to remove any accidental whitespace
        String trimmedIsbn = isbn != null ? isbn.trim() : null;

        // Validate the sanitized input
        isbnValidator(trimmedIsbn);

        if(title == null || title.isBlank()){
            throw new IllegalArgumentException("Title cannot be null or empty");
        }

        if(author == null){
            throw new IllegalArgumentException("Author cannot be null");
        }

        publicationYearValidator(publicationYear);

        if(genre == null || genre.isBlank()){
            throw new IllegalArgumentException("Genre cannot be null or empty");
        }

        // Store the sanitized values
        this.isbn = trimmedIsbn;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.genre = genre;
        this.isAvailable = true;
        this.currentBorrower = null;
    }

    //Getter methods
    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public String getGenre() {
        return genre;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    /*Availability must be controlled only by borrow/return
    Public setters allow invalid states:
    so, setAvailable and setCurrentBorrower should not exist;
    state changes only via borrowBook() / returnBook()
    public void setAvailable() {
        isAvailable = true;
    }
    public void setCurrentBorrower(String currentBorrower) {
        if(currentBorrower == null || currentBorrower.isBlank()){
            throw new IllegalArgumentException("Current borrower cannot be null or empty");
        }
        this.currentBorrower = currentBorrower;
    }*/

    public String getCurrentBorrower() {
        return currentBorrower;
    }


    //borrowBook(String borrowerId) - marks book as borrowed, returns success status
    /*  1-Book must be available
        2-Borrower ID must be valid
        3-Book becomes unavailable
        4-Borrower is recorded*/
    public boolean borrowBook(String borrowerId){
        if(borrowerId == null || borrowerId.isBlank()){
            throw new IllegalArgumentException("Borrower id cannot be null or empty");
        }
        if(!isAvailable){
            return false;
        }
        this.isAvailable = false;
        this.currentBorrower = borrowerId;
        return true;
        /*if (!isAvailable) {
            throw new IllegalStateException("Book already borrowed");
        }
        this.isAvailable = false;
        this.currentBorrower = borrowerId;*/
    }

    //returnBook() - marks book as available, clears borrower
    /*  1-If book is already available → return false
        2-Otherwise:
            - Set available as true
            - Clear borrower as null*/
    public boolean returnBook(){
        if(isAvailable()){
            return false;
        }
        this.isAvailable = true;
        this.currentBorrower = null;
        return true;
    }

    //isOverdue(int daysBorrowed, int maxDays) - checks if book is overdue
    public boolean isOverdue(int daysBorrowed, int maxDays){
        if(daysBorrowed < 0 || maxDays < 0){
            throw new IllegalArgumentException("Days borrowed or max days cannot be negative");
        }
        return daysBorrowed > maxDays;
    }

    //getBookAge() - returns how many years since publication
    public int getBookAge(){
        return CURRENT_YEAR - this.publicationYear;
    }

    //Override equals() (based on ISBN), hashCode()
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof Book)) return false;
        return isbn.equals(((Book)o).isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    //Override toString()
    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", publicationYear=" + publicationYear +
                ", genre='" + genre + '\'' +
                ", isAvailable=" + isAvailable +
                ", currentBorrower='" + currentBorrower + '\'' +
                '}';
    }

    /**helper methods for validation Book parameters:*/
    //Validate ISBN format: "XXX-X-XX-XXXXXX-X" where X is digit
    private void isbnValidator(String isbn) {
        //null check
        if (isbn == null) {
            throw new IllegalArgumentException("ISBN cannot be null");
        }
        // Check length first
        if (isbn.length() != 17) {
            throw new IllegalArgumentException("ISBN length should be 17 characters including hyphens.");
        }

        // Define regex for format "XXX-X-XX-XXXXXX-X"
        String pattern = "\\d{3}-\\d-\\d{2}-\\d{6}-\\d";
        if (!isbn.matches(pattern)) {
            throw new IllegalArgumentException("Invalid ISBN format. Expected format: XXX-X-XX-XXXXXX-X");
        }
    }

    //Validate publication year is not in the future
    private void publicationYearValidator(int publicationYear) {
        if(publicationYear <= 0 ) {
            throw new IllegalArgumentException("Publication year should be greater than 0.");
        }
        if(publicationYear > CURRENT_YEAR) {
            throw new IllegalArgumentException("Publication year should be less than CURRENT_YEAR (not in future publishing years).");
        }
    }
}
