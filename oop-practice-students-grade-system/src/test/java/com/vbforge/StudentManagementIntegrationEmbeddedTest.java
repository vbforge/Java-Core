package com.vbforge;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Student Management Integration Test")
public class StudentManagementIntegrationEmbeddedTest {

    @ParameterizedTest
    @CsvSource({
            "Basic Group, Alice:20:9.5:STU001;Bob:19:8.2:STU002, 2, 8.85, 1",
            "Excellent Group, Alice:20:9.5:STU001;Bob:19:9.2:STU002;Charlie:21:9.8:STU003, 3, 9.5, 3",
            "Mixed Group, Alice:20:7.0:STU001;Bob:19:8.0:STU002;Charlie:21:9.5:STU003;Diana:18:6.5:STU004, 4, 7.75, 1"
    })
    @DisplayName("Test Complete Scenario")
    public void testCompleteScenario(String scenario, String studentsData, int expectedCount, double expectedAverage, int expectedExcellent) {

        StudentGroup group = new StudentGroup(10);

        // Parse and add students
        String[] students = studentsData.split(";");
        for (String studentInfo : students) {
            if (!studentInfo.trim().isEmpty()) {
                String[] parts = studentInfo.split(":");
                Student student = new Student(parts[0],
                        Integer.parseInt(parts[1]),
                        Double.parseDouble(parts[2]),
                        parts[3]);
                group.addStudent(student);
            }
        }

        assertEquals(expectedCount, group.getStudentsCount(),"Student count mismatch for scenario: " + scenario);
        assertEquals(expectedAverage, group.getAverageGroupGrade(), 0.01, "Average grade mismatch for scenario: " + scenario);
        assertEquals(expectedExcellent, group.getExcellentStudents().length, "Excellent students count mismatch for scenario: " + scenario);
    }

}