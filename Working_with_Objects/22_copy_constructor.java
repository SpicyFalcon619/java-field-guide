/*
 * Working with Objects: Copy Constructor
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What is Copy Constructor?
///   - Example
/// ============================================================================


/// ### Topic 1: What is Copy Constructor? ###
/// A copy constructor is a constructor that creates a new object by copying the fields from another
/// object of the same class.
/// Java doesn't provide it by default — we define it ourself.


/// ### Topic 2: Example ###

class Student9 {
    String name;
    int age;

    // Normal constructor
    Student9(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Copy constructor
    Student9(Student9 s) {
        this.name = s.name;
        this.age = s.age;
    }

    void display() {
        System.out.println("Name: " + name + ", Age: " + age);
    }
}

public class Main {
    public static void main(String[] args) {
        Student9 s1 = new Student9("Noman", 35);
        Student9 s2 = new Student9(s1); // Copy constructor called

        s1.display();
        s2.display();
    }
}

/// Use Case:
///    - Copy constructors are useful when you want to duplicate an object with the same values.
