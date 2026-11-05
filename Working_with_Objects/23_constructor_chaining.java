/*
 * Working with Objects: Constructor Chaining
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What is Constructor Chaining?
///   - Example
/// ============================================================================


/// ### Topic 1: What is Constructor Chaining? ###
/// Constructor chaining means calling one constructor from another constructor using `this()` — it
/// helps reduce code duplication.


/// ### Topic 2: Example ###

class Student10 {
    String name;
    int age;

    // Default constructor
    Student10() {
        this("Unknown", 0); // Chaining to parameterized constructor
    }

    // Constructor with one parameter
    Student10(String name) {
        this(name, 0); // Chaining to two-parameter constructor
    }

    // Constructor with two parameters
    Student10(String name, int age) {
        this.name = name;
        this.age = age;
    }

    void display() {
        System.out.println("Name: " + name + ", Age: " + age);
    }
}

public class Main {
    public static void main(String[] args) {
        Student10 s1 = new Student10();
        Student10 s2 = new Student10("Noman");
        Student10 s3 = new Student10("Tortilla", 35);

        s1.display(); // Name: Unknown, Age: 0
        s2.display(); // Name: Noman, Age: 0
        s3.display(); // Name: Tortilla, Age: 35
    }
}

/// Rule:
///    - `this()` must be the first statement inside the constructor.
