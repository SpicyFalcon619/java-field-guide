/*
 * Practice: Interfaces problem set
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - Declaring an `interface` and writing classes that `implements` it
///   - Constants, `default` methods and `static` methods inside an interface
///   - Implementing several interfaces, and one interface extending another
///   - Settling a name clash between two interfaces
///   - Writing a method that accepts an interface type, so any implementing object works
/// ============================================================================

/// Twelve problems on interfaces. An interface says WHAT a class can do; the class decides HOW.

/// ### Problem 1: Animal sounds ###
/// Create an interface `Animal` with a method `sound()`. Create two classes `Dog` and `Cat` that implement it. Create objects
/// of both in `main` and call `sound()`.

interface Animal {
    void sound();
}

class Dog implements Animal {
    @Override
    public void sound() {
        System.out.println("Woof");
    }
}

class Cat implements Animal {
    @Override
    public void sound() {
        System.out.println("Meow");
    }
}

public class Main {
    public static void main(String[] args) {
        Dog dog = new Dog();
        Cat cat = new Cat();

        dog.sound();
        cat.sound();
    }
}

/// NOTE: Methods in an interface are `public` automatically, so the implementing method must be written `public` too. Leaving
/// it out is the most common error here ("attempting to assign weaker access privileges").

/// ### Problem 2: Drawing shapes ###
/// Define an interface `Shape` with a method `draw()`. Create classes `Circle` and `Rectangle` that implement it with their
/// own versions of `draw()`. Create objects of both and call `draw()`.

interface Shape {
    void draw();
}

class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing a circle");
    }
}

class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing a rectangle");
    }
}

public class Main {
    public static void main(String[] args) {
        Shape[] shapes = { new Circle(), new Rectangle() };

        for (Shape s : shapes) {
            s.draw();
        }
    }
}

/// ### Problem 3: A constant in an interface ###
/// Create an interface `MathConstants` with a constant `PI` (3.14). Create a class `Circle` that implements it and calculates
/// the area of a circle using `PI`. Show its use in `main`.

interface MathConstants {
    double PI = 3.14;               // automatically public static final
}

class Circle implements MathConstants {
    double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    double area() {
        return PI * radius * radius;     // PI is inherited from the interface
    }
}

public class Main {
    public static void main(String[] args) {
        Circle circle = new Circle(10);
        System.out.println("Area of the circle: " + circle.area());
        System.out.println("PI from the interface: " + MathConstants.PI);

        // MathConstants.PI = 3.0;       // error: a constant is final, it cannot be changed
    }
}

/// Every field in an interface is silently `public static final`: it belongs to the interface itself, so it can be reached as
/// `MathConstants.PI`, and it can never change.

/// ### Problem 4: A default method ###
/// Define an interface `Printer` with a default method `connect()` that prints "Printer connected", and an abstract method
/// `print()`. Create a class `LaserPrinter` that implements `print()`. Show the default method in `main`.

interface Printer {
    default void connect() {
        System.out.println("Printer connected");
    }

    void print();
}

class LaserPrinter implements Printer {
    @Override
    public void print() {
        System.out.println("The laser printer prints a page");
    }
}

public class Main {
    public static void main(String[] args) {
        LaserPrinter printer = new LaserPrinter();

        printer.connect();         // inherited default method: LaserPrinter did not write it
        printer.print();
    }
}

/// A `default` method has a body, so classes that implement the interface get it for free, and old classes keep working when a
/// new method is added to the interface later. That is why Java added default methods.

/// ### Problem 5: Start and service ###
/// Create an interface `Vehicle` with an abstract method `start()` and a default method `service()` that prints "Service
/// required". Implement it in a `Car` class, where `start()` is implemented. In `main`, call both `start()` and `service()`.

interface Vehicle {
    void start();

    default void service() {
        System.out.println("Service required");
    }
}

class Car implements Vehicle {
    @Override
    public void start() {
        System.out.println("The car starts");
    }
}

public class Main {
    public static void main(String[] args) {
        Car car = new Car();
        car.start();
        car.service();
    }
}

/// ### Problem 6: Two interfaces in one class ###
/// Create two interfaces `Playable` and `Recordable`, with the methods `play()` and `record()`. Create a class `MediaPlayer`
/// that implements both. Show how one class can implement several interfaces.

interface Playable {
    void play();
}

interface Recordable {
    void record();
}

class MediaPlayer implements Playable, Recordable {
    @Override
    public void play() {
        System.out.println("Playing the recording");
    }

    @Override
    public void record() {
        System.out.println("Recording started");
    }
}

public class Main {
    public static void main(String[] args) {
        MediaPlayer player = new MediaPlayer();
        player.record();
        player.play();

        Playable justPlayer = player;          // seen as only a Playable, record() is hidden
        justPlayer.play();
    }
}

/// ### Problem 7: An interface extending an interface ###
/// Create an interface `Animal` with a method `eat()`. Create another interface `Pet` that extends `Animal` and adds a method
/// `play()`. Create a class `Dog` that implements `Pet`, giving both `eat()` and `play()`. Show interface inheritance.

interface Animal {
    void eat();
}

interface Pet extends Animal {          // Pet has eat() from Animal, plus play()
    void play();
}

class Dog implements Pet {
    @Override
    public void eat() {
        System.out.println("The dog eats its food");
    }

    @Override
    public void play() {
        System.out.println("The dog plays fetch");
    }
}

public class Main {
    public static void main(String[] args) {
        Dog dog = new Dog();
        dog.eat();
        dog.play();

        Animal animal = dog;                // a Dog is a Pet, and a Pet is an Animal
        animal.eat();
    }
}

/// An interface uses `extends` for another interface (and can extend several, separated by commas). A class uses `implements`.

/// ### Problem 8: A static method in an interface ###
/// Create an interface `Calculator` with a static method `add(int a, int b)` that returns the sum. In `main`, call the static
/// method directly from the interface, without creating an object.

interface Calculator {
    static int add(int a, int b) {
        return a + b;
    }
}

public class Main {
    public static void main(String[] args) {
        int sum = Calculator.add(12, 30);       // called on the interface name, no object needed
        System.out.println("12 + 30 = " + sum);
    }
}

/// A static interface method belongs to the interface itself. Unlike a default method, implementing classes do not inherit
/// it, so it must always be called as `Calculator.add(...)`.

/// ### Problem 9: Payments ###
/// Define an interface `Payment` with a method `processPayment()`. Create `CreditCardPayment` and `PayPalPayment` that
/// implement it. Show polymorphism with a `Payment` reference that can point to both kinds of object.

interface Payment {
    void processPayment();
}

class CreditCardPayment implements Payment {
    @Override
    public void processPayment() {
        System.out.println("Paid by credit card");
    }
}

class PayPalPayment implements Payment {
    @Override
    public void processPayment() {
        System.out.println("Paid through PayPal");
    }
}

public class Main {
    public static void main(String[] args) {
        Payment payment = new CreditCardPayment();
        payment.processPayment();

        payment = new PayPalPayment();
        payment.processPayment();
    }
}

/// ### Problem 10: The same method name in two interfaces ###
/// Create two interfaces `Printer` and `Scanner`, both with a method `connect()`. Create a class `AllInOnePrinter` that
/// implements both and settles the name clash with a single implementation of `connect()`. Show it in `main`.
///
/// NOTE: `Scanner` is also the name of `java.util.Scanner`. That only matters if you import it, which this program does not.

interface Printer {
    void connect();
}

interface Scanner {
    void connect();
}

class AllInOnePrinter implements Printer, Scanner {
    @Override
    public void connect() {
        System.out.println("The all-in-one device is connected as a printer and as a scanner");
    }
}

public class Main {
    public static void main(String[] args) {
        AllInOnePrinter device = new AllInOnePrinter();
        device.connect();

        Printer printer = device;
        Scanner scanner = device;
        printer.connect();       // the same single method, reached from both interfaces
        scanner.connect();
    }
}

/// ### Problem 11: A clash between two default methods ###
/// Create two interfaces `InterfaceA` and `InterfaceB`, both with a default method `show()`. Create a class `ClassC` that
/// implements both and overrides `show()` to settle the conflict. Show it in `main`.
///
/// --- First, the problem -----
/// Both interfaces hand `ClassC` a different `show()`. Java will not guess which one you want.

interface InterfaceA {
    default void show() {
        System.out.println("show() from InterfaceA");
    }
}

interface InterfaceB {
    default void show() {
        System.out.println("show() from InterfaceB");
    }
}

class ClassC implements InterfaceA, InterfaceB {
}

public class Main {
    public static void main(String[] args) {
        new ClassC().show();
    }
}
/// @error

/// --- Then, the fix -----
/// `ClassC` must override `show()`. Inside it you can pick one version with `InterfaceName.super.show()`, call both, or write
/// something new.

interface InterfaceA {
    default void show() {
        System.out.println("show() from InterfaceA");
    }
}

interface InterfaceB {
    default void show() {
        System.out.println("show() from InterfaceB");
    }
}

class ClassC implements InterfaceA, InterfaceB {
    @Override
    public void show() {
        InterfaceA.super.show();          // choose A's version...
        InterfaceB.super.show();          // ...and B's as well
        System.out.println("show() from ClassC settles the conflict");
    }
}

public class Main {
    public static void main(String[] args) {
        new ClassC().show();
    }
}

/// This is the real "diamond problem". Java solves it by refusing to compile until the class says which method it means.

/// ### Problem 12: Passing an interface to a method ###
/// Create an interface `Appliance` with a method `start()`. Create `WashingMachine` and `AirConditioner` that implement it.
/// Write a method `operateAppliance()` that takes an `Appliance` and calls its `start()`. Pass it both kinds of object.

interface Appliance {
    void start();
}

class WashingMachine implements Appliance {
    @Override
    public void start() {
        System.out.println("The washing machine starts the wash cycle");
    }
}

class AirConditioner implements Appliance {
    @Override
    public void start() {
        System.out.println("The air conditioner starts cooling the room");
    }
}

public class Main {
    static void operateAppliance(Appliance appliance) {
        System.out.println("Operating an appliance...");
        appliance.start();
    }

    public static void main(String[] args) {
        operateAppliance(new WashingMachine());
        operateAppliance(new AirConditioner());
    }
}

/// `operateAppliance` knows nothing about washing machines or air conditioners. It only needs something that can `start()`, so
/// a brand new appliance class would work with it without changing a line.
