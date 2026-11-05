/*
 * Encapsulation and Access: The final Keyword in Java
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What is `final` keyword?
///   - `final` with Variables
///   - `final` with Methods
///   - `final` with Class
///   - `final` with Parameters
///   - Why Use `final`?
/// ============================================================================


/// ### Topic 1: What is `final` keyword? ###
/// The `final` keyword in Java is used to make variables, methods, and classes immutable or
/// unmodifiable. Depending on where you apply it, it can have different effects:


/// ### Topic 2: `final` with Variables ###
/// When a variable is declared as `final`, its value cannot be changed after it’s initialized.
/// Essentially, it becomes a constant.
///
/// --- Syntax -----
///
///     final dataType variableName = value;
///
/// --- Example -----

public class Main {
    final int MAX_SPEED = 120; // Final variable

    public static void main(String[] args) {
        Main obj = new Main();
        // MAX_SPEED = 150; // This will cause a compile-time error
        System.out.println("Max speed: " + obj.MAX_SPEED);
    }
}

/// In this case, `MAX_SPEED` cannot be changed after it is initialized. If you try to assign a new
/// value to `MAX_SPEED`, it will result in a compilation error.


/// ### Topic 3: `final` with Methods ###
/// When a method is declared as `final`, it cannot be overridden by any subclass. This is
/// particularly useful when you want to ensure that the behavior of a method remains consistent and
/// cannot be altered.
///
/// --- Syntax -----
///
///     public final void methodName() {
///         // method body
///     }
///
/// --- Example -----


class Parent2 {
    public final void show() {
        System.out.println("This is the final method");
    }
}

class Child2 extends Parent2 {
    // public void show() {  // Error: Cannot override the final method
    //     System.out.println("Trying to override");
    // }
}

/// In this example, the `show()` method in the `Parent` class is `final`, so it cannot be
/// overridden in the `Child` class.


/// ### Topic 4: `final` with Class ###
/// When a class is declared as `final`, it cannot be subclassed. This is useful when you want to
/// prevent inheritance, ensuring that no one can extend the class and modify its behavior.
///
/// --- Syntax -----

public final class Main {
    // class body
}

/// --- Example -----


final class Vehicle2 {
    void display() {
        System.out.println("This is a vehicle");
    }
}

/// In this case, the `Vehicle` class is `final`, and no other class can extend it.


/// ### Topic 5: `final` with Parameters ###
/// You can also use `final` with method parameters. When a parameter is declared as `final`, its
/// value cannot be changed inside the method.
///
/// --- Syntax -----
///
///     public void method(final int x) {
///         // x = 10; // Error: Cannot assign a value to a final variable
///     }
///
/// --- Example -----

public class Main {
    public void display(final int number) {
        // number = 100; // This will cause a compile-time error
        System.out.println("The number is: " + number);
    }

    public static void main(String[] args) {
        Main obj = new Main();
        obj.display(50);
    }
}

/// In this case, the `Vehicle` class is `final`, and no other class can extend it.


/// ### Topic 6: Why Use `final`? ###
///    - For Constants: Declaring variables as `final` ensures that their values stay constant,
///    making your code more readable and less error-prone.
///    - Preventing Overriding: Marking methods as `final` ensures that subclasses cannot change the
///    method’s implementation, which can be useful when you need to preserve the original behavior.
///    - Preventing Inheritance: Marking a class as `final` ensures that no one can extend the
///    class, which might be helpful if the class contains critical functionality that should not be
///    changed.


/// ### Topic 7: Example with All Final Features ###

final class Vehicle3 {
    final int MAX_SPEED = 120;

    public final void showDetails(final int speed) {
        // speed = 150; // Error: Cannot change the final parameter
        System.out.println("Max speed: " + MAX_SPEED);
        System.out.println("Current speed: " + speed);
    }
}

public class Main {
    public static void main(String[] args) {
        Vehicle3 car = new Vehicle3();
        car.showDetails(100);
    }
}
