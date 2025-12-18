package com.vbforge;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorCoreTest {

    // ---------- Constructor validation ----------

    @Test
    @DisplayName("Constructor should create Author with valid data")
    void constructor_validData_createsAuthor() {
        // Arrange
        String firstName = "George";
        String lastName = "Orwell";
        String nationality = "British";
        int birthYear = 1903;

        // Act
        Author author = new Author(firstName, lastName, nationality, birthYear);

        // Assert
        assertEquals(firstName, author.getFirstName());
        assertEquals(lastName, author.getLastName());
        assertEquals(nationality, author.getNationality());
        assertEquals(birthYear, author.getBirthYear());
    }

    @Test
    @DisplayName("Constructor should throw exception when first name is null")
    void constructor_nullFirstName_throwsException() {
        // Arrange
        String lastName = "Orwell";
        String nationality = "British";

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Author(null, lastName, nationality, 1903));
    }

    @Test
    @DisplayName("Constructor should throw exception when last name is blank")
    void constructor_blankLastName_throwsException() {
        // Arrange
        String firstName = "George";
        String nationality = "British";

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Author(firstName, "   ", nationality, 1903));
    }

    @Test
    @DisplayName("Constructor should throw exception when nationality is null")
    void constructor_nullNationality_throwsException() {
        // Arrange
        String firstName = "George";
        String lastName = "Orwell";

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Author(firstName, lastName, null, 1903));
    }

    @Test
    @DisplayName("Constructor should throw exception when birth year is out of range")
    void constructor_invalidBirthYear_throwsException() {
        // Arrange
        String firstName = "George";
        String lastName = "Orwell";
        String nationality = "British";

        // Act & Assert
        assertAll(
                () -> assertThrows(IllegalArgumentException.class,
                        () -> new Author(firstName, lastName, nationality, 1899)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> new Author(firstName, lastName, nationality, 2026))
        );
    }

    // ---------- Business methods ----------

    @Test
    @DisplayName("getFullName should return first name and last name separated by space")
    void getFullName_returnsCorrectValue() {
        // Arrange
        Author author = new Author("George", "Orwell", "British", 1903);

        // Act
        String fullName = author.getFullName();

        // Assert
        assertEquals("George Orwell", fullName);
    }

    @Test
    @DisplayName("getAge should return correct age based on current year 2025")
    void getAge_returnsCorrectAge() {
        // Arrange
        Author author = new Author("George", "Orwell", "British", 1903);

        // Act
        int age = author.getAge();

        // Assert
        assertEquals(2025 - 1903, age);
    }

    // ---------- equals & hashCode ----------

    @Test
    @DisplayName("equals should return true for authors with same data")
    void equals_sameData_returnsTrue() {
        // Arrange
        Author author1 = new Author("George", "Orwell", "British", 1903);
        Author author2 = new Author("George", "Orwell", "British", 1903);

        // Act & Assert
        assertEquals(author1, author2);
        assertEquals(author1.hashCode(), author2.hashCode());
    }

    @Test
    @DisplayName("equals should return false when at least one field differs")
    void equals_differentData_returnsFalse() {
        // Arrange
        Author base = new Author("George", "Orwell", "British", 1903);

        Author differentName = new Author("Eric", "Orwell", "British", 1903);
        Author differentNationality = new Author("George", "Orwell", "Indian", 1903);
        Author differentBirthYear = new Author("George", "Orwell", "British", 1904);

        // Act & Assert
        assertAll(
                () -> assertNotEquals(base, differentName),
                () -> assertNotEquals(base, differentNationality),
                () -> assertNotEquals(base, differentBirthYear)
        );
    }

    @Test
    @DisplayName("equals should return false when compared with null or different type")
    void equals_nullOrDifferentType_returnsFalse() {
        // Arrange
        Author author = new Author("George", "Orwell", "British", 1903);

        // Act & Assert
        assertNotEquals(null, author);
        assertNotEquals(new Object(), author);
    }

    @Test
    @DisplayName("hashCode should be consistent across multiple calls")
    void hashCode_consistent() {
        // Arrange
        Author author = new Author("George", "Orwell", "British", 1903);

        // Act
        int firstHash = author.hashCode();
        int secondHash = author.hashCode();

        // Assert
        assertEquals(firstHash, secondHash);
    }

}