/*
 * Classes and Objects: this Keyword
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What is `this` keyword?
///   - Basic Use Case of `this` keyword
///   - Using `this()` to Call Another Constructor
///   - Using `this` to Return Current Object (Method Chaining)
/// ============================================================================


/// ### Topic 1: What is `this` keyword? ###
/// `this` is a reference variable in Java that refers to the current object.
/// We use it when:
///    - Local variables have the same name as instance variables.
///    - You want to call another constructor from inside a constructor.
///    - You want to pass the current object as a parameter.


/// ### Topic 2: Basic Use Case of `this` keyword ###

class Student4 {
    String name;
    int age;

    public Student4(String name, int age) {
        this.name = name; // 'this.name' refers to instance variable
        this.age = age;   // 'age' on the right is the parameter
    }

    public void info() {
        System.out.println("Name: " + this.name);
        System.out.println("Age: " + this.age);
    }
}

public class Main {
    public static void main(String[] args) {
        Student4 s1 = new Student4("Noman", 35);
        s1.info();
    }
}


/// ### Topic 3: Using `this()` to Call Another Constructor ###

class Student5 {
    String name;
    int age;

    // Default constructor
    public Student5() {
        this("Nothing", 0);  // calls the parameterized constructor
    }

    // Parameterized constructor
    public Student5(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void info() {
        System.out.println("Name: " + name + ", Age: " + age);
    }
}

public class Main {
    public static void main(String[] args) {
        Student5 s1 = new Student5();             // uses default
        Student5 s2 = new Student5("Noman", 35);  // uses parameterized

        s1.info();
        s2.info();
    }
}


/// ### Topic 4: Using `this` to Return Current Object (Method Chaining) ###
///     TBW later
