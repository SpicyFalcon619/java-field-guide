/*
 * Encapsulation and Access: The final keyword
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What `final` does, and the four places you can put it
///   - A final variable, a final method, a final class and a final parameter
///   - A subtle point: a final reference does not make the object itself unchangeable
///   - Why programmers use final
/// ============================================================================


/// ### Topic 1: What is the `final` keyword? ###
/// `final` means "this is the last word: no changing it". The keyword is used to make variables, methods and
/// classes unmodifiable. What it locks depends on where you put it:
///
///     final variable     its value cannot be changed after it is set
///     final method       a child class cannot override it
///     final class        no class can extend it
///     final parameter    the method cannot reassign it
///
/// ANALOGY: A name carved in stone versus one written on a whiteboard. You can still read a carved name. You just
/// cannot rub it out.

/// ### Topic 2: final with variables ###
/// A final variable can be given a value once and never changed. It becomes a CONSTANT. Constants are written in
/// UPPER_SNAKE_CASE by habit.
///
///     final dataType variableName = value;

public class Main {
    final int MAX_SPEED = 120; // Final variable

    public static void main(String[] args) {
        Main obj = new Main();
        System.out.println("Max speed: " + obj.MAX_SPEED);
    }
}

/// `MAX_SPEED` cannot be changed after it is initialised. Trying to do so is a compile error:

public class Main {
    final int MAX_SPEED = 120;

    public static void main(String[] args) {
        Main obj = new Main();
        obj.MAX_SPEED = 150;
        System.out.println(obj.MAX_SPEED);
    }
}
/// @error

/// "cannot assign a value to final variable MAX_SPEED". The compiler guards the constant for you.
///
/// A final variable does not have to be given its value on the very same line. You may leave it empty and assign it
/// ONCE later, for instance inside the constructor. That is how each object can have its own unchangeable value,
/// such as an ID number that is fixed at birth.

class Student {
    final int id;                 // blank final: must be set exactly once
    String name;

    Student(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

public class Main {
    public static void main(String[] args) {
        Student s = new Student(101, "Rafi");
        s.name = "Rafi Ahmed";            // fine: name is not final
        System.out.println(s.id + " " + s.name);
    }
}

/// ### Topic 3: A final reference is not a frozen object ###
/// This is a subtle point that trips up many people. When a variable holds an OBJECT, `final` locks the REFERENCE,
/// the address. It means the variable will always point to the same object. It does NOT stop the object's own
/// contents from changing.

public class Main {
    public static void main(String[] args) {
        final int[] numbers = {1, 2, 3};

        numbers[0] = 99;                   // allowed: changing the contents
        System.out.println(numbers[0]);

        numbers = new int[5];              // not allowed: pointing at a different array
    }
}
/// @error

/// Recall the house-and-address picture. `final` means the paper with the address may never be swapped for
/// another. It says nothing about repainting the house.

/// ### Topic 4: final with methods ###
/// A final method cannot be overridden by a child class. Use it when the behaviour is so important that it must
/// stay exactly as written.
///
///     public final void methodName() {
///         // method body
///     }

class Parent2 {
    public final void show() {
        System.out.println("This is the final method");
    }
}

class Child2 extends Parent2 {
    public void show() {          // Error: Cannot override the final method
        System.out.println("Trying to override");
    }
}

public class Main {
    public static void main(String[] args) {
        new Child2().show();
    }
}
/// @error

/// The compiler says "show() in Child2 cannot override show() in Parent2; overridden method is final". Here the
/// `show()` method in `Parent2` is final, so `Child2` is not allowed to replace it. Without the offending method,
/// `Child2` would simply inherit and use `show()` as it is:

class Parent2 {
    public final void show() {
        System.out.println("This is the final method");
    }
}

class Child2 extends Parent2 {
}

public class Main {
    public static void main(String[] args) {
        new Child2().show();
    }
}

/// ### Topic 5: final with classes ###
/// A final class cannot be extended at all. This is useful when you want to prevent inheritance, so no one can
/// subclass it and change its behaviour. Java's own `String` is final: nobody can create a trick subclass of
/// String, which keeps text handling trustworthy everywhere.
///
///     public final class Name { ... }

final class Vehicle2 {
    void display() {
        System.out.println("This is a vehicle");
    }
}

class Car extends Vehicle2 {
}

public class Main {
    public static void main(String[] args) {
        new Car().display();
    }
}
/// @error

/// "cannot inherit from final Vehicle2". The class itself can still be used and its objects created; only
/// inheriting from it is forbidden.

public class Main {
    public static void main(String[] args) {
        Vehicle2 v = new Vehicle2();
        v.display();
    }
}

final class Vehicle2 {
    void display() {
        System.out.println("This is a vehicle");
    }
}

/// ### Topic 6: final with parameters ###
/// A final parameter cannot be reassigned inside the method. It documents that the method only READS what it was
/// given.
///
///     public void method(final int x) {
///         // x = 10;   Error: Cannot assign a value to a final variable
///     }

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

/// ### Topic 7: Why use final? ###
///   - FOR CONSTANTS: a final variable's value stays fixed, which makes code more readable and less error-prone.
///   - PREVENTING OVERRIDING: a final method keeps its original behaviour in every child.
///   - PREVENTING INHERITANCE: a final class cannot be extended, useful when it holds critical functionality.
///   - SAFETY: a value that cannot change cannot be changed by mistake, and can be shared freely.
///
/// --- Example with all the final features -----

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

/// REMEMBER: `final` variable = set once. `final` method = no overriding. `final` class = no inheriting. A final
/// reference locks the address, not the contents. Compare it with `static final`, a shared constant for the whole
/// class, from the Static keyword lesson.

/// ============================================================================
/// YOUR TURN
///   1. Declare `final double GST = 0.15;` and use it in a price calculation. Then try to change it.
///   2. Give a `Book` class a `final String isbn` that is set in the constructor. Try to set it twice.
///   3. Make a method final and try to override it in a child. Read the error.
///   4. Declare a `final` ArrayList or array, add items to it, and then try to reassign it. Explain the difference.
///   5. Why do you think Java's `String` class is final?
/// ============================================================================
