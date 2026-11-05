/*
 * Inheritance and Polymorphism: Inheritance
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What is Inheritance?
///   - `extends` Keyword
///   - Basic Syntax
///   - `super` Keyword
///   - Basic Example
///   - Inheritance Levels:
/// ============================================================================


/// ### Topic 1: What is Inheritance? ###
/// Inheritance = when one class inherits the properties and behavior of another.
///
/// NOTE: It helps us reuse code, make things more organized, and support polymorphism.


/// ### Topic 2: `extends` Keyword ###
/// The `extends` keyword is used to create a child class (also called a subclass) from a parent
/// class (superclass).
/// It tells Java:
///
/// NOTE: This new class will inherit all the accessible stuff from the parent class.


/// ### Topic 3: Basic Syntax ###
class Parent {
		
}

class Child extends Parent {
		
}


/// ### Topic 4: `super` Keyword ###
/// --- What is `super` keyword? -----
///
/// The `super` keyword is used to refer to the parent class (superclass) from the child class.
/// We use `super` for:
///    - Accessing parent class methods
///    - Accessing parent class variables
///    - Calling the parent class constructor
///
/// --- Using `super` to Call Parent Method -----


class Animal2 {
    void sound() {
        System.out.println("Animal makes a sound");
    }
}

class Dog2 extends Animal2 {
    void sound() {
        super.sound(); // Call parent method
        System.out.println("Dog barks");
    }
}

public class Main {
    public static void main(String[] args) {
        Dog2 d = new Dog2();
        d.sound();
    }
}

/// --- Using super to Access Parent Variable -----


class Animal3 {
    String type = "Animal";
}

class Cat extends Animal3 {
    String type = "Cat";

    void printType() {
        System.out.println("Child type: " + type);
        System.out.println("Parent type: " + super.type);
    }
}

public class Main {
    public static void main(String[] args) {
        Cat c = new Cat();
        c.printType();
    }
}

/// --- Using `super()` to call parent constructor -----


class Person {
    Person() {
        System.out.println("Person constructor called");
    }
}

class Student6 extends Person {
    Student6() {
        super();
        System.out.println("Student constructor called");
    }
}

public class Main {
    public static void main(String[] args) {
        Student6 s = new Student6();
    }
}


/// ### Topic 5: Basic Example ###

// Parent class
class Animal4 {
    public void eat() {
        System.out.println("This animal eats food");
    }
}

// Child class
class Dog3 extends Animal4 {
    public void bark() {
        System.out.println("The dog barks");
    }
}

public class Main {
    public static void main(String[] args) {
        Dog3 d = new Dog3();

        d.eat();
        d.bark();
    }
}


/// ### Topic 6: Inheritance Levels: ###
///     Type           Example
///     Single         Dog extends Animal
///     Multilevel     Puppy extends Dog extends Animal
///     Hierarchical   Cat, Dog, Cow all extend Animal


/// ### Topic 7: Hierarchical Inheritance ###
/// --- What is Hierarchical Inheritance? -----
///
/// NOTE: Hierarchical Inheritance occurs when multiple child classes inherit from the same parent
/// class.
///
/// It looks like a tree structure:
/// One Parent → Multiple Children.
///
/// --- Real-life Example -----
///
/// Think of Animal as the parent:
///    - Dog inherits from Animal
///    - Cat inherits from Animal
///    - Cow inherits from Animal
///
/// All different animals share some common features (like `eat()`, `sleep()`) but behave
/// differently too.
///
/// --- Example -----


class Animal5 {
    void eat() {
        System.out.println("This animal eats food.");
    }
}

class Dog4 extends Animal5 {
    void bark() {
        System.out.println("The dog barks.");
    }
}

class Cat2 extends Animal5 {
    void meow() {
        System.out.println("The cat meows.");
    }
}

public class Main {
    public static void main(String[] args) {
        Dog4 d = new Dog4();
        d.eat();
        d.bark();

        Cat2 c = new Cat2();
        c.eat();
        c.meow();
    }
}

/// --- Key Points -----
///    - One parent class → Multiple child classes.
///    - Child classes inherit common methods/fields from parent.
///    - Each child can also have its own extra features.
///
/// --- Diagram -----
///
///            Animal
///           /   |    \
///        Dog   Cat   Cow


/// ### Topic 8: Multilevel Inheritance ###
/// --- What is Multilevel Inheritance? -----
///
/// NOTE: Multilevel Inheritance means a class is derived from a class, which is also derived from
/// another class.
///
/// It’s like grandparent → parent → child.
///
/// --- Real-life Example -----
///
/// Imagine:
///    - Grandfather → Father → Son
///
/// The son inherits properties from the Father and Grandfather.
///
/// --- Example -----


class Animal6 {
    void eat() {
        System.out.println("This animal eats food.");
    }
}

class Dog5 extends Animal6 {
    void bark() {
        System.out.println("The dog barks.");
    }
}

class Puppy extends Dog5 {
    void weep() {
        System.out.println("The puppy weeps.");
    }
}

public class Main {
    public static void main(String[] args) {
        Puppy p = new Puppy();
        p.eat();   // Inherited from Animal
        p.bark();  // Inherited from Dog
        p.weep();  // Defined in Puppy
    }
}

/// --- Key Points -----
///    - Class A → Class B → Class C.
///    - Child class gets properties from parent and grandparent.
///    - Increases reusability and organized structure.
///    - Supports deep hierarchy.
///
/// --- Diagram -----
///
///     Animal
///        ↓
///     Dog
///        ↓
///     Puppy


/// ### Topic 9: Multiple Inheritance (Using Interface) ###
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

interface A {
    void methodA();
}

interface B {
    void methodB();
}

class C implements A, B {
    public void methodA() {
        System.out.println("Method A from Interface A");
    }

    public void methodB() {
        System.out.println("Method B from Interface B");
    }
}

public class Main {
    public static void main(String[] args) {
        C obj = new C();
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
