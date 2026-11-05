/*
 * Classes and Objects: Constructors
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What is a Constructor?
///   - Example of a Constructor
///   - Types of Constructor
/// ============================================================================


/// ### Topic 1: What is a Constructor? ###
///    - A constructor is a special method used to create and initialize objects.
///    - It has the same name as the class and no return type (not even `void`)


/// ### Topic 2: Example of a Constructor ###

class Student2 {
    String name;
    int age;

    // Constructor
    public Student2(String n, int a) {
        name = n;
        age = a;
    }

    public void introduce() {
        System.out.println("Hi, I'm " + name + " and I'm " + age + " years old.");
    }
}

public class Main {
    public static void main(String[] args) {
        
        Student2 s1 = new Student2("Noman", 35);
        Student2 s2 = new Student2("Tortilla", 100);

        s1.introduce();
        s2.introduce();
    }
}


/// ### Topic 3: Types of Constructor ###
/// --- Default Constructor -----
///
///     public Student() {
///         name = "Noman";
///         age = 35;
///     }
///
/// --- Parameterized Constructor -----


class Student3 {
    String name;
    int age;

    public Student3() {
        name = "nothing";
        age = 0;
    }

    public Student3(String n, int a) {
        name = n;
        age = a;
    }

    public void introduce() {
        System.out.println("Hi, I'm " + name + " and I'm " + age + " years old.");
    }
}

public class Main {
    public static void main(String[] args) {
        Student3 s1 = new Student3();
        Student3 s2 = new Student3("Noman", 35);

        s1.introduce();
        s2.introduce();
    }
}
