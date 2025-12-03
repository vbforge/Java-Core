package com.vbforge;

import java.util.Optional;

public class StudentManagementSystem {
    public static void main(String[] args) {

        System.out.println("=== Student Management System Demo ===\n");

        // Create a student group with capacity of 5
        StudentGroup group = new StudentGroup(5);

        // Create students
        Student student1 = new Student("Alice Johnson", 20, 9.5, "STU001");
        Student student2 = new Student("Bob Smith", 19, 8.2, "STU002");
        Student student3 = new Student("Charlie Brown", 21, 9.1, "STU003");
        Student student4 = new Student("Diana Prince", "STU004"); // Using second constructor

        // Add students to group
        System.out.println("Adding students:");
        System.out.println("Added Alice: " + group.addStudent(student1));
        System.out.println("Added Bob: " + group.addStudent(student2));
        System.out.println("Added Charlie: " + group.addStudent(student3));
        System.out.println("Added Diana: " + group.addStudent(student4));

        // Update Diana's grade
        student4.updateGrade(7.8);

        System.out.println("\nStudent count: " + group.getStudentsCount());

        // Find a student
        Student found = group.findStudentById("STU002");
        System.out.println("\nFound student STU002: " + found);

        // Check if students exist
        System.out.println("Has student STU001: " + group.isStudentExist("STU001"));
        System.out.println("Has student STU999: " + group.isStudentExist("STU999"));

        // Get average grade
        System.out.printf("\nGroup average grade: %.2f\n", group.getAverageGroupGrade());

        // Get excellent students
        Student[] excellentStudents = group.getExcellentStudents();
        System.out.println("\nExcellent students (grade >= 9.0):");
        for (Student s : excellentStudents) {
            System.out.println("  " + s);
        }

        // Get the oldest student
        Optional<Student> oldest = group.getOldestStudent();
        oldest.ifPresent(student -> System.out.println("\nOldest student: " + student));

        // Check student properties
        System.out.println("\nStudent properties:");
        System.out.println("Alice is excellent: " + student1.isExcellentStudent());
        System.out.println("Alice is adult: " + student1.isAdult());

        // Remove a student
        System.out.println("\nRemoving student STU002: " + group.removeStudent("STU002"));
        System.out.println("Student count after removal: " + group.getStudentsCount());

        // Try to find removed student
        Student removedStudent = group.findStudentById("STU002");
        System.out.println("Finding removed student STU002: " + removedStudent);

        // Demonstrate validation
        System.out.println("\n=== Validation Examples ===");
        try {
            new Student("", 15, -1.0, "");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected validation error: " + e.getMessage());
        }

        try {
            student1.updateGrade(15.0);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected grade validation error: " + e.getMessage());
        }

    }
}
