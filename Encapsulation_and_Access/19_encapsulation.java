/*
 * Encapsulation and Access: Encapsulation
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What is Encapsulation?
///   - Basic Example of Encapsulation
///   - Benefits of Encapsulation:
/// ============================================================================


/// ### Topic 1: What is Encapsulation? ###
///    - Encapsulation is the process of wrapping data (variables) and methods (functions) into a
///    single unit, i.e., a class.
///    - It hides the internal state of an object and only allows access to it through public
///    methods (getters and setters).


/// ### Topic 2: Basic Example of Encapsulation ###

class Person2 {
    // Private attributes
    private String name;
    private int age;

    // Constructor to initialize the object
    public Person2(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getter method for 'name'
    public String getName() {
        return name;
    }

    // Setter method for 'name'
    public void setName(String name) {
        this.name = name;
    }

    // Getter method for 'age'
    public int getAge() {
        return age;
    }

    // Setter method for 'age' with validation
    public void setAge(int age) {
        if (age >= 0 && age <= 150) {
            this.age = age;
        } else {
            System.out.println("Invalid age!");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Person2 p1 = new Person2("Noman", 35);

        System.out.println(p1.getName());
        System.out.println(p1.getAge());

        p1.setName("Tortilla");
        p1.setAge(100);

        System.out.println(p1.getName());
        System.out.println(p1.getAge());

        // Attempting to set an invalid age
        p1.setAge(200);
    }
}

/// --- Notes: -----
///    - The Person class has private attributes (`name` and `age`), which cannot be accessed
///    directly from outside the class.
///    - Public methods (`getName()`, `getAge()`, `setName()`, `setAge()`) are used to access and
///    modify the private data.
///    - The setter method for `age` includes validation to ensure that only valid age values are
///    accepted.


/// ### Topic 3: Benefits of Encapsulation: ###
///    - Data Protection: By making variables private, we protect the data from direct modification,
///    ensuring that only valid values are set.
///    - Control: We can control how the data is accessed and modified using the getter and setter
///    methods. You can add logic in setters for validation or transformation.
///    - Flexibility: The internal implementation of the class can be changed without affecting the
///    code that uses the class, as long as the public methods (the interface) remain the same.
///    - Maintainability: With encapsulation, the code is easier to maintain and debug, as the
///    internal state is hidden, and changes to it are controlled.
