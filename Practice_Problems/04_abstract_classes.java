/*
 * Practice: Abstract classes problem set
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - Declaring an `abstract` class and `abstract` methods that subclasses must implement
///   - Mixing abstract methods with ordinary ones (and `final` ones) in the same class
///   - Constructors in an abstract class, called from subclasses with `super`
///   - Combining an abstract class with an interface, and chains of abstract classes
/// ============================================================================

/// Twelve problems on abstract classes. Remember the two rules: you cannot create an object of an abstract class, and a
/// subclass must implement every abstract method (or be abstract itself).

/// ### Problem 1: Animal sounds ###
/// Create an abstract class `Animal` with an abstract method `sound()`. Create two subclasses `Dog` and `Cat` that implement
/// `sound()`. Create objects of both in `main` and call `sound()`.

abstract class Animal {
    abstract void sound();
}

class Dog extends Animal {
    @Override
    void sound() {
        System.out.println("Dog says Woof");
    }
}

class Cat extends Animal {
    @Override
    void sound() {
        System.out.println("Cat says Meow");
    }
}

public class Main {
    public static void main(String[] args) {
        Animal dog = new Dog();
        Animal cat = new Cat();

        dog.sound();
        cat.sound();
        // Animal generic = new Animal();     // error: Animal is abstract, it cannot be instantiated
    }
}

/// ### Problem 2: A shape with a description ###
/// Define an abstract class `Shape` with an abstract method `area()` and a concrete method `description()` that prints "This
/// is a shape". Create subclasses `Circle` and `Rectangle` that implement `area()`. Call both `description()` and `area()` on
/// objects of each.

abstract class Shape {
    abstract double area();                 // each shape works this out its own way

    void description() {                    // shared by every shape, written once
        System.out.println("This is a shape");
    }
}

class Circle extends Shape {
    double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    @Override
    double area() {
        return Math.PI * radius * radius;
    }
}

class Rectangle extends Shape {
    double length;
    double width;

    Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    @Override
    double area() {
        return length * width;
    }
}

public class Main {
    public static void main(String[] args) {
        Shape[] shapes = { new Circle(3), new Rectangle(4, 5) };

        for (Shape s : shapes) {
            s.description();
            System.out.printf("Area = %.2f%n", s.area());
        }
    }
}

/// An abstract class is allowed to mix both kinds of method: `description()` is inherited as it is, `area()` must be provided.

/// ### Problem 3: A constructor in an abstract class ###
/// Create an abstract class `Vehicle` with a constructor that takes a String `type` and stores it. Create subclasses `Car` and
/// `Bike` that provide their own `getFuelType()`. Show that the abstract class's constructor is used.

abstract class Vehicle {
    String type;

    Vehicle(String type) {
        this.type = type;
        System.out.println("Vehicle constructor: this is a " + type);
    }

    abstract String getFuelType();
}

class Car extends Vehicle {
    Car() {
        super("car");                  // runs the abstract class's constructor
    }

    @Override
    String getFuelType() {
        return "petrol or diesel";
    }
}

class Bike extends Vehicle {
    Bike() {
        super("bike");
    }

    @Override
    String getFuelType() {
        return "petrol";
    }
}

public class Main {
    public static void main(String[] args) {
        Vehicle car = new Car();
        Vehicle bike = new Bike();

        System.out.println(car.type + " uses " + car.getFuelType());
        System.out.println(bike.type + " uses " + bike.getFuelType());
    }
}

/// You cannot call `new Vehicle(...)`, but `Vehicle` can still have a constructor. It runs when a subclass is created, through
/// `super(...)`, and sets up the part of the object that belongs to the parent.

/// ### Problem 4: Employee salaries ###
/// Create an abstract class `Employee` with an abstract method `getSalary()` that returns a double. Create subclasses
/// `FullTimeEmployee` and `PartTimeEmployee`, each with its own `getSalary()`. Create instances of both in `main` and display
/// their salaries.

abstract class Employee {
    String name;

    Employee(String name) {
        this.name = name;
    }

    abstract double getSalary();
}

class FullTimeEmployee extends Employee {
    double monthlySalary;

    FullTimeEmployee(String name, double monthlySalary) {
        super(name);
        this.monthlySalary = monthlySalary;
    }

    @Override
    double getSalary() {
        return monthlySalary;
    }
}

class PartTimeEmployee extends Employee {
    double hourlyRate;
    int hours;

    PartTimeEmployee(String name, double hourlyRate, int hours) {
        super(name);
        this.hourlyRate = hourlyRate;
        this.hours = hours;
    }

    @Override
    double getSalary() {
        return hourlyRate * hours;
    }
}

public class Main {
    public static void main(String[] args) {
        Employee full = new FullTimeEmployee("Rafi", 55000);
        Employee part = new PartTimeEmployee("Mim", 400, 60);

        System.out.println(full.name + ": " + full.getSalary());
        System.out.println(part.name + ": " + part.getSalary());
    }
}

/// ### Problem 5: An array of instruments ###
/// Create an abstract class `Instrument` with an abstract method `play()`. Create subclasses `Piano` and `Guitar` that
/// implement it. In `main`, create an array of `Instrument` references holding a `Piano` and a `Guitar`, and call `play()` on
/// each.

abstract class Instrument {
    abstract void play();
}

class Piano extends Instrument {
    @Override
    void play() {
        System.out.println("Piano: plink plonk");
    }
}

class Guitar extends Instrument {
    @Override
    void play() {
        System.out.println("Guitar: strum strum");
    }
}

public class Main {
    public static void main(String[] args) {
        Instrument[] band = { new Piano(), new Guitar() };

        for (Instrument instrument : band) {
            instrument.play();
        }
    }
}

/// ### Problem 6: Appliances ###
/// Define an abstract class `Appliance` with an abstract method `turnOn()` and a concrete method `powerInfo()` that prints
/// "This appliance uses electricity." Create subclasses `WashingMachine` and `Microwave` that implement `turnOn()`. Call both
/// `turnOn()` and `powerInfo()` in `main`.

abstract class Appliance {
    abstract void turnOn();

    void powerInfo() {
        System.out.println("This appliance uses electricity.");
    }
}

class WashingMachine extends Appliance {
    @Override
    void turnOn() {
        System.out.println("The washing machine fills with water");
    }
}

class Microwave extends Appliance {
    @Override
    void turnOn() {
        System.out.println("The microwave starts heating");
    }
}

public class Main {
    public static void main(String[] args) {
        Appliance[] appliances = { new WashingMachine(), new Microwave() };

        for (Appliance a : appliances) {
            a.turnOn();
            a.powerInfo();
        }
    }
}

/// ### Problem 7: Teacher and Student greetings ###
/// Create an abstract class `Person` with an abstract method `getDetails()` and a concrete method `sayHello()` that prints
/// "Hello!". Create subclasses `Teacher` and `Student` that implement `getDetails()` and also override `sayHello()` with their
/// own greetings. Show both overriding and abstract class implementation.

abstract class Person {
    String name;

    Person(String name) {
        this.name = name;
    }

    abstract String getDetails();             // must be implemented

    void sayHello() {                         // may be overridden
        System.out.println("Hello!");
    }
}

class Teacher extends Person {
    Teacher(String name) {
        super(name);
    }

    @Override
    String getDetails() {
        return "Teacher " + name;
    }

    @Override
    void sayHello() {
        System.out.println("Good morning, class!");
    }
}

class Student extends Person {
    Student(String name) {
        super(name);
    }

    @Override
    String getDetails() {
        return "Student " + name;
    }

    @Override
    void sayHello() {
        System.out.println("Good morning, sir!");
    }
}

public class Main {
    public static void main(String[] args) {
        Person[] people = { new Teacher("Karim"), new Student("Mim") };

        for (Person p : people) {
            System.out.println(p.getDetails());
            p.sayHello();
        }
    }
}

/// `getDetails()` is abstract, so a subclass has no choice. `sayHello()` already has a body, so overriding it is a choice. A
/// subclass that does not override it would simply print "Hello!".

/// ### Problem 8: An abstract class and an interface in one class ###
/// Create an abstract class `Shape` with an abstract method `area()`, and an interface `Drawable` with a method `draw()`.
/// Create a class `Circle` that extends `Shape` and implements `Drawable`, giving both `area()` and `draw()`.

abstract class Shape {
    abstract double area();
}

interface Drawable {
    void draw();
}

class Circle extends Shape implements Drawable {
    double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    @Override
    double area() {
        return Math.PI * radius * radius;
    }

    @Override
    public void draw() {
        System.out.println("Drawing a circle with radius " + radius);
    }
}

public class Main {
    public static void main(String[] args) {
        Circle circle = new Circle(2.5);

        circle.draw();
        System.out.printf("Area = %.2f%n", circle.area());
    }
}

/// The order in the header is fixed: `extends` (one class) first, then `implements` (any number of interfaces).

/// ### Problem 9: Constructor chaining through an abstract class ###
/// Create an abstract class `Machine` with a constructor that takes an int `id`. Create subclasses `Computer` and `Printer`
/// that use `Machine`'s constructor through `super()`. Override a method `start()` in both. Show the constructor chaining.

abstract class Machine {
    int id;

    Machine(int id) {
        this.id = id;
        System.out.println("Machine constructor: id " + id);
    }

    abstract void start();
}

class Computer extends Machine {
    Computer(int id) {
        super(id);
        System.out.println("Computer constructor");
    }

    @Override
    void start() {
        System.out.println("Computer " + id + " is booting");
    }
}

class Printer extends Machine {
    Printer(int id) {
        super(id);
        System.out.println("Printer constructor");
    }

    @Override
    void start() {
        System.out.println("Printer " + id + " is warming up");
    }
}

public class Main {
    public static void main(String[] args) {
        Machine pc = new Computer(101);
        pc.start();
        System.out.println();

        Machine printer = new Printer(202);
        printer.start();
    }
}

/// ### Problem 10: A final method inside an abstract class ###
/// Create an abstract class `Device` with a `final` method `deviceInfo()` that prints "This is a device." and an abstract
/// method `functionality()`. Create subclasses `Phone` and `Tablet` that implement `functionality()`. Call both methods on
/// instances of each.

abstract class Device {
    final void deviceInfo() {                 // final: no subclass may change it
        System.out.println("This is a device.");
    }

    abstract void functionality();
}

class Phone extends Device {
    @Override
    void functionality() {
        System.out.println("A phone makes calls and sends messages");
    }
}

class Tablet extends Device {
    @Override
    void functionality() {
        System.out.println("A tablet is for reading and drawing");
    }
}

public class Main {
    public static void main(String[] args) {
        Device[] devices = { new Phone(), new Tablet() };

        for (Device d : devices) {
            d.deviceInfo();
            d.functionality();
        }
    }
}

/// Together, `final` and `abstract` give a template: one part every subclass must keep exactly as it is, one part every
/// subclass must fill in.

/// ### Problem 11: A chain of abstract classes ###
/// Create an abstract class `LivingBeing` with an abstract method `breathe()`. Create an abstract subclass `Animal` that adds
/// another abstract method `move()`. Create a class `Bird` that implements both methods. Show the use of several abstract
/// classes in one chain.

abstract class LivingBeing {
    abstract void breathe();
}

abstract class Animal extends LivingBeing {
    abstract void move();                     // breathe() is still not implemented here
}

class Bird extends Animal {
    @Override
    void breathe() {
        System.out.println("The bird breathes through its lungs");
    }

    @Override
    void move() {
        System.out.println("The bird flies");
    }
}

public class Main {
    public static void main(String[] args) {
        Bird bird = new Bird();
        bird.breathe();
        bird.move();

        LivingBeing being = bird;             // a Bird is an Animal and a LivingBeing
        being.breathe();
    }
}

/// `Animal` is abstract too, so it does not have to implement `breathe()`. The first concrete class in the chain, `Bird`, must
/// implement everything that is still abstract above it.

/// ### Problem 12: Interest on bank accounts ###
/// Create an abstract class `BankAccount` with an abstract method `calculateInterest()`. Create `SavingsAccount` and
/// `CurrentAccount` that implement it differently. In `main`, use a `BankAccount` reference to call `calculateInterest()` on
/// both types of account.

abstract class BankAccount {
    String owner;
    double balance;

    BankAccount(String owner, double balance) {
        this.owner = owner;
        this.balance = balance;
    }

    abstract double calculateInterest();
}

class SavingsAccount extends BankAccount {
    SavingsAccount(String owner, double balance) {
        super(owner, balance);
    }

    @Override
    double calculateInterest() {
        return balance * 0.04;                // 4% a year
    }
}

class CurrentAccount extends BankAccount {
    CurrentAccount(String owner, double balance) {
        super(owner, balance);
    }

    @Override
    double calculateInterest() {
        return balance * 0.005;               // only 0.5% a year
    }
}

public class Main {
    public static void main(String[] args) {
        BankAccount[] accounts = {
            new SavingsAccount("Rafi", 100000),
            new CurrentAccount("Mim", 100000)
        };

        for (BankAccount account : accounts) {
            System.out.println(account.owner + " earns " + account.calculateInterest() + " in interest");
        }
    }
}
