# Java OOP Mastery Guide

> A comprehensive, production-ready educational repository for mastering Object-Oriented Programming in Java 17+

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://openjdk.java.net/)
[![Maven](https://img.shields.io/badge/Maven-3.8+-blue.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-Educational-green.svg)](LICENSE)

---

## 📖 Overview

**Java OOP Mastery Guide** is a meticulously crafted learning resource that takes from foundational Java concepts to advanced object-oriented design patterns. Each package contains working code examples, practical demonstrations, and real-world applications of OOP principles.

### 🎯 Who Is This For?

- **Students** learning Java and OOP for the first time
- **Developers** transitioning from procedural to object-oriented programming
- **Interview Candidates** preparing for technical assessments
- **Educators** seeking structured teaching materials
- **Self-learners** building a strong OOP foundation

---

## 🗂️ Project Structure

### 1. 🔰 **Fundamentals** - Core Java Building Blocks

Master the essential building blocks of Java programming.

```
fundamentals/
├── classes/              → Class definitions, objects, instances
├── fields/               → Field declarations, initialization, scope
├── constructors/         → Constructor types, chaining, best practices
├── methods/              → Method overloading, signatures, parameters
├── modifiers/            → Access control (public, private, protected, default)
└── keywords/
    ├── this_keyword/     → Referencing current instance
    ├── super_keyword/    → Accessing parent class members
    ├── static_keyword/   → Class-level members, static blocks, inheritance
    └── final_keyword/    → Constants, immutability, final classes
```

**Key Concepts**: Class anatomy, instance vs class members, access control, keyword usage

---

### 2. 🏛️ **OOP Pillars** - The Four Foundations

Deep dive into the four pillars that define object-oriented programming.

```
oop_pillars/
├── encapsulation/
│   ├── basic/            → Data hiding, getters/setters, access control
│   └── advanced/         → Field hiding, information expert principle
├── inheritance/
│   ├── basic/            → IS-A relationships, code reuse, hierarchies
│   └── advanced/         → Covariant return types, multilevel inheritance
├── polymorphism/
│   ├── basic/            → Runtime polymorphism, dynamic dispatch
│   ├── override/         → Method overriding rules, @Override annotation
│   ├── binding/          → Static vs dynamic binding, method resolution
│   └── casting/          → Upcasting, downcasting, instanceof checks
└── abstraction/
    ├── abstract_classes/ → Abstract methods, partial implementations
    └── interfaces/       → Contracts, multiple inheritance, default methods
```

**Key Concepts**: Data hiding, code reuse, runtime behavior, interface contracts

---

### 3. 🔗 **Relationships** - Object Interactions

Understand how objects relate and interact with each other.

```
relationships/
├── association/                                → Loose coupling, bidirectional relationships
├── aggregation/                                → HAS-A relationships, shared ownership
├── composition/                                → Strong ownership, lifecycle dependency
├── composition_aggregation_delegation/         → complex example 
└── delegation/                                 → Behavior delegation, composition over inheritance
```

**Key Concepts**: UML relationships, lifecycle management, coupling strength

---

### 4. 🎨 **Design Principles** - SOLID & Best Practices

Apply industry-standard design principles for maintainable code.

```
design_principles/
├── solid/                → SOLID principles (SRP, OCP, LSP, ISP, DIP)
├── cohesion/             → High cohesion, single responsibility
├── coupling/             → Loose coupling, dependency management
└── patterns/             → separation of concerns, service/repository layers, constructor chaining
    
```

**Key Concepts**: SOLID principles, high cohesion, low coupling, design patterns

---

### 5. 💎 **Core Concepts** - Essential Java Knowledge

Master critical Java concepts that every developer must know.

```
core_concepts/
├── immutability/         → Immutable classes, defensive copying, benefits
├── parameter_passing/    → Pass-by-value semantics, reference behavior
├── type_conversion/      → Wrapper classes, autoboxing, primitive conversions
└── object_methods/
    ├── equals/                 → Object equality, contracts, best practices
    ├── equals_hashcode/        → Object equality, contracts, best practices example
    └── hashcode/               → Hash codes, collections, equals/hashCode contract
```

**Key Concepts**: Immutability, value semantics, object contracts

---

### 6. ⚠️ **Exception Handling** - Error Management

Handle errors gracefully and build robust applications.

```
exception_handling/             → Try-catch blocks, custom exceptions, best practices
├── advanced/                 
├── custom/        
├── handler/        
├── service/        
├── simple_examples/        
└── util/ 
```

**Key Concepts**: Checked vs unchecked exceptions, exception hierarchy, error recovery

---

### 7. 🚀 **Advanced Topics** - Deep Dives *(in process)*

Explore advanced Java features and JVM internals.

```
advanced_topics/
├── access_control/       → In-depth access modifier behavior
├── string_pool/          → String interning, memory optimization
├── integer_cache/        → Integer caching (-128 to 127), autoboxing
├── garbage_collection/   → GC algorithms, memory management, tuning
├── classloaders/         → Bootstrap, Extension, Application loaders
└── security/             → SecurityManager, bytecode verification, sandboxing
```

**Key Concepts**: JVM internals, memory management, security mechanisms

---

### 8. 📝 **Assignments** - Practice Makes Perfect

Exercises to reinforce  learning.

```
assignments/
├── assignment1/          
├── assignment2/          
├── assignment3/          
├── assignment4/          
└── assignment5/          
```

**Key Concepts**: Hands-on practice, real-world scenarios, progressive difficulty

---

### 9. 🛠️ **Common** - Shared Resources

Utilities and demonstrations used across the project.

```
common/
├── theory/               → some theoretical utilities
└── demo/                 → some working demonstrations
```

---


### Prerequisites

- **Java 17+**
- **Maven 3.8+**
- **IntelliJ IDEA**

---

## 📚 Key Concepts Reference

### The Four Pillars of OOP

| Pillar | Definition | Location |
|--------|-----------|----------|
| **Encapsulation** | Bundling data with methods that operate on that data | `oop_pillars/encapsulation/` |
| **Inheritance** | Mechanism for code reuse through parent-child relationships | `oop_pillars/inheritance/` |
| **Polymorphism** | Ability of objects to take multiple forms at runtime | `oop_pillars/polymorphism/` |
| **Abstraction** | Hiding complex implementation details behind simple interfaces | `oop_pillars/abstraction/` |

### SOLID Principles

| Principle | Definition | Package |
|-----------|-----------|---------|
| **S**RP | Single Responsibility Principle | `design_principles/solid/` |
| **O**CP | Open/Closed Principle | `design_principles/solid/` |
| **L**SP | Liskov Substitution Principle | `design_principles/solid/` |
| **I**SP | Interface Segregation Principle | `design_principles/solid/` |
| **D**IP | Dependency Inversion Principle | `design_principles/solid/` |

### Object Relationships

| Relationship | Strength | Lifecycle | Package |
|--------------|----------|-----------|---------|
| **Association** | Weak | Independent | `relationships/association/` |
| **Aggregation** | Medium | Independent | `relationships/aggregation/` |
| **Composition** | Strong | Dependent | `relationships/composition/` |
| **Delegation** | Flexible | Varies | `relationships/delegation/` |

---

## 🔍 Code Examples

### Encapsulation Example

```java
// From oop_pillars/encapsulation/basic/
public class BankAccount {
    private double balance;  // Encapsulated field
    
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }
    
    public double getBalance() {
        return balance;
    }
}
```

### Polymorphism Example

```java
// From oop_pillars/polymorphism/basic/
interface Shape {
    double calculateArea();
}

class Circle implements Shape {
    private double radius;
    
    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}
```

### Composition Example

```java
// From relationships/composition/
class Engine {
    void start() { /* ... */ }
}

class Car {
    private final Engine engine = new Engine();  // Composition
    
    void startCar() {
        engine.start();
    }
}
```

---

## 📦 Dependencies

```xml
<dependencies>
    <!-- Testing -->
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
    </dependency>
    
    <!-- Utilities ( Lombok (optional) ) -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>
    
    <!-- Database (optional) -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
    </dependency>
</dependencies>
```

---

## 🗺️ Roadmap

### 🚧 In Progress
- Advanced topics (GC, ClassLoaders, Security)
- More design pattern examples
- Integration with databases

### 📋 Planned
- Concurrent programming examples
- Functional programming with Java
- Microservices patterns
- Performance optimization techniques

---
