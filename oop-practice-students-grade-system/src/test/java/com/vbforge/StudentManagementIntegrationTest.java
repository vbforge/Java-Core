package com.vbforge;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Student Management Integration Test")
public class StudentManagementIntegrationTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/integration_test_scenario.csv", numLinesToSkip = 1)
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