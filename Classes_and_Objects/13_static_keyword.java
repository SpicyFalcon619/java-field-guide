/*
 * Classes and Objects: Static Keyword
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What is `static`?
///   - Types of static:
///   - Static Variable
///   - Static Method
///   - Static Block
///   - Multiple Static Block
/// ============================================================================


/// ### Topic 1: What is `static`? ###
///    - Belongs to the class, not to any object.
///    - Useful for shared data or utility methods


/// ### Topic 2: Types of static: ###
///     Type              Example
///     Static variable   shared among all objects
///     Static method     called using ClassName.method()
///     Static block      runs once when class is loaded


/// ### Topic 3: Static Variable ###
class Counter {
    static int count = 0;

    Counter() {
        count++;
        System.out.println("Count is: " + count);
    }
}

public class Main {
    public static void main(String[] args) {
        Counter c1 = new Counter();
        Counter c2 = new Counter();
        Counter c3 = new Counter();
    }
}


/// ### Topic 4: Static Method ###
class Math {
    static int add(int a, int b) {
        return a + b;
    }
}

public class Main {
    public static void main(String[] args) {
        int sum = Math.add(5, 3); // no need to create object
        System.out.println("Sum = " + sum);
    }
}


/// ### Topic 5: Static Block ###
/// A `static block` is used to run code once when the class is loaded into memory, even before the
/// `main()` method runs or any object is created.


class MyClass2 {
    static {
        // Static block
        System.out.println("Static block is running!");
    }

    public static void main(String[] args) {
        System.out.println("Main method is running!");
    }
}

/// Key Points:
///    - Static block runs once when the class is loaded, not when objects are created.
///    - It's good for initial setup like loading configuration, initializing static variables, etc.


/// ### Topic 6: Multiple Static Block ###
class Demo {
    static {
        System.out.println("First static block");
    }

    static {
        System.out.println("Second static block");
    }

    public static void main(String[] args) {
        System.out.println("Inside main method");
    }
}
