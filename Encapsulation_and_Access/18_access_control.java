/*
 * Encapsulation and Access: Access Control
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What is Encapsulation?
///   - Access Modifiers
///   - `public` Access Modifier
///   - `private` Access Modifier
///   - `protected` Access Modifier
///   - Default (Package-Private) Access Modifier
/// ============================================================================


/// ### Topic 1: What is Encapsulation? ###
///    - Encapsulation is the process of wrapping data (variables) and methods (functions) into a
///    single unit, i.e., a class.
///    - It hides the internal state of an object and only allows access to it through public
///    methods (getters and setters).


/// ### Topic 2: Access Modifiers ###
/// Java provides four main access modifiers that you can use to control the accessibility of
/// classes, methods, and variables:
///    1. `public`
///    1. `private`
///    1. `protected`
///    1. Default


/// ### Topic 3: `public` Access Modifier ###
///    - Visibility: The member is accessible from any class in any package.
///    - Use case: We typically use `public` for methods and variables that are meant to be accessed
///    globally, outside the class.

public class Main {
    public String name; // Public variable

	  // Public method
    public void greet() {
        System.out.println("Hello, " + name);
    }
}


/// ### Topic 4: `private` Access Modifier ###
///    - Visibility: The member is only accessible within the class. It cannot be accessed from
///    outside the class.
///    - Use case: `private` is used to implement encapsulation, where you hide the details of the
///    implementation and provide controlled access through getters and setters.

public class Main {
    private String name; // Private variable

    // Getter method to access the private variable
    public String getName() {
        return name;
    }

    // Setter method to modify the private variable
    public void setName(String name) {
        this.name = name;
    }
}


/// ### Topic 5: `protected` Access Modifier ###
///    - Visibility: The member is accessible within the same package and also by subclasses
///    (including those in different packages).
///    - Use case: `protected` is typically used when you want to allow subclasses to access certain
///    members, but still want to hide them from other parts of the code.

public class Main {
    protected String name; // Protected variable

    // Protected method can be accessed by subclasses
    protected void greet() {
        System.out.println("Hello, " + name);
    }
}


/// ### Topic 6: Default (Package-Private) Access Modifier ###
///    - Visibility: If no access modifier is specified, the member is accessible only within the
///    same package
///    - Use case: This is used when you want to restrict access to the class, method, or variable
///    only to other classes within the same package, and not from outside.

public class Main {
    String name; // No access modifier, package-private

    void greet() { // Package-private method
        System.out.println("Hello, " + name);
    }
}


/// ### Topic 7: Example with Different Access Modifiers ###
public class Main {

    public String publicVariable = "I am public";
    private String privateVariable = "I am private";
    protected String protectedVariable = "I am protected";
    String defaultVariable = "I am package-private";

    public void publicMethod() {
        System.out.println("Public method called");
    }

    private void privateMethod() {
        System.out.println("Private method called");
    }

    protected void protectedMethod() {
        System.out.println("Protected method called");
    }

    void defaultMethod() {
        System.out.println("Package-private method called");
    }

    public static void main(String[] args) {
        Main example = new Main();

        // Accessing public variable and method
        System.out.println(example.publicVariable);
        example.publicMethod();

        // Accessing private variable and method (not allowed outside class)
        // System.out.println(example.privateVariable);  // Error
        // example.privateMethod();  // Error

        // Accessing protected variable and method
        System.out.println(example.protectedVariable);
        example.protectedMethod();

        // Accessing default variable and method
        System.out.println(example.defaultVariable);
        example.defaultMethod();
    }
}


/// ### Topic 8: When to Use Each Modifier? ###
///    - Use `public` when you need a class, method, or variable to be accessible anywhere.
///    - Use `private` when you want to hide implementation details (data encapsulation).
///    - Use `protected` when you want to allow access to subclasses but restrict it from other
///    code.
///    - Use default (package-private) when you want to restrict access to the same package, often
///    used for helper classes or methods.
