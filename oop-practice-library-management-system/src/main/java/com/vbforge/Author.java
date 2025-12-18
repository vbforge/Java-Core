package com.vbforge;

import java.util.Objects;

public class Author {

    private static final int CURRENT_YEAR = 2025;
    private static final int STARTED_YEAR = 1900;

    private final String firstName;       //author's first name
    private final String lastName;        //author's last name
    private final String nationality;     //author's nationality
    private final int birthYear;          //author's birth year

    /*Constructor with all parameters
    Validate that names are not null/empty and birthYear is reasonable (1900-2025)*/
    public Author(String firstName, String lastName, String nationality, int birthYear) {
        if(firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("FirstName cannot be null or blank");
        }
        if(lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("LastName cannot be null or blank");
        }
        if(nationality == null || nationality.isBlank()) {
            throw new IllegalArgumentException("Nationality cannot be null or blank");
        }
        if(birthYear < STARTED_YEAR || birthYear > CURRENT_YEAR) {
            throw new IllegalArgumentException("BirthYear must be between 1900 and 2025");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationality = nationality;
        this.birthYear = birthYear;
    }

    /*Getter methods*/
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNationality() {
        return nationality;
    }

    public int getBirthYear() {
        return birthYear;
    }

    //getFullName(): firstName + " " + lastName
    public String getFullName() {
        return firstName + " " + lastName;
    }

    //getAge() (assuming current year is 2025)
    public int getAge() {
        return CURRENT_YEAR - birthYear;
    }

    //Override equals(), hashCode() with immutable fields to prevent inconsistency methods
    /*Safe for HashSet / HashMap
    Immutable fields
    Stable hash code
    No mutation problems*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author author)) return false;

        return birthYear == author.birthYear
                && Objects.equals(firstName, author.firstName)
                && Objects.equals(lastName, author.lastName)
                && Objects.equals(nationality, author.nationality);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, nationality, birthYear);
    }

    //toString()
    @Override
    public String toString() {
        return "Author{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nationality='" + nationality + '\'' +
                ", birthYear=" + birthYear +
                '}';
    }
}
