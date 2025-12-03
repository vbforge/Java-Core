# Java Core 

🎯 **Comprehensive Java Core learning projects (modules) with practical examples and tests**

🎓 **Learning Breakdown:**

- Fundamentals (variables, operators, control flow)
- Core OOP concepts
- Exception handling and generics
- Modern Java features (lambdas, streams, functional programming)
- I/O and serialization
- Advanced features (annotations, reflection)
- Concurrency and multithreading
- Design patterns and best practices

## 📚 Modules


| Module                                                                     | Description                                                                                        | #TAGS                     |
|----------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------|---------------------------|
| [oop-practice-students-grade-system](./oop-practice-students-grade-system) | Class creation (Constructors), getters/setters, arrays operations, validation                      | OOP                       |
| basics-variables-and-types                                                 | Primitive types, type casting, literals, constants, variable scope                                 | BASICS                    |
| basics-operators-and-expressions                                           | Arithmetic, logical, bitwise, ternary operators, operator precedence                               | BASICS                    |
| control-flow-statements                                                    | if-else, switch-case (enhanced), loops (for, while, do-while), break/continue                      | CONTROL-FLOW              |
| strings-and-text-processing                                                | String manipulation, StringBuilder/StringBuffer, String pool, text formatting, regular expressions | STRINGS                   |
| arrays-and-collections-basics                                              | Array operations, ArrayList, LinkedList, HashSet, HashMap, iteration techniques                    | COLLECTIONS               |
| oop-inheritance-and-polymorphism                                           | Inheritance, method overriding, super keyword, polymorphism, abstract classes                      | OOP                       |
| oop-interfaces-and-abstraction                                             | Interface definition, multiple inheritance, default/static methods, abstract design                | OOP                       |
| oop-encapsulation-patterns                                                 | Access modifiers, package structure, data hiding, immutability, record classes                     | OOP                       |
| exception-handling                                                         | try-catch-finally, custom exceptions, checked vs unchecked, try-with-resources, exception chains   | EXCEPTIONS                |
| generics-fundamentals                                                      | Generic classes, generic methods, bounded type parameters, wildcards (?, extends, super)           | GENERICS                  |
| collections-framework-advanced                                             | Queue, Deque, TreeSet, TreeMap, PriorityQueue, custom comparators, Collections utility             | COLLECTIONS               |
| enums-and-nested-classes                                                   | Enum with fields/methods, inner classes, static nested classes, anonymous classes                  | OOP, ADVANCED             |
| functional-interfaces                                                      | Lambda expressions, method references, Predicate, Function, Consumer, Supplier                     | FUNCTIONAL                |
| streams-api-basics                                                         | Stream creation, filter, map, sorted, collect, forEach, reduce                                     | STREAMS, FUNCTIONAL       |
| streams-api-advanced                                                       | flatMap, groupingBy, partitioningBy, parallel streams, custom collectors                           | STREAMS, ADVANCED         |
| optional-and-null-handling                                                 | Optional creation, isPresent/isEmpty, orElse/orElseGet/orElseThrow, map/flatMap                    | FUNCTIONAL                |
| date-time-api                                                              | LocalDate, LocalTime, LocalDateTime, ZonedDateTime, Period, Duration, formatting/parsing           | DATE-TIME                 |
| file-io-basics                                                             | File reading/writing, BufferedReader/Writer, try-with-resources, Path and Files API                | I/O                       |
| file-io-nio                                                                | NIO2 Files/Paths, DirectoryStream, WatchService, file attributes, symbolic links                   | I/O, NIO                  |
| serialization                                                              | Serializable, ObjectInputStream/ObjectOutputStream, transient, serialVersionUID                    | I/O, SERIALIZATION        |
| annotations                                                                | Built-in annotations, custom annotations, retention policies                                       | ANNOTATIONS, ADVANCED     |
| reflection-api                                                             | Class introspection, accessing fields/methods, dynamic invocation, annotation processing           | REFLECTION, ADVANCED      |
| multithreading-basics                                                      | Thread creation, lifecycle, synchronization, volatile keyword                                      | CONCURRENCY               |
| multithreading-advanced                                                    | ExecutorService, ThreadPool, Callable/Future, CountDownLatch, CyclicBarrier, Semaphore             | CONCURRENCY, ADVANCED     |
| concurrent-collections                                                     | ConcurrentHashMap, CopyOnWriteArrayList, BlockingQueue, atomic classes                             | CONCURRENCY, COLLECTIONS  |
| design-patterns                                                            | Singleton, Factory, Builder, Observer, Strategy, Decorator                                         | DESIGN-PATTERNS, ADVANCED |
| best-practices-and-solid                                                   | SOLID principles, clean code, code smells, refactoring                                             | BEST-PRACTICES, ADVANCED  |


### 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- JUnit 5
- Maven 3.6+

### 📦 Recommended Dependencies by Module Category

| Module Category     | Recommended Dependencies                              |
|---------------------|-------------------------------------------------------|
| All modules         | junit-jupiter, junit-jupiter-params, assertj-core     |
| OOP modules         | lombok (optional, for @Getter/@Setter/@Builder)       |
| Collections/Streams | guava (optional, for additional collection utilities) |
| File I/O            | commons-io, slf4j-api, logback-classic                |
| Serialization       | jackson-databind, jackson-datatype-jsr310             |
| Strings/Text        | commons-lang3                                         |
| Multithreading      | mockito-core (for testing concurrent code)            |
| All testing         | mockito-core, mockito-junit-jupiter                   |

#### How dependencies management works under the hood:
```
Must declare each dependency in the child POM, but without the version tag.

    # How it works:

Parent POM (<dependencyManagement>):
 - Defines which versions to use
 - Does NOT add dependencies to child modules automatically
 - Acts like a "version catalog"

Child POM (<dependencies>):
 - Declares which dependencies it actually needs
 - Inherits the version from parent's <dependencyManagement>
 - Each module can choose different dependencies

    # Example:
 - Parent says: "If anyone wants JUnit, use version 5.10.1"
 - Child 1 says: "I want JUnit" → Gets version 5.10.1 
 - Child 2 says: "I don't need JUnit" → Doesn't get it 
 - Child 3 says: "I want JUnit AND Mockito" → Gets both with correct versions 
```
#### Properties specification:
**No need to specify properties in child pom.xml - inherited from parent pom.xml**
 
 
 
 
 
  



