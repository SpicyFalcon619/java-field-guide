/*
 * Abstraction and Interfaces: Abstraction and abstract classes
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What abstraction means, and why hiding details makes code easier to use
///   - What an abstract class and an abstract method are
///   - Why you cannot create an object of an abstract class
///   - When to choose an abstract class
/// ============================================================================


/// ### Topic 1: What is abstraction? ###
/// ABSTRACTION is the idea of hiding the implementation details and showing only the functionality to the user.
///
/// ANALOGY: Think of driving a car. You use the steering wheel, the accelerator and the brakes without knowing how the
/// engine works inside. The controls are the simple surface; the machinery stays hidden. The next driver can use a
/// different car with a different engine and still drive it, because the controls are the same.
///
/// In code, abstraction means: say WHAT something can do, and leave HOW to the classes that do it.
///
/// Abstraction is close to encapsulation, but they answer different questions:
///    - ENCAPSULATION hides the DATA inside a class (private fields, controlled access).
///    - ABSTRACTION hides the DETAILS of what an operation does, and exposes only the idea of it.

/// ### Topic 2: How do we get abstraction in Java? ###
/// Java has two tools for it:
///    1. Abstract classes
///    2. Interfaces (the next lesson)

/// ### Topic 3: Abstract class ###
/// An abstract class is a class that is deliberately left unfinished. It is a template that describes what its
/// children must be able to do, and provides whatever they can share.
///    - Declared using the `abstract` keyword.
///    - Can have both abstract methods (no body) and concrete methods (with a body).
///    - Cannot be instantiated: you cannot create an object of it.
///
/// An ABSTRACT METHOD is a method with no body, only a signature and a semicolon. It is a promise: "every concrete
/// child of this class WILL provide this."
///
/// --- Example -----

abstract class Animal11 {
    abstract void makeSound();  // abstract method (no body)

    void sleep() {
        System.out.println("Sleeping...");
    }
}

class Dog10 extends Animal11 {
    void makeSound() {
        System.out.println("Woof!");
    }
}

public class Main {
    public static void main(String[] args) {
        Dog10 d = new Dog10();
        d.makeSound();
        d.sleep();
    }
}

/// Read what each part does:
///    - `Animal` defines a template.
///    - `Dog` must implement `makeSound()`, since the parent left it open. Every animal makes a sound, but each makes
///      its own, so the parent cannot say what it is.
///    - `sleep()` is a shared behaviour: every animal sleeps the same way, so it is written once, in the parent.
///
/// WHY: This gives you the best of two things. You are FORCED to implement what differs, and you REUSE what is the
/// same. A plain parent class cannot force anything. If `makeSound` were an ordinary method, a child could forget
/// to override it and silently inherit a meaningless "generic sound".

/// ### Topic 4: What you cannot do ###
/// An abstract class is incomplete, so Java will not let you build one. Imagine asking for "an animal" with no kind.
/// What sound would it make?

abstract class Animal {
    abstract void makeSound();
}

public class Main {
    public static void main(String[] args) {
        Animal a = new Animal();
        a.makeSound();
    }
}
/// @error

/// The compiler says "Animal is abstract; cannot be instantiated". You can, however, USE the abstract type as the
/// type of a variable, and put a real child object in it. That is polymorphism, from the earlier lesson:

abstract class Shape {
    abstract double area();

    void describe() {
        System.out.println("A shape with area " + area());
    }
}

class Circle extends Shape {
    double r;

    Circle(double r) {
        this.r = r;
    }

    double area() {
        return 3.14 * r * r;
    }
}

class Rectangle extends Shape {
    double w, h;

    Rectangle(double w, double h) {
        this.w = w;
        this.h = h;
    }

    double area() {
        return w * h;
    }
}

public class Main {
    public static void main(String[] args) {
        Shape[] shapes = { new Circle(2), new Rectangle(3, 4) };

        for (Shape s : shapes) {
            s.describe();        // describe() is shared; area() is each child's own
        }
    }
}

/// The shared `describe()` method calls `area()`, which the parent does not define. It works because at run time every
/// Shape is a real Circle or Rectangle that does. This pattern, a shared method built on an abstract one, is called the
/// TEMPLATE METHOD pattern, and you will meet it in real frameworks.

/// ### Topic 5: The child must finish the job ###
/// A child of an abstract class has two choices. It must implement every abstract method, or else it must be
/// declared `abstract` as well.

abstract class Vehicle {
    abstract void start();
    abstract void stop();
}

class Car extends Vehicle {
    void start() {
        System.out.println("Car starts");
    }
}

public class Main {
    public static void main(String[] args) {
        new Car().start();
    }
}
/// @error

/// "Car is not abstract and does not override abstract method stop() in Vehicle". Implement `stop()` and it compiles.
///
/// --- More facts about abstract classes -----
///    - It may have fields, constructors (called through `super(...)` by its children), and ordinary methods.
///    - It may have no abstract methods at all, though that is unusual.
///    - A method cannot be both `abstract` and `final`, or `abstract` and `private`, because abstract methods exist to
///      be overridden.

/// ### Topic 6: When to use an abstract class ###
///    - When several classes share common code.
///    - When you want to force subclasses to implement certain methods.
///    - When the classes really are one family, with an "is-a" relationship (a Circle IS a Shape).
///
/// TIP: If you only need a promise of behaviour and no shared code or fields, an interface (next lesson) is the
/// lighter tool. A class can extend only ONE class, but implement many interfaces.
///
/// REMEMBER: An abstract class is a template with gaps. It cannot be instantiated, abstract methods have no body, and a
/// concrete child must fill every gap.

/// ============================================================================
/// YOUR TURN
///   1. Write an abstract class `Employee` with an abstract `double salary()` and a concrete `void show()` that
///      prints the salary. Create `Manager` and `Intern` children with different salaries, and call `show()` on
///      both through an `Employee[]`.
///   2. Try to write `new Employee()` and read the error.
///   3. Remove one abstract method's implementation from a child and read the error.
///   4. Add a constructor with a `name` parameter to the abstract class, and call it from the children with
///      `super(name)`.
///   5. In your own words: what does an abstract class give you that a normal parent class does not?
/// ============================================================================
