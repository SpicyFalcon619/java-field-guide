/*
 * Classes and Objects: Classes & Objects
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What is a Class?
///   - Class & Object Example
///   - Notes
/// ============================================================================


/// ### Topic 1: What is a Class? ###
///    - A class is like a blueprint — it defines attributes (variables) and behaviors (methods).
///    - An object is a real instance of the class.


/// ### Topic 2: Class & Object Example ###
class Student {
    String name;
    int age;

    public void info() {
        System.out.println("Hi, I'm " + name + " and I'm " + age + " years old.");
    }
}

public class Main {
    public static void main(String[] args) {
        Student s1 = new Student();

        s1.name = "Noman";
        s1.age = 35;

        s1.info();
    }
}


/// ### Topic 3: Notes ###
///    - `Student` is the class.
///    - `s1` is the object.
///    - We use `.` to access the attributes and methods of an object.
