package com.vbforge;

import java.util.Arrays;
import java.util.Objects;

public class LibraryMember {

    public static final int BORROWED_BOOKS_INIT_CAPACITY = 5;

    private final String memberId;                //unique member identifier
    private final String name;                    //member's name
    private final String email;                   //member's email address
    private final String[] borrowedBooks;               //array of borrowed book ISBNs
    private boolean isActive;                     //membership status

    /*Constructor with memberId, name, and email
    Validate email format (contains @ and .)
    Initialize borrowedBooks array with capacity of 5*/
    public LibraryMember(String memberId, String name, String email) {

        if(memberId == null || memberId.isBlank()){
            throw new IllegalArgumentException("memberId cannot be null or blank");
        }

        if(name==null || name.isBlank()){
            throw new IllegalArgumentException("name cannot be null or blank");
        }

        emailValidator(email);

        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.borrowedBooks = new String[BORROWED_BOOKS_INIT_CAPACITY];
        this.isActive = true;
    }

    //Getter methods and appropriate setters
    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String[] getBorrowedBooks() {
        return Arrays.copyOf(borrowedBooks, borrowedBooks.length);
    }

    public boolean isActive() {
        return isActive;
    }

    // Package-private
    void setActive(boolean active) {
        this.isActive = active;
    }

    //addBorrowedBook(String isbn) - adds book to borrowed list
    public void addBorrowedBook(String isbn){
        //ensure only active members can borrow books
        if (!isActive) {
            throw new IllegalStateException("Inactive member cannot borrow books");
        }
        if(isbn == null || isbn.isBlank()){
            throw new IllegalArgumentException("ISBN cannot be null or blank");
        }
        if (!canBorrowMore()) {
            throw new IllegalStateException("Member cannot borrow more books");
        }
        for (int i = 0; i < borrowedBooks.length; i++) {
            if(borrowedBooks[i] == null){
                borrowedBooks[i] = isbn;
                return;
            }
        }
        throw new IllegalStateException("Failed to add book: borrowedBooks array is full");
    }

    //removeBorrowedBook(String isbn) - removes book from borrowed list
    public void removeBorrowedBook(String isbn){
        if(isbn == null || isbn.isBlank()){
            throw new IllegalArgumentException("ISBN cannot be null or blank");
        }
        for (int i = 0; i < borrowedBooks.length; i++) {
            if (isbn.equals(borrowedBooks[i])) {
                borrowedBooks[i] = null;
                return;
            }
        }
        throw new IllegalStateException("Book not found in borrowed list");
    }

    //getBorrowedBookCount() - returns number of currently borrowed books
    public int getBorrowedBookCount(){
        int count = 0;
        for (String borrowedBook : borrowedBooks) {
            if(borrowedBook != null){
                count++;
            }
        }
        return count;
    }

    //canBorrowMore() - returns true if member can borrow more books (max 5)
    public boolean canBorrowMore(){
        return getBorrowedBookCount() < BORROWED_BOOKS_INIT_CAPACITY;
    }

    //hasBorrowedBook(String isbn) - checks if member has specific book
    public boolean hasBorrowedBook(String isbn){
        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("ISBN cannot be null or blank");
        }
        for (String borrowedBook : borrowedBooks) {
            if (borrowedBook != null && borrowedBook.equals(isbn)) {
                return true;
            }
        }
        return false;
    }

    //Override equals(), hashCode() member ID only (it is immutable)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LibraryMember that)) return false;
        return Objects.equals(memberId, that.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId);
    }

    //Override toString()
    @Override
    public String toString() {
        return "LibraryMember{" +
                "memberId='" + memberId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", borrowedBooks=" + Arrays.toString(borrowedBooks) +
                ", isActive=" + isActive +
                '}';
    }

    /**helper methods validation*/
    //Validate email format (contains @ and .)
    private void emailValidator(String email) {
        if(email == null || email.isBlank()){
            throw new IllegalArgumentException("Email cannot be null or blank");
        }
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!email.matches(emailPattern)) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }



}
