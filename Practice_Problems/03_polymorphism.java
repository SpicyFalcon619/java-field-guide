/*
 * Practice: Polymorphism problem set
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - One reference type, many kinds of object: a `Shape` variable holding a `Circle` or a `Square`
///   - Runtime polymorphism: the object's real class decides which overridden method runs
///   - Polymorphism through abstract classes and interfaces, and through arrays of references
///   - The difference between overloading (same class, chosen when compiling) and overriding (subclass, chosen when running)
/// ============================================================================

/// Thirteen problems on one idea: you write the call once, and the object decides what happens. Try each one before you read
/// the solution.

/// ### Problem 1: Dog and Cat ###
/// Create a base class `Animal` with a method `makeSound()` that prints "Some generic animal sound." Create two subclasses
/// `Dog` and `Cat` that override it to print "Bark" and "Meow". In `main`, create a `Dog` and a `Cat` and call `makeSound()`.

class Animal {
    void makeSound() {
        System.out.println("Some generic animal sound.");
    }
}

class Dog extends Animal {
    @Override
    void makeSound() {
        System.out.println("Bark");
    }
}

class Cat extends Animal {
    @Override
    void makeSound() {
        System.out.println("Meow");
    }
}

public class Main {
    public static void main(String[] args) {
        Dog dog = new Dog();
        Cat cat = new Cat();

        dog.makeSound();
        cat.makeSound();
    }
}

/// ### Problem 2: A Shape reference ###
/// Create a superclass `Shape` with a method `draw()`. Create subclasses `Circle` and `Square` that override `draw()`. Show
/// polymorphism by creating a `Shape` reference and assigning it to both a `Circle` and a `Square` object.

class Shape {
    void draw() {
        System.out.println("Drawing a shape");
    }
}

class Circle extends Shape {
    @Override
    void draw() {
        System.out.println("Drawing a circle");
    }
}

class Square extends Shape {
    @Override
    void draw() {
        System.out.println("Drawing a square");
    }
}

public class Main {
    public static void main(String[] args) {
        Shape shape;                 // one variable...

        shape = new Circle();
        shape.draw();                // ...that holds a Circle

        shape = new Square();
        shape.draw();                // ...and later a Square
    }
}

/// The call `shape.draw()` is written once, yet it prints a different line each time. Java looks at the real object when the
/// program runs, not at the type of the variable. That is runtime polymorphism.

/// ### Problem 3: Upcasting a Car ###
/// Define a class `Vehicle` with a method `move()`. Create a subclass `Car` that overrides `move()`. In `main`, create a `Car`,
/// upcast it to a `Vehicle` reference and call `move()`.

class Vehicle {
    void move() {
        System.out.println("The vehicle moves");
    }
}

class Car extends Vehicle {
    @Override
    void move() {
        System.out.println("The car drives on the road");
    }
}

public class Main {
    public static void main(String[] args) {
        Car car = new Car();
        Vehicle vehicle = car;        // upcast: no cast needed, a Car is a Vehicle

        vehicle.move();               // still the Car's version
    }
}

/// ### Problem 4: Managers and developers ###
/// Create a class `Employee` with a method `work()`. Create two subclasses `Manager` and `Developer` that override `work()`.
/// In `main`, create an array of `Employee` that contains both a `Manager` and a `Developer`, and call `work()` for each.

class Employee {
    void work() {
        System.out.println("The employee does some work");
    }
}

class Manager extends Employee {
    @Override
    void work() {
        System.out.println("The manager runs a meeting");
    }
}

class Developer extends Employee {
    @Override
    void work() {
        System.out.println("The developer fixes a bug");
    }
}

public class Main {
    public static void main(String[] args) {
        Employee[] employees = new Employee[3];
        employees[0] = new Manager();
        employees[1] = new Developer();
        employees[2] = new Developer();

        for (Employee employee : employees) {
            employee.work();
        }
    }
}

/// An array of the parent type is the classic polymorphism trick: one loop, several kinds of object. A new `Tester` class
/// could be added later and this loop would not need to change.

/// ### Problem 5: An interface, two classes ###
/// Create an interface `Playable` with a method `play()`. Create two classes `Guitar` and `Piano` that implement it with their
/// own `play()`. Show polymorphism by calling `play()` on objects of both.

interface Playable {
    void play();
}

class Guitar implements Playable {
    @Override
    public void play() {
        System.out.println("Strumming the guitar");
    }
}

class Piano implements Playable {
    @Override
    public void play() {
        System.out.println("Playing the piano keys");
    }
}

public class Main {
    public static void main(String[] args) {
        Playable first = new Guitar();
        Playable second = new Piano();

        first.play();
        second.play();
    }
}

/// ### Problem 6: Overloading and overriding together ###
/// Create a class `Calculator` with two overloaded `add()` methods, one for integers and one for doubles. Create a subclass
/// `ScientificCalculator` that overrides `add()` to perform a different calculation. Show overloading and overriding in `main`.

class Calculator {
    int add(int a, int b) {                      // overload 1
        return a + b;
    }

    double add(double a, double b) {             // overload 2: same name, different parameter types
        return a + b;
    }
}

class ScientificCalculator extends Calculator {
    @Override
    double add(double a, double b) {             // override: rounds away floating point noise
        return Math.round((a + b) * 1_000_000) / 1_000_000.0;
    }
}

public class Main {
    public static void main(String[] args) {
        Calculator plain = new Calculator();
        Calculator scientific = new ScientificCalculator();

        System.out.println("add(2, 3) = " + plain.add(2, 3));                 // the int version
        System.out.println("add(0.1, 0.2) plain      = " + plain.add(0.1, 0.2));
        System.out.println("add(0.1, 0.2) scientific = " + scientific.add(0.1, 0.2));
    }
}

/// Overloading is decided by the COMPILER from the argument types (two ints pick the int version). Overriding is decided at
/// run time by the OBJECT: the same call `add(0.1, 0.2)` gives a different answer on a `ScientificCalculator`.

/// ### Problem 7: Abstract Animal ###
/// Create an abstract class `Animal` with an abstract method `eat()`. Create subclasses `Lion` and `Elephant` that implement
/// `eat()` differently. Create objects of both and call `eat()` to show runtime polymorphism.

abstract class Animal {
    abstract void eat();
}

class Lion extends Animal {
    @Override
    void eat() {
        System.out.println("The lion eats meat");
    }
}

class Elephant extends Animal {
    @Override
    void eat() {
        System.out.println("The elephant eats leaves and grass");
    }
}

public class Main {
    public static void main(String[] args) {
        Animal a = new Lion();
        Animal b = new Elephant();

        a.eat();
        b.eat();
    }
}

/// ### Problem 8: Teacher and Student ###
/// Define a class `Person` with a method `getDetails()`. Create subclasses `Teacher` and `Student` that both override it. Use
/// polymorphism: a `Person` reference assigned to a `Teacher` and then to a `Student`.

class Person {
    String name;

    Person(String name) {
        this.name = name;
    }

    String getDetails() {
        return "Person: " + name;
    }
}

class Teacher extends Person {
    String subject;

    Teacher(String name, String subject) {
        super(name);
        this.subject = subject;
    }

    @Override
    String getDetails() {
        return "Teacher: " + name + ", teaches " + subject;
    }
}

class Student extends Person {
    int grade;

    Student(String name, int grade) {
        super(name);
        this.grade = grade;
    }

    @Override
    String getDetails() {
        return "Student: " + name + ", grade " + grade;
    }
}

public class Main {
    public static void main(String[] args) {
        Person p = new Teacher("Mr. Karim", "Mathematics");
        System.out.println(p.getDetails());

        p = new Student("Mim", 9);          // the same variable, now a Student
        System.out.println(p.getDetails());
    }
}

/// ### Problem 9: Device, Phone and SmartPhone ###
/// Create a base class `Device` with a method `start()`. Create a subclass `Phone` that overrides `start()`, then a subclass
/// `SmartPhone` of `Phone` that overrides `start()` again. Create objects of all three and call `start()`.

class Device {
    void start() {
        System.out.println("Device: power on");
    }
}

class Phone extends Device {
    @Override
    void start() {
        System.out.println("Phone: ringing is ready");
    }
}

class SmartPhone extends Phone {
    @Override
    void start() {
        super.start();                                  // Phone's version first
        System.out.println("SmartPhone: apps are loading");
    }
}

public class Main {
    public static void main(String[] args) {
        Device[] devices = { new Device(), new Phone(), new SmartPhone() };

        for (Device d : devices) {
            d.start();
            System.out.println("---");
        }
    }
}

/// Overriding can go down several levels. `SmartPhone` here also calls `super.start()`, so it adds to its parent's behaviour
/// instead of replacing it.

/// ### Problem 10: An array of Printers ###
/// Create an interface `Printer` with a method `print()`. Create two classes `LaserPrinter` and `InkjetPrinter` that implement
/// it. Create an array of `Printer` references and call `print()` on both kinds of object.

interface Printer {
    void print();
}

class LaserPrinter implements Printer {
    @Override
    public void print() {
        System.out.println("Laser printer: fast, sharp black text");
    }
}

class InkjetPrinter implements Printer {
    @Override
    public void print() {
        System.out.println("Inkjet printer: slower, good for colour photos");
    }
}

public class Main {
    public static void main(String[] args) {
        Printer[] printers = { new LaserPrinter(), new InkjetPrinter(), new LaserPrinter() };

        for (Printer printer : printers) {
            printer.print();
        }
    }
}

/// ### Problem 11: An abstract class and an interface together ###
/// Create an abstract class `Shape` with an abstract method `area()`, and an interface `Drawable` with a method `draw()`.
/// Create `Rectangle` and `Circle` that extend `Shape` and implement `Drawable`. Show polymorphism using references of both
/// the `Shape` type and the `Drawable` type.

abstract class Shape {
    abstract double area();
}

interface Drawable {
    void draw();
}

class Rectangle extends Shape implements Drawable {
    double width;
    double height;

    Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    double area() {
        return width * height;
    }

    @Override
    public void draw() {
        System.out.println("Drawing a " + width + " x " + height + " rectangle");
    }
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
        System.out.println("Drawing a circle of radius " + radius);
    }
}

public class Main {
    public static void main(String[] args) {
        Shape[] shapes = { new Rectangle(4, 3), new Circle(2) };
        for (Shape s : shapes) {
            System.out.printf("Area: %.2f%n", s.area());      // through the Shape view
        }

        Drawable[] drawables = { new Rectangle(4, 3), new Circle(2) };
        for (Drawable d : drawables) {
            d.draw();                                         // through the Drawable view
        }
    }
}

/// The same objects can be seen through different references. A `Shape` reference only offers `area()`, a `Drawable` reference
/// only offers `draw()`.

/// ### Problem 12: Overloaded constructors ###
/// Create a class `Person` with overloaded constructors: one with no parameters and one that takes a `String`. Create a
/// subclass `Employee` that inherits from `Person` and overloads its constructors as well. Create different `Employee` objects
/// using both constructors.

class Person {
    String name;

    Person() {
        this("Unknown");                         // reuse the other constructor
        System.out.println("Person() called");
    }

    Person(String name) {
        this.name = name;
        System.out.println("Person(String) called for " + name);
    }
}

class Employee extends Person {
    String role;

    Employee() {
        super();                                 // picks Person()
        this.role = "Trainee";
        System.out.println("Employee() called");
    }

    Employee(String name) {
        super(name);                             // picks Person(String)
        this.role = "Staff";
        System.out.println("Employee(String) called");
    }
}

public class Main {
    public static void main(String[] args) {
        Employee first = new Employee();
        System.out.println(first.name + " is a " + first.role);
        System.out.println();

        Employee second = new Employee("Rafi");
        System.out.println(second.name + " is a " + second.role);
    }
}

/// Which constructor runs is decided by the arguments you pass, which is overloading. The parent's constructor always runs
/// first.

/// ### Problem 13: Payments ###
/// Create a base class `Payment` with a method `processPayment()`. Create two subclasses `CreditCardPayment` and
/// `PayPalPayment` that override it. In `main`, create a `Payment` reference and assign it to both types of object to process
/// payments in different ways.

class Payment {
    void processPayment() {
        System.out.println("Processing a generic payment");
    }
}

class CreditCardPayment extends Payment {
    @Override
    void processPayment() {
        System.out.println("Charging the credit card");
    }
}

class PayPalPayment extends Payment {
    @Override
    void processPayment() {
        System.out.println("Redirecting to PayPal");
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
