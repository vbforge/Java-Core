package com.vbforge;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Student {

    private final String name;
    private final int age;
    private double averageGrade;
    private final String studentId;


    //specific constructor with all fields and kind of validation:
    public Student(String name, int age, double averageGrade, String studentId) {
        if (name == null || name.isEmpty() ||
            age < 16 || age > 100 ||
            averageGrade < 0.0 || averageGrade > 10.0 ||
            studentId == null || studentId.isEmpty()
        ) {
            throw new IllegalArgumentException();
        }

        this.name = name;
        this.age = age;
        this.averageGrade = averageGrade;
        this.studentId = studentId;
    }

    //constructor with default values for some fields:
    public Student(String name, String studentId) {
        if(name == null || name.isEmpty() ||
                studentId == null || studentId.isEmpty()){
            throw  new IllegalArgumentException();
        }

        this.name = name;
        this.studentId = studentId;
        this.averageGrade = 0.0;
        this.age = 18;
    }

    //some specific methods to check student state:

    public boolean isExcellentStudent(){
        return averageGrade >= 9.0;
    }

    public boolean isAdult(){
        return age >= 18;
    }

    public void updateGrade(double newGrade){
        if(newGrade < 0.0 || newGrade > 10.0){
            throw new IllegalArgumentException();
        }
        this.averageGrade = newGrade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return age == student.age &&
                Double.compare(averageGrade, student.averageGrade) == 0 &&
                Objects.equals(name, student.name) &&
                Objects.equals(studentId, student.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, averageGrade, studentId);
    }

    @Override
    public String toString() {
        return String.format("%s, %d, avg-grade: %.2f, id: %s",  name, age,  averageGrade,  studentId);
    }

}
