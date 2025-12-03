package com.vbforge;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test Student Class")
public class StudentTest {

    @ParameterizedTest
    @CsvSource({
            "Alice Johnson, 20, 9.5, STU001",
            "Bob Smith, 19, 8.2, STU002",
            "Charlie Brown, 21, 9.1, STU003",
            "Diana Prince, 18, 7.8, STU004",
            "Eve Wilson, 22, 10.0, STU005"
    })
    @DisplayName("Test Create Student")
    public void testValidStudentCreation(String name, int age, double grade, String studentId){
        assertDoesNotThrow(()->{
            Student student = new Student(name, age, grade, studentId);
            assertEquals(name, student.getName());
            assertEquals(age, student.getAge());
            assertEquals(grade, student.getAverageGrade(), 0.01);
            assertEquals(studentId, student.getStudentId());
        });
    }

    //took data for test from csv file
    @ParameterizedTest
    @CsvFileSource(resources = "/students_list.csv", numLinesToSkip = 1)
    @DisplayName("Test Create Student from csv file")
    public void testValidStudentCreationFromFile(String name, int age, double grade, String studentId){
        assertDoesNotThrow(()->{
            Student student = new Student(name, age, grade, studentId);
            assertEquals(name, student.getName());
            assertEquals(age, student.getAge());
            assertEquals(grade, student.getAverageGrade(), 0.01);
            assertEquals(studentId, student.getStudentId());
        });
    }

    @ParameterizedTest
    @CsvSource({
            "EMPTY_STRING, 20, 9.5, STU001",
            "NULL_VALUE, 20, 9.5, STU001",
            "Alice, 15, 9.5, STU001",
            "Alice, 101, 9.5, STU001",
            "Alice, 20, -0.1, STU001",
            "Alice, 20, 10.1, STU001",
            "Alice, 20, 9.5, EMPTY_STRING",
            "Alice, 20, 9.5, NULL_VALUE"
    })
    @DisplayName("Test Create Student with invalid params")
    public void testInvalidStudentCreation(String name, int age, double grade, String studentId) {

        assertThrows(IllegalArgumentException.class, () -> {

            String actualName = name.equals("NULL_VALUE") ? null : name.equals("EMPTY_STRING") ? "" : name;
            String actualStudentId = studentId.equals("NULL_VALUE") ? null : studentId.equals("EMPTY_STRING") ? "" : studentId;

            new Student(actualName, age, grade, actualStudentId);
        });
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/students_list_invalid.csv", numLinesToSkip = 1)
    @DisplayName("Test Create Student with invalid params from csv file")
    public void testInvalidStudentCreationFromFile(String name, int age, double grade, String studentId) {

        assertThrows(IllegalArgumentException.class, () -> {

            String actualName = name.equals("NULL_VALUE") ? null : name.equals("EMPTY_STRING") ? "" : name;
            String actualStudentId = studentId.equals("NULL_VALUE") ? null : studentId.equals("EMPTY_STRING") ? "" : studentId;

            new Student(actualName, age, grade, actualStudentId);
        });
    }

    @ParameterizedTest
    @CsvSource({
            "Alice Johnson, STU001",
            "Bob Smith, STU002",
            "Charlie Brown, STU003"
    })
    @DisplayName("Test two parameter Constructor")
    public void testTwoParameterConstructor(String name, String studentId) {
        Student student = new Student(name, studentId);
        assertEquals(name, student.getName());
        assertEquals(studentId, student.getStudentId());
        assertEquals(18, student.getAge());
        assertEquals(0.0, student.getAverageGrade(), 0.001);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/student_list_two_params.csv", numLinesToSkip = 1)
    @DisplayName("Test two parameter Constructor from csv file")
    public void testTwoParameterConstructorFromFile(String name, String studentId) {
        Student student = new Student(name, studentId);
        assertEquals(name, student.getName());
        assertEquals(studentId, student.getStudentId());
        assertEquals(18, student.getAge());
        assertEquals(0.0, student.getAverageGrade(), 0.001);
    }

    @ParameterizedTest
    @CsvSource({
            "Alice, STU001, 8.0, 9.5, true",
            "Bob, STU002, 7.0, 10.0, true",
            "Charlie, STU003, 6.0, -0.1, false",
            "Diana, STU004, 5.0, 10.1, false",
            "Eve, STU005, 9.0, 0.0, true"
    })
    @DisplayName("Test Update Grade")
    public void testUpdateGrade(String name, String studentId, double initialGrade, double newGrade, boolean shouldSucceed) {
        Student student = new Student(name, 20, initialGrade, studentId);

        if (shouldSucceed) {
            assertDoesNotThrow(() -> student.updateGrade(newGrade));
            assertEquals(newGrade, student.getAverageGrade(), 0.001);
        } else {
            assertThrows(IllegalArgumentException.class, () -> student.updateGrade(newGrade));
            assertEquals(initialGrade, student.getAverageGrade(), 0.001); // Should remain unchanged
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/grade_updates.csv", numLinesToSkip = 1)
    @DisplayName("Test Update Grade from csv file")
    public void testUpdateGradeFromFile(String name, String studentId, double initialGrade, double newGrade, boolean shouldSucceed) {
        Student student = new Student(name, 20, initialGrade, studentId);

        if (shouldSucceed) {
            assertDoesNotThrow(() -> student.updateGrade(newGrade));
            assertEquals(newGrade, student.getAverageGrade(), 0.001);
        } else {
            assertThrows(IllegalArgumentException.class, () -> student.updateGrade(newGrade));
            assertEquals(initialGrade, student.getAverageGrade(), 0.001); // Should remain unchanged
        }
    }

    @ParameterizedTest
    @CsvSource({
            "Alice, 20, 9.5, STU001, true, true",
            "Bob, 17, 8.2, STU002, false, false",
            "Charlie, 21, 9.0, STU003, true, true",
            "Diana, 16, 8.9, STU004, false, false"
    })
    @DisplayName("Test Student Properties")
    public void testStudentProperties(String name, int age, double grade, String studentId, boolean isExcellent, boolean isAdult) {
        Student student = new Student(name, age, grade, studentId);
        assertEquals(isExcellent, student.isExcellentStudent());
        assertEquals(isAdult, student.isAdult());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/student_properties.csv", numLinesToSkip = 1)
    @DisplayName("Test Student Properties from csv file")
    public void testStudentPropertiesFromFile(String name, int age, double grade, String studentId, boolean isExcellent, boolean isAdult) {
        Student student = new Student(name, age, grade, studentId);
        assertEquals(isExcellent, student.isExcellentStudent());
        assertEquals(isAdult, student.isAdult());
    }

    @Test
    @DisplayName("Test Equals AndHashCode")
    public void testEqualsAndHashCode() {
        Student student1 = new Student("John Doe", 20, 8.5, "STU001");
        Student student2 = new Student("John Doe", 20, 8.5, "STU001");
        Student student3 = new Student("Jane Doe", 20, 8.5, "STU002");

        assertEquals(student1, student2);
        assertNotEquals(student1, student3);
        assertEquals(student1.hashCode(), student2.hashCode());
    }

    @Test
    @DisplayName("Test toString")
    public void testToString() {
        Student student = new Student("John Doe", 20, 8.75, "STU001");
        String expected = "John Doe, 20, avg-grade: 8.75, id: STU001";
        assertEquals(expected, student.toString());
    }


}