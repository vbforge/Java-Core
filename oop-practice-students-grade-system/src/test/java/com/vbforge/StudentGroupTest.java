package com.vbforge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName("Test StudentGroup Class")
public class StudentGroupTest {

    private StudentGroup studentGroup;

    private Student student1;
    private Student student2;
    private Student student3;

    @BeforeEach
    public void setup(){
        studentGroup = new StudentGroup(5);
        student1 = new Student("Bob", 20, 9.5,"STUD001");
        student2 = new Student("Tom", 22, 9.7,"STUD002");
        student3 = new Student("Harry", 19, 8.5,"STUD003");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10, 100})
    @DisplayName("Test Valid Group Creation")
    public void testValidGroupCreation(int size){
        assertDoesNotThrow(() -> {
            new StudentGroup(size);
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -10})
    @DisplayName("Test Invalid Group Creation")
    public void testInvalidGroupCreation(int size) {
        assertThrows(IllegalArgumentException.class, () -> new StudentGroup(size));
    }

    @ParameterizedTest
    @CsvSource({
            "TestStudent1, 20, 8.5, TEST001",
            "TestStudent2, 19, 9.2, TEST002",
            "TestStudent3, 21, 7.8, TEST003"
    })
    @DisplayName("Test Add Students")
    public void testAddStudents(String name, int age, double grade, String studentId) {
        Student student = new Student(name, age, grade, studentId);
        assertTrue(studentGroup.addStudent(student));
        assertEquals(1, studentGroup.getStudentsCount());
        assertTrue(studentGroup.isStudentExist(studentId));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/students_list.csv", numLinesToSkip = 1)
    @DisplayName("Test Add Students from csv file")
    public void testAddStudentsFromCsvFile(String name, int age, double grade, String studentId) {
        Student student = new Student(name, age, grade, studentId);
        assertTrue(studentGroup.addStudent(student));
        assertEquals(1, studentGroup.getStudentsCount());
        assertTrue(studentGroup.isStudentExist(studentId));
    }

    @Test
    @DisplayName("Test Add Student Capacity Limit")
    public void testAddStudentCapacityLimit() {
        StudentGroup smallGroup = new StudentGroup(2);

        assertTrue(smallGroup.addStudent(student1));
        assertTrue(smallGroup.addStudent(student2));
        assertFalse(smallGroup.addStudent(student3)); // Should fail - capacity reached

        assertEquals(2, smallGroup.getStudentsCount());
    }

    @Test
    @DisplayName("Test Add Null Student")
    public void testAddNullStudent() {
        assertFalse(studentGroup.addStudent(null));
        assertEquals(0, studentGroup.getStudentsCount());
    }

    @ParameterizedTest
    @CsvSource({
            "STUD001, true",
            "STU999, false",
            "NULL_VALUE, false"
    })
    @DisplayName("Test Remove Student")
    public void testRemoveStudent(String studentId, boolean expectedResult) {
        studentGroup.addStudent(student1);

        // Handle the special NULL_VALUE case
        // can't pass actual null in CSV data, the test uses the string "NULL_VALUE" as a placeholder, then converts it to actual null
        String actualStudentId = studentId.equals("NULL_VALUE") ? null : studentId;
        boolean result = studentGroup.removeStudent(actualStudentId);
        assertEquals(expectedResult, result);

        if (expectedResult) {
            assertEquals(0, studentGroup.getStudentsCount());
            assertFalse(studentGroup.isStudentExist("STU001"));
        }

        //iteration1: removeStudent("STU001") successfully finds and removes the student → returns true
        //assertEquals(true, true)
        //Additional checks run because expectedResult is true: Count is now 0 (student was removed), Student no longer exists in group

        //iteration2: removeStudent("STU999") tries to remove non-existent student → returns false
        //Assertion passes: assertEquals(false, false)
        //Additional checks are skipped (expectedResult is false)

        //iteration3: actualStudentId = null (the special case conversion)
        //removeStudent(null) handles null parameter → returns false
        //Assertion passes: assertEquals(false, false)
        //Additional checks are skipped
    }

    @ParameterizedTest
    @CsvSource({
            "STUD001, Bob",
            "STU999, null",
            "NULL_VALUE, null"
    })
    @DisplayName("Test Find Student")
    public void testFindStudent(String studentId, String expectedName) {
        studentGroup.addStudent(student1);

        // Handle the special NULL_VALUE case
        String actualStudentId = studentId.equals("NULL_VALUE") ? null : studentId;
        Student found = studentGroup.findStudentById(actualStudentId);

        if (expectedName.equals("null")) {
            assertNull(found);
        } else {
            assertNotNull(found);
            assertEquals(expectedName, found.getName());
        }
    }

    @ParameterizedTest
    @CsvSource({
            "Alice:20:8.0:STU001;Bob:19:6.0:STU002, 7.0",
            "Charlie:21:10.0:STU003, 10.0",
            "Diana:18:7.0:STU004;Eve:22:9.0:STU005;Frank:20:8.0:STU006, 8.0",
            "EMPTY_GROUP, 0.0"
    })
    @DisplayName("Test Average Grade Calculation")
    public void testAverageGradeCalculation(String studentData, double expectedAverage) {
        // Handle empty group case
        if (studentData.equals("EMPTY_GROUP")) {
            assertEquals(expectedAverage, studentGroup.getAverageGroupGrade(), 0.01);
            return;
        }

        // Parse student data: "name1:age1:grade1:id1;name2:age2:grade2:id2;..."
        String[] students = studentData.split(";");

        for (String studentInfo : students) {
            if (!studentInfo.trim().isEmpty()) {
                String[] parts = studentInfo.split(":");
                String name = parts[0];
                int age = Integer.parseInt(parts[1]);
                double grade = Double.parseDouble(parts[2]);
                String id = parts[3];

                studentGroup.addStudent(new Student(name, age, grade, id));
            }
        }

        assertEquals(expectedAverage, studentGroup.getAverageGroupGrade(), 0.01);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/group_average_test.csv", numLinesToSkip = 1)
    @DisplayName("Test Average Grade Calculation from csv file")
    public void testAverageGradeCalculationFromCsvFile(String studentData, double expectedAverage) {
        // Handle empty group case
        if (studentData.equals("EMPTY_GROUP")) {
            assertEquals(expectedAverage, studentGroup.getAverageGroupGrade(), 0.01);
            return;
        }

        // Parse student data: "name1:age1:grade1:id1;name2:age2:grade2:id2;..."
        String[] students = studentData.split(";");

        for (String studentInfo : students) {
            if (!studentInfo.trim().isEmpty()) {
                String[] parts = studentInfo.split(":");
                String name = parts[0];
                int age = Integer.parseInt(parts[1]);
                double grade = Double.parseDouble(parts[2]);
                String id = parts[3];

                studentGroup.addStudent(new Student(name, age, grade, id));
            }
        }

        assertEquals(expectedAverage, studentGroup.getAverageGroupGrade(), 0.01);
    }

    @Test
    @DisplayName("Test Get Excellent Students")
    public void testGetExcellentStudents() {
        studentGroup.addStudent(student1);
        studentGroup.addStudent(student2);
        studentGroup.addStudent(student3);

        Student[] excellent = studentGroup.getExcellentStudents();
        assertEquals(2, excellent.length);

        // Check that both excellent students are in the array
        boolean ex1 = false, ex2 = false;
        for (Student s : excellent) {
            if ("Bob".equals(s.getName())) ex1 = true;
            if ("Tom".equals(s.getName())) ex2 = true;
        }
        assertTrue(ex1 && ex2);
    }

    @Test
    @DisplayName("Test Get Oldest Student")
    public void testGetOldestStudent() {
        studentGroup.addStudent(student1); // age 20
        studentGroup.addStudent(student2); // age 22
        studentGroup.addStudent(student3); // age 19

        Optional<Student> oldest = studentGroup.getOldestStudent();
        assertTrue(oldest.isPresent());
        assertEquals("Tom", oldest.get().getName());
        assertEquals(22, oldest.get().getAge());
    }

    @Test
    @DisplayName("Test Get Oldest Student Empty Group")
    public void testGetOldestStudentEmptyGroup() {
        Optional<Student> oldest = studentGroup.getOldestStudent();
        assertFalse(oldest.isPresent());
    }

    @Test
    @DisplayName("Test Empty Group Operations")
    public void testEmptyGroupOperations() {
        assertEquals(0, studentGroup.getStudentsCount());
        assertEquals(0.0, studentGroup.getAverageGroupGrade(), 0.001);
        assertEquals(0, studentGroup.getExcellentStudents().length);
        assertFalse(studentGroup.getOldestStudent().isPresent());
        assertFalse(studentGroup.isStudentExist("STU001"));
        assertNull(studentGroup.findStudentById("STU001"));
        assertFalse(studentGroup.removeStudent("STU001"));
    }




}