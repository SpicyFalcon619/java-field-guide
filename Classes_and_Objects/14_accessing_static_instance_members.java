/*
 * Classes and Objects: Accessing Static & Instance Members
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - Static variables and methods belong to the class, not objects.
///   - Instance variables and methods belong to objects.
///   - Example: Full Code with All Access Types
///   - Accessing Instance Variables From A Static Method
///   - Notes
/// ============================================================================


/// ### Topic 1: Static variables and methods belong to the class, not objects. ###
/// We can access them in two ways:
///    - Using the class name: `ClassName.staticVar`
///    - Using an object (allowed, but not recommended)


/// ### Topic 2: Instance variables and methods belong to objects. ###
/// We must access them using an object reference.


/// ### Topic 3: Example: Full Code with All Access Types ###
public class Main {
    static int staticVar = 100;

    static void staticMethod() {
        System.out.println("Inside static method. staticVar = " + staticVar);
    }

    int instanceVar = 200;

    void instanceMethod() {
        System.out.println("Inside instance method. instanceVar = " + instanceVar);
    }

    public static void main(String[] args) {

        // Accessing static members directly from main (which is also static)
        System.out.println("Accessing staticVar directly: " + staticVar);
        staticMethod();

        // Accessing static members using class name (recommended)
        System.out.println("Accessing staticVar using class name: " + Main.staticVar);
        Main.staticMethod();

        // Accessing static members using object (allowed but NOT recommended)
        Main obj = new Main();
        System.out.println("Accessing staticVar using object: " + obj.staticVar);
        obj.staticMethod();  // this works, but IDE might warn you

        // Accessing instance members using object (must use object)
        System.out.println("Accessing instanceVar using object: " + obj.instanceVar);
        obj.instanceMethod();
    }
}


/// ### Topic 4: Accessing Instance Variables From A Static Method ###
/// We can only do it indirectly, by creating an object inside the static method and using that
/// object:

public class Main {
    int instanceVar = 42;

    static void staticMethod() {
        Main obj = new Main();
        System.out.println("Accessing instanceVar from static method: " + obj.instanceVar);
    }

    public static void main(String[] args) {
        staticMethod();
    }
}


/// ### Topic 5: Notes ###
///    - `main()` is static → can access other static stuff directly
///    - We can’t use `this` in static methods, because `this` refers to an object
///    - Static methods cannot access instance variables directly
