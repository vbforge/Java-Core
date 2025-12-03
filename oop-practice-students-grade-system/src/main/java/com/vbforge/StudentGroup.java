package com.vbforge;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

public class StudentGroup {

    private final Student [] students;

    //constructor with specified size of array
    public StudentGroup(int size) {
        if(size <= 0){
            throw new IllegalArgumentException();
        }
        this.students = new Student[size];
    }

    //logic methods to work with for management

    //add student to the group
    public boolean addStudent(Student student){
        if(student == null){
            return false;
        }
        for (int i = 0; i < students.length; i++) {
            if(students[i] == null){
                students[i] = student;
                return true;
            }
        }
        return false;
    }

    //remove student from the group
    public boolean removeStudent(String studentId){
        if(studentId == null || studentId.isEmpty()){
            return false;
        }
        for (int i = 0; i < students.length; i++) {
            Student student = students[i];
            if(student != null && studentId.equals(student.getStudentId())){
                students[i] = null;
                return true;
            }
        }
        return false;
    }

    //find student by ID
    public Student findStudentById(String studentId){
        if(studentId == null){
            return null;
        }
        for (Student student : students) {
            if(student != null && studentId.equals(student.getStudentId())){
                return student;
            }
        }
        return null;
    }

    //get amount of students in a group
    public int getStudentsCount(){
        int count = 0;
        for (Student student : students) {
            if(student != null){
                count++;
            }
        }
        return count;
    }

    //get avg group grade
    public double getAverageGroupGrade(){
        double allGrade = 0.0;
        int count = 0;
        for (Student student : students) {
            if(student != null){
                allGrade += student.getAverageGrade();
                count++;
            }
        }
        return count > 0 ? allGrade / count : 0.0;
    }

    //get excellent students
    public Student[] getExcellentStudents(){
        return Arrays.stream(students)
                .filter(Objects::nonNull)
                .filter(Student::isExcellentStudent)
                .toArray(Student[]::new);
    }

    //get the oldest student
    public Optional<Student> getOldestStudent(){
        return Arrays.stream(students)
                .filter(Objects::nonNull)
                .max(Comparator.comparingInt(Student::getAge));
    }

    //check if student with specified ID is exist
    public boolean isStudentExist(String studentId){
        if(studentId == null){
            return false;
        }
        for (Student student : students) {
            if(student != null && studentId.equals(student.getStudentId())){
                return true;
            }
        }
        return false;
    }



}
