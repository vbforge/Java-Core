# Student Management System (Easy Level)

- **Focus:** Basic class creation, simple arrays, basic validation  
- **Key concepts:** Constructors, getters/setters, simple array operations
- **Estimated time:** 30 minutes

#### This task designed to give practice with:

- Class design and relationships
- Array manipulation at different complexity levels
- Input validation strategies
- Method overriding and inheritance
- Business logic implementation
- Error handling patterns

## Description

Develop a `Student` class and a `StudentGroup` class to manage student information and group operations.

### Task Requirements:

#### Develop `Student` class with the following content:

- **Private fields:**
  - `String name` - student's full name
  - `int age` - student's age
  - `double averageGrade` - student's average grade (0.0 to 10.0)
  - `String studentId` - unique student identifier

- **Constructors:**
  - Constructor with all four parameters (name, age, averageGrade, studentId)
  - Constructor with name and studentId only (sets age to 18 and averageGrade to 0.0)
  - Should throw `IllegalArgumentException` if:
    - name is null or empty
    - age is less than 16 or greater than 100
    - averageGrade is less than 0.0 or greater than 10.0
    - studentId is null or empty

- **Methods:**
  - Getter methods for all fields
  - `isExcellentStudent()` - returns true if averageGrade >= 9.0
  - `isAdult()` - returns true if age >= 18
  - `updateGrade(double newGrade)` - updates the average grade, should validate the grade
  - Override `equals()`, `hashCode()`, and `toString()` methods

#### Develop `StudentGroup` class with the following content:

- **Private field:**
  - `Student[] students` - array to store students

- **Constructors:**
  - Constructor that creates an empty array of given capacity
  - Should throw `IllegalArgumentException` if capacity <= 0

- **Methods:**
  - `addStudent(Student student)` - adds student to first available position, returns true if successful
  - `removeStudent(String studentId)` - removes student by ID, returns true if found and removed
  - `findStudent(String studentId)` - returns Student object or null if not found
  - `getStudentCount()` - returns actual number of students in the group
  - `getAverageGroupGrade()` - calculates and returns average grade of all students
  - `getExcellentStudents()` - returns array of students with grade >= 9.0
  - `getOldestStudent()` - returns the oldest student in the group
  - `hasStudent(String studentId)` - returns true if student exists in group

---

