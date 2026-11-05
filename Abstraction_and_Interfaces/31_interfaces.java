/*
 * Abstraction and Interfaces: Interfaces
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What an interface is, and how a class implements it
///   - Why a class can implement many interfaces but extend only one class
///   - Default and static methods, and constants in interfaces
///   - Using an interface and an abstract class together
///   - Interface or abstract class: how to choose
/// ============================================================================


/// ### Topic 1: What is an interface? ###
/// An interface is a blueprint of a class. It lists what a class must be able to do, and says nothing about how.
///
/// ANALOGY: A plug socket. Anything with the right plug fits, whether it is a lamp, a phone charger or a kettle. The
/// socket does not care what the device is. It only requires the shape of the plug. An interface is that required
/// shape. A class that "implements" it promises to have the matching methods.
///
/// --- Key points -----
///    - Declared using the `interface` keyword.
///    - Until Java 7 it contained only abstract methods. From Java 8 it can also have default and static methods.
///    - It cannot have constructors, and you cannot create an object of an interface.
///    - A class implements an interface with `implements`.
///    - A class can implement multiple interfaces (this is how Java supports multiple inheritance).
///    - Methods in an interface are automatically `public abstract`, and variables are automatically `public static
///      final` (constants).

/// ### Topic 2: Example ###

interface Drawable {
    void draw();
}

class Circle implements Drawable {
    public void draw() {
        System.out.println("Drawing a circle");
    }
}

public class Main {
    public static void main(String[] args) {
        Drawable d = new Circle();
        d.draw();
    }
}

/// Two things to notice. First, `Circle` had to write `public void draw()`. The method must be public, because every
/// interface method is public and a class cannot make it less visible (the same rule you saw in Method Overriding).
/// Second, the variable has the interface as its type: `Drawable d = new Circle();`. That is polymorphism again. Any
/// class that implements Drawable can go there.
///
/// MISTAKE: Leaving off `public` when implementing the method is a very common error.

interface Drawable {
    void draw();
}

class Circle implements Drawable {
    void draw() {
        System.out.println("Drawing a circle");
    }
}

public class Main {
    public static void main(String[] args) {
        new Circle().draw();
    }
}
/// @error

/// The compiler says "attempting to assign weaker access privileges; was public".

/// ### Topic 3: One interface, many classes ###
/// The real value of an interface is that unrelated classes can share one capability, and the code that uses them does
/// not need to know which is which.

interface Playable {
    void play();
}

class Song implements Playable {
    public void play() {
        System.out.println("Playing a song");
    }
}

class Video implements Playable {
    public void play() {
        System.out.println("Playing a video");
    }
}

class Game implements Playable {
    public void play() {
        System.out.println("Starting the game");
    }
}

public class Main {
    static void pressPlay(Playable p) {
        p.play();
    }

    public static void main(String[] args) {
        Playable[] things = { new Song(), new Video(), new Game() };
        for (Playable p : things) {
            pressPlay(p);
        }
    }
}

/// A Song, a Video and a Game have nothing in common as a family, so inheritance would make no sense. But they can
/// all be played, and `pressPlay` works for each, and for any class added later.

/// ### Topic 4: Multiple inheritance (recap) ###
/// --- What is multiple inheritance? -----
///
/// NOTE: Multiple inheritance means one class inherits from more than one parent class.
///
/// --- Problem in Java -----
///    - Java classes do NOT support multiple inheritance directly (through classes).
///    - To solve this, Java uses interfaces.
///
/// --- Why not multiple inheritance with classes? -----
///
/// NOTE: To avoid confusion: if two parent classes have the same method name, which one will the child inherit? This
/// is called the DIAMOND PROBLEM.
///
/// --- Solution in Java: use interfaces -----
///
/// A class can implement multiple interfaces, thus achieving multiple inheritance of behaviour.
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

/// --- Key points -----
///    - A class can implement multiple interfaces.
///    - No ambiguity, because interfaces only declare what is needed (no code to clash).
///    - Java solves the multiple inheritance problem by using interfaces.
///
/// --- Diagram -----
///
///      Interface A     Interface B
///              \          /
///               \        /
///                \      /
///                Class C

/// ### Topic 5: Using both an abstract class and an interface ###
/// A class may extend ONE class and implement ANY NUMBER of interfaces at the same time. The `extends` part comes
/// first, then `implements`.

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

/// `Cat4` inherits the fields `name` and `age` from the abstract class, must implement `makeNoise()` from it, and
/// must also implement `eat()` from the interface.

/// ### Topic 6: Default and static methods, and constants ###
/// Since Java 8 an interface method may have a body, if you mark it `default`. This lets library authors add a method
/// to an interface without breaking every class that already implements it. A `static` method belongs to the interface
/// itself, and a variable is automatically a constant.

interface Greeter {
    String PREFIX = "Hello, ";                       // a constant: public static final

    String name();                                   // abstract: every class must give a name

    default void greet() {                           // has a body, inherited for free
        System.out.println(PREFIX + name() + "!");
    }

    static void about() {                            // called on the interface itself
        System.out.println("A Greeter says hello.");
    }
}

class Student implements Greeter {
    public String name() {
        return "Rafi";
    }
}

public class Main {
    public static void main(String[] args) {
        Student s = new Student();
        s.greet();
        Greeter.about();
        System.out.println(Greeter.PREFIX.trim());
    }
}

/// `Student` only wrote `name()`. It got `greet()` for free. It could also override `greet()` if it wanted its own
/// version.

/// ### Topic 7: A functional interface, and a look ahead ###
/// An interface with exactly ONE abstract method is called a FUNCTIONAL INTERFACE. Java lets you supply the one
/// method with a very short piece of code called a lambda, instead of writing a whole class. You will see this properly
/// in the lessons on lambdas. For now, notice how little is needed:

interface Operation {
    int apply(int a, int b);
}

public class Main {
    public static void main(String[] args) {
        Operation add = (a, b) -> a + b;
        Operation multiply = (a, b) -> a * b;

        System.out.println(add.apply(3, 4));
        System.out.println(multiply.apply(3, 4));
    }
}

/// ### Topic 8: Interface or abstract class? ###
///     Abstract class                                  Interface
///     a class can extend only ONE                     a class can implement MANY
///     can have fields, constructors, any methods      constants and (default/static/abstract) methods
///     describes what something IS (a Shape)           describes what something CAN DO (Drawable, Playable)
///     use for a family sharing code                   use for a shared capability across unrelated classes
///
/// TIP: A good rule: start with an interface for the capability. Use an abstract class when children also need to share
/// fields or real code.
///
/// REMEMBER: An interface is a contract. A class signs it with `implements` and must write every abstract method as
/// `public`. One class, many interfaces.

/// ============================================================================
/// YOUR TURN
///   1. Write an interface `Flyable` with `void fly()`. Implement it in `Bird` and `Plane`, and call `fly()` through a
///      `Flyable[]`.
///   2. Make `Duck` extend an `Animal` class and also implement both `Flyable` and `Swimmable`.
///   3. Add a `default` method to an interface and use it from a class that does not override it.
///   4. Implement an interface method without `public` and read the error.
///   5. Write a functional interface `Converter` with `double convert(double x)` and create two lambdas: km to miles and
///      Celsius to Fahrenheit.
///   6. For each, decide between interface and abstract class: "Printable", "Vehicle with a speed field", "Comparable".
/// ============================================================================
