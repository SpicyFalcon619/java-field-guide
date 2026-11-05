/*
 * Classes and Objects: Static keyword
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What `static` means: it belongs to the class, not to any one object
///   - Static variables (shared by everyone), static methods and static blocks
///   - How to reach static and instance members, and why `main` is static
///   - The most common static error, and how to fix it
/// ============================================================================


/// ### Topic 1: What is `static`? ###
/// So far, every field belonged to an object. Each Student had its own `name`. But sometimes a piece of data is
/// about the WHOLE CLASS, not about one object: the number of students created so far, the name of the school,
/// the value of pi. It would be wasteful, and wrong, to store a separate copy in every object.
///
/// The keyword `static` marks something as belonging to the CLASS itself. There is exactly ONE copy, shared by all
/// objects, and it exists even if no object has been created yet.
///
/// ANALOGY: In a classroom, every student has their own notebook (an instance variable). But there is only one
/// whiteboard on the wall (a static variable), and everyone sees the same thing on it. If somebody writes on the
/// whiteboard, all students see the change.
///
///     Type              Example
///     Static variable   shared among all objects
///     Static method     called using ClassName.method()
///     Static block      runs once when class is loaded

/// ### Topic 2: Static variable ###
/// Here a `Counter` class counts how many objects have been created. The count lives in one shared place, so
/// every constructor call adds to the same number.

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

/// If `count` had not been static, each Counter would have its own `count` starting at 0, and every object would
/// print 1. Because it is static, there is one `count`, and the three objects built it up together to 3.
///
/// TIP: A very common use is giving every object a unique number (an ID) from one shared counter.

/// ### Topic 3: Static method ###
/// A static method belongs to the class too, so you call it with the CLASS NAME, with no object needed. This is
/// perfect for helper methods that do not depend on any object's data. You have already used some: `Math.sqrt(9)`
/// and `Integer.parseInt("5")` are static methods, called on the class names `Math` and `Integer`.

class MathHelper {
    static int add(int a, int b) {
        return a + b;
    }
}

public class Main {
    public static void main(String[] args) {
        int sum = MathHelper.add(5, 3); // no need to create object
        System.out.println("Sum = " + sum);
    }
}

/// NOTE: The original example for this lesson named the class `Math`, but Java already has a class called
/// `Math`. When you declare your own with the same name, yours hides it, which causes confusing errors. Choose names
/// that do not clash with Java's own.
///
/// WHY: Compare the two styles. With a non-static method you would first write `new MathHelper()` just to add two
/// numbers, creating an object you never use. Static methods avoid that.

/// ### Topic 4: Reaching static and instance members ###
/// Here is one class with all the combinations, so you can see what is allowed.
///
///   - A STATIC member is reached through the class name: `Main.staticVar`. (Using an object works but is
///     discouraged, because it hides the fact that it is shared.)
///   - An INSTANCE member needs an object: `obj.instanceVar`.

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

/// ### Topic 5: Why `main` is static, and the classic error ###
/// Now the mystery from the Methods lesson is solved. Java must start your program BEFORE any object exists, so
/// `main` has to be something that can be called without one. That is exactly what `static` gives it: the JVM
/// calls `Main.main(...)` directly using the class name.
///
/// The price is a rule. A static method has no object behind it, so it cannot reach INSTANCE members directly,
/// because "whose instance variable?" would have no answer.

public class Main {
    int instanceVar = 42;

    public static void main(String[] args) {
        System.out.println(instanceVar);
    }
}
/// @error

/// The compiler says "non-static variable instanceVar cannot be referenced from a static context". There are two
/// fixes. Either create an object and go through it, as below, or make the variable static too if it really is
/// shared.

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

/// The reverse direction is always fine: an instance method can read static variables, because the class-wide data
/// exists for everyone.
///
/// REMEMBER, the summary of the rules:
///
///   - `main()` is static, so it can use other static members directly.
///   - Static code cannot use `this`, because `this` means an object, and there is none.
///   - Static methods cannot access instance variables or call instance methods directly.
///   - Instance methods CAN use static members freely.

/// ### Topic 6: Constants with static final ###
/// Remember `final` from the Variables lesson? A value that is shared by everyone and never changes is declared
/// `static final`, written in UPPER_SNAKE_CASE. It then behaves like a named constant of the class.

class Circle {
    static final double PI = 3.14159;
    double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    double area() {
        return PI * radius * radius;
    }
}

public class Main {
    public static void main(String[] args) {
        Circle c = new Circle(2);
        System.out.println(c.area());
        System.out.println(Circle.PI);
    }
}

/// ### Topic 7: The static block ###
/// A static block is a block of code in the class, marked with the word `static`, that runs ONCE, when the class
/// is first loaded into memory. That happens even before `main` begins and before any object exists. It is used
/// for one-time setup, such as filling a shared table or loading settings.

public class Main {
    static {
        // Static block
        System.out.println("Static block is running!");
    }

    public static void main(String[] args) {
        System.out.println("Main method is running!");
    }
}

/// Look at the order: the static block printed first, even though `main` is the entry point. A class may have more
/// than one, and they run in the order they are written.

public class Main {
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

/// Key points: a static block runs once when the class is loaded, and not when objects are created. The next
/// lesson shows how it fits with its object-level cousin, the instance initialization block.

/// ============================================================================
/// YOUR TURN
///   1. Add a static variable `totalStudents` to a Student class and increase it in the constructor. Print it
///      after creating four students.
///   2. Give every Student a unique `id` using a static counter, so the first is 1, the second 2, and so on.
///   3. Write a class `Converter` with static methods `kmToMiles` and `celsiusToFahrenheit` and call them
///      without creating any object.
///   4. Remove `static` from a method that `main` calls, and read the error message.
///   5. Explain, in one sentence each: why can `main` not use `this`, and why can an instance method use a static
///      variable?
/// ============================================================================
