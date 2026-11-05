/*
 * Abstraction and Interfaces: Abstraction & Interface
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What is Abstraction?
///   - How to Achieve Abstraction in Java
///   - Abstract Class
///   - Interface
///   - Multiple Inheritance (Recap)
///   - Using both Abstract Class & Interface in a Child Class
/// ============================================================================


/// ### Topic 1: What is Abstraction? ###
/// Abstraction in Java is a process of hiding the implementation details and showing only the
/// functionality to the user.
/// Think of driving a car — we use the steering, accelerator, and brakes without knowing how the
/// engine works inside.


/// ### Topic 2: How to Achieve Abstraction in Java ###
///    1. Abstract Classes
///    1. Interfaces


/// ### Topic 3: Abstract Class ###
///    - Declared using the `abstract` keyword.
///    - Can have both abstract (no body) and concrete (with body) methods.
///    - Cannot be instantiated(you can’t create an object of it).
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

///    - `Animal` defines a template.
///    - `Dog` must implement `makeSound()`.
///    - `sleep()` is a shared behavior.
///
/// --- When to Use Abstract Class: -----
///    - When several classes share common code.
///    - When you want to force subclasses to implement certain methods.


/// ### Topic 4: Interface ###
/// --- What is an Interface? -----
///
/// An interface is a blueprint of a class. It only contains abstract methods (until Java 7), and
/// from Java 8+, it can also have default and static methods.
///
/// --- Key Points -----
///    - Declared using the `interface` keyword.
///    - Can’t have constructors.
///    - A class implements an interface.
///    - A class can implement multiple interfaces (supports multiple inheritance).
///    - Any variable declared in an Interface is both `static` & `final` .
///
/// --- Example -----

interface Drawable {
    void draw();
}

class Circle implements Drawable {
    public void draw() {
        System.out.println("Drawing a circle");
    }
}


/// ### Topic 5: Multiple Inheritance (Recap) ###
/// --- What is Multiple Inheritance? -----
///
/// NOTE: Multiple Inheritance means one class inherits from more than one parent class.
///
/// --- Problem in Java -----
///    - Java classes DO NOT support multiple inheritance directly (through classes).
///    - To solve this, Java uses Interfaces!
///
/// --- Why not multiple inheritance with classes? -----
///
/// NOTE: To avoid confusion — like if two parent classes have the same method name, which one will
/// the child inherit?
///
/// This is called the Diamond Problem.
///
/// --- Solution in Java: Use Interfaces -----
///
/// A class can implement multiple interfaces, thus achieving multiple inheritance.
///
/// --- Example -----


interface A2 {
    void methodA();
}

interface B2 {
    void methodB();
}

class C2 implements A2, B2 {
    public void methodA() {
        System.out.println("Method A from Interface A");
    }

    public void methodB() {
        System.out.println("Method B from Interface B");
    }
}

public class Main {
    public static void main(String[] args) {
        C2 obj = new C2();
        obj.methodA();
        obj.methodB();
    }
}

/// --- Key Points -----
///    - A class can implement multiple interfaces.
///    - No ambiguity because interfaces only declare methods (no body).
///    - Java solves the multiple inheritance problem smartly using interfaces.
///
/// --- Diagram -----
///
///      Interface A     Interface B
///              \          /
///               \        /
///                \      /
///                Class C


/// ### Topic 6: Using both Abstract Class & Interface in a Child Class ###

abstract class Animal12 {

    int age;
    String name;

    public abstract void makeNoise();
}

interface AnimalStuff {

    public void eat();
}

class Cat4 extends Animal12 implements AnimalStuff {

    public Cat4(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public void makeNoise() {
        System.out.println("Meow");
    }

    @Override
    public void eat() {
        System.out.println("Wiwiwiwi..... wiwiwi");
    }

    public void printDetails() {
        System.out.println("Name: " + name + "\nAge: " + age);
    }
}

public class Main {

    public static void main(String[] args) {
        Cat4 c1 = new Cat4("Tom", 1);

        c1.printDetails();

        c1.makeNoise();
        c1.eat();
    }
}
