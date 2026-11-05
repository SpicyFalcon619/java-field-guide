/*
 * Classes and Objects: Example of Static, IIB and Object Creation
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - Code
///   - Output Breakdown
///   - Method Overriding
/// ============================================================================


/// ### Topic 1: Code ###

class Demo2 {

    // Static variable
    static int staticCount = 0;

    // Instance variable
    int instanceCount = 0;

    // Static block
    static {
        System.out.println("Static block executed");
        staticCount = 10;
    }

    // IIB (Instance Initialization Block)
    {
        System.out.println("IIB executed");
        instanceCount = 5;
        staticCount += 1; // can access static members
    }

    // Constructor
    Demo2() {
        System.out.println("Constructor executed");
        instanceCount += 2;
    }

    // Static method
    static void staticMethod() {
        System.out.println("Static method called");
        System.out.println("Static Count = " + staticCount);
        // Can't use instanceCount here (no object)
    }

    // Non-static method
    void instanceMethod() {
        System.out.println("Instance method called");
        System.out.println("Instance Count = " + instanceCount);
        System.out.println("Static Count = " + staticCount);
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("ExampleStaticIibObjectCreationCode method starts");

        // Call static method (no object needed)
        Demo2.staticMethod();

        System.out.println("Creating first object:");
        Demo2 d1 = new Demo2();
        d1.instanceMethod();

        System.out.println("Creating second object:");
        Demo2 d2 = new Demo2();
        d2.instanceMethod();
    }
}


/// ### Topic 2: Output Breakdown ###
///     Static block executed           ← runs once when class is loaded
///     
///     Main method starts
///     
///     Static method called            ← direct call without object
///     Static Count = 10
///     
///     Creating first object:
///     IIB executed                    ← runs before constructor
///     Constructor executed
///     Instance method called
///     Instance Count = 7             ← 5 (IIB) + 2 (constructor)
///     Static Count = 11              ← increased in IIB
///     
///     Creating second object:
///     IIB executed                    ← again before constructor
///     Constructor executed
///     Instance method called
///     Instance Count = 7             ← again 5 + 2
///     Static Count = 12              ← increased again


/// ### Topic 3: Method Overriding ###
/// --- What is Method Overriding? -----
///
/// Overriding means:
///
/// NOTE: A subclass (child) gives its own version of a method that it inherits from the superclass
/// (parent).
///
/// We use it to change or customize behavior of a method that comes from the parent class.
///
/// --- Basic Method Overriding -----

class Animal {
    public void makeSound() {
        System.out.println("Some animal sound");
    }
}

class Dog extends Animal {
    // Overriding makeSound method
    @Override
    public void makeSound() {
        System.out.println("Woof woof");
    }
}

public class Main {
    public static void main(String[] args) {
        Animal a = new Animal();
        Dog d = new Dog();

        a.makeSound();
        d.makeSound();
    }
}

/// --- Key Rules of Overriding -----
///    1. Method must have the same name, return type, and parameters.
///    1. Only works with inheritance (must be subclass).
///    1. Use `@Override` to help the compiler catch mistakes.
///    1. We cannot override:
///    - Static methods
///    - Constructors
///    - Final methods
///    - Private methods
///
/// --- Yet Another Example (w/Runtime Polymorphism) -----

class Vehicle {
    public void drive() {
        System.out.println("The vehicle is moving...");
    }
}

class Car extends Vehicle {
    @Override
    public void drive() {
        System.out.println("The car is moving!");
    }
}

public class Main {
    public static void main(String[] args) {
        Vehicle v1 = new Vehicle();
        Vehicle v2 = new Car(); // Parent reference, child object

        v1.drive();
        v2.drive();
    }
}
