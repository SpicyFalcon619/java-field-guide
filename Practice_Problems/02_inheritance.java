/*
 * Practice: Inheritance problem set
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - Building a subclass with `extends` and reusing everything the parent already has
///   - Calling a parent's constructor and methods with `super`
///   - Overriding methods, and `protected` members that only subclasses can reach
///   - Multilevel and hierarchical inheritance, upcasting and downcasting
///   - What `final` and interfaces do to an inheritance hierarchy
/// ============================================================================

/// Fifteen problems, from the very first `extends` to interfaces. Read each one, try it on your own, then compare with the
/// worked solution. Every solution is a complete program you can copy and run.

/// ### Problem 1: Animal and Dog ###
/// Create a base class `Animal` with a method `sound()` that prints "This is an animal sound." Create a derived class `Dog`
/// that extends `Animal` and overrides `sound()` to print "Bark". Create a `Dog` in `main` and call `sound()`.

class Animal {
    void sound() {
        System.out.println("This is an animal sound.");
    }
}

class Dog extends Animal {
    @Override
    void sound() {
        System.out.println("Bark");
    }
}

public class Main {
    public static void main(String[] args) {
        Dog dog = new Dog();
        dog.sound();
    }
}

/// `@Override` is optional, but it asks the compiler to check that you really are overriding something, so a typo such as
/// `sond()` becomes an error instead of a silent new method.

/// ### Problem 2: Person and Student ###
/// Define a base class `Person` with a constructor that sets the `name`. Create a subclass `Student` that adds a `rollNo`.
/// Call the base class constructor from the subclass and print both the name and the roll number.

class Person {
    String name;

    Person(String name) {
        this.name = name;
    }
}

class Student extends Person {
    int rollNo;

    Student(String name, int rollNo) {
        super(name);                 // runs Person's constructor first
        this.rollNo = rollNo;
    }
}

public class Main {
    public static void main(String[] args) {
        Student s = new Student("Mim", 42);
        System.out.println("Name: " + s.name);
        System.out.println("Roll no: " + s.rollNo);
    }
}

/// `super(name)` must be the first line of the constructor. `Person` has no empty constructor, so without that call `Student`
/// would not compile.

/// ### Problem 3: Vehicle and Car ###
/// Create a class `Vehicle` with a method `move()` that prints "Vehicle is moving." Create a subclass `Car` that overrides
/// `move()` to print "Car is moving fast." Show method overriding by creating objects of both classes and calling `move()`.

class Vehicle {
    void move() {
        System.out.println("Vehicle is moving.");
    }
}

class Car extends Vehicle {
    @Override
    void move() {
        System.out.println("Car is moving fast.");
    }
}

public class Main {
    public static void main(String[] args) {
        Vehicle vehicle = new Vehicle();
        Car car = new Car();

        vehicle.move();
        car.move();
    }
}

/// ### Problem 4: Parent and Child with super ###
/// Create a class `Parent` with a method `showMessage()` that prints "This is the parent class." Create a subclass `Child`
/// that overrides `showMessage()` but also calls the parent's version with `super`.

class Parent {
    void showMessage() {
        System.out.println("This is the parent class.");
    }
}

class Child extends Parent {
    @Override
    void showMessage() {
        super.showMessage();                       // the parent's version first
        System.out.println("This is the child class.");
    }
}

public class Main {
    public static void main(String[] args) {
        new Child().showMessage();
    }
}

/// Overriding replaces the parent's method, but `super.showMessage()` lets the child still use it and add to it.

/// ### Problem 5: A protected member ###
/// Create a class `Person` with a `protected` attribute `name`. Create a subclass `Employee` that inherits from `Person` and
/// sets the value of `name` from within the subclass. Show the access to the protected member from the subclass.

class Person {
    protected String name;
}

class Employee extends Person {
    void setName(String newName) {
        name = newName;                        // allowed: Employee is a subclass of Person
    }

    void show() {
        System.out.println("Employee name: " + name);
    }
}

public class Main {
    public static void main(String[] args) {
        Employee e = new Employee();
        e.setName("Rafi");
        e.show();
    }
}

/// NOTE: `protected` means "this class, its subclasses, and the classes in the same package". Code that is in neither
/// (an unrelated class in another package) cannot touch `name`.

/// ### Problem 6: Multilevel inheritance ###
/// Create a base class `Appliance` with a method `turnOn()`. Create `WashingMachine` that inherits from `Appliance` and adds
/// `startWash()`. Finally create `AutomaticWashingMachine` that inherits from `WashingMachine` and adds `autoStart()`. Show the
/// several levels of inheritance in `main`.

class Appliance {
    void turnOn() {
        System.out.println("The appliance is on");
    }
}

class WashingMachine extends Appliance {
    void startWash() {
        System.out.println("Washing started");
    }
}

class AutomaticWashingMachine extends WashingMachine {
    void autoStart() {
        turnOn();                // from Appliance, two levels up
        startWash();             // from WashingMachine, one level up
        System.out.println("Automatic cycle running");
    }
}

public class Main {
    public static void main(String[] args) {
        AutomaticWashingMachine machine = new AutomaticWashingMachine();
        machine.turnOn();
        machine.startWash();
        machine.autoStart();
    }
}

/// The bottom class has all three methods: its own, its parent's and its grandparent's.

/// ### Problem 7: Constructor chaining with super ###
/// Create a base class `Vehicle` with an attribute `maxSpeed` and a constructor that sets it. Create a subclass `Car` that uses
/// `super` to call the parent constructor to initialize `maxSpeed`. Add an attribute `brand` to `Car` and show the
/// constructor chaining.

class Vehicle {
    int maxSpeed;

    Vehicle(int maxSpeed) {
        this.maxSpeed = maxSpeed;
        System.out.println("Vehicle constructor: maxSpeed = " + maxSpeed);
    }
}

class Car extends Vehicle {
    String brand;

    Car(int maxSpeed, String brand) {
        super(maxSpeed);                       // chain up to Vehicle first
        this.brand = brand;
        System.out.println("Car constructor: brand = " + brand);
    }
}

public class Main {
    public static void main(String[] args) {
        Car car = new Car(180, "Toyota");
        System.out.println(car.brand + " tops out at " + car.maxSpeed + " km/h");
    }
}

/// The output shows the order: the parent's constructor finishes before the child's one continues.

/// ### Problem 8: Hierarchical inheritance ###
/// Define a base class `Shape` with a method `draw()`. Create two subclasses `Circle` and `Square` that override `draw()` to
/// print different messages. Call `draw()` on objects of both to show hierarchical inheritance.

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
        new Circle().draw();
        new Square().draw();
    }
}

/// "Hierarchical" means one parent with several children, here `Circle` and `Square` both extend `Shape`.

/// ### Problem 9: Upcasting and downcasting ###
/// Create a base class `Animal` and a subclass `Cat`. In `main`, create a `Cat` and upcast it to `Animal`. Then downcast it
/// back to `Cat` and call a method that exists only in `Cat`.

class Animal {
    void eat() {
        System.out.println("The animal eats");
    }
}

class Cat extends Animal {
    void purr() {
        System.out.println("The cat purrs");
    }
}

public class Main {
    public static void main(String[] args) {
        Animal animal = new Cat();      // upcast: automatic and always safe
        animal.eat();
        // animal.purr();               // would not compile: an Animal reference only knows Animal's methods

        if (animal instanceof Cat) {    // check first, so the cast cannot fail
            Cat cat = (Cat) animal;     // downcast: you must write it yourself
            cat.purr();
        }
    }
}

/// The object was a `Cat` all along. The reference type decides which methods you may call, and the downcast gives back the
/// full `Cat` view. Casting an object to the wrong class throws a `ClassCastException`, which is why `instanceof` goes first.

/// ### Problem 10: An array of employees ###
/// Create a class `Employee` with a method `work()`. Create two subclasses `Manager` and `Developer` that override `work()`.
/// In `main`, create an array of `Employee` that holds both a `Manager` and a `Developer`, and call `work()` for each.

class Employee {
    void work() {
        System.out.println("The employee works");
    }
}

class Manager extends Employee {
    @Override
    void work() {
        System.out.println("The manager plans the project");
    }
}

class Developer extends Employee {
    @Override
    void work() {
        System.out.println("The developer writes code");
    }
}

public class Main {
    public static void main(String[] args) {
        Employee[] team = { new Manager(), new Developer(), new Employee() };

        for (Employee e : team) {
            e.work();          // each object runs its own version
        }
    }
}

/// ### Problem 11: An abstract Employee ###
/// Create an abstract class `Employee` with an abstract method `calculateSalary()`. Create two subclasses `FullTimeEmployee`
/// and `PartTimeEmployee` that implement it. Show polymorphism by calling the method on objects of both subclasses.

abstract class Employee {
    String name;

    Employee(String name) {
        this.name = name;
    }

    abstract double calculateSalary();
}

class FullTimeEmployee extends Employee {
    double monthlySalary;

    FullTimeEmployee(String name, double monthlySalary) {
        super(name);
        this.monthlySalary = monthlySalary;
    }

    @Override
    double calculateSalary() {
        return monthlySalary;
    }
}

class PartTimeEmployee extends Employee {
    double hourlyRate;
    int hoursWorked;

    PartTimeEmployee(String name, double hourlyRate, int hoursWorked) {
        super(name);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    @Override
    double calculateSalary() {
        return hourlyRate * hoursWorked;
    }
}

public class Main {
    public static void main(String[] args) {
        Employee[] staff = {
            new FullTimeEmployee("Rafi", 60000),
            new PartTimeEmployee("Mim", 350, 80)
        };

        for (Employee e : staff) {
            System.out.println(e.name + " earns " + e.calculateSalary());
        }
    }
}

/// You cannot write `new Employee(...)`: an abstract class is only a base for other classes. The loop does not care which kind
/// of employee it holds, because each one knows how to work out its own salary.

/// ### Problem 12: getDetails with super ###
/// Create a class `Person` with a method `getDetails()` that returns the person's name. Create a subclass `Employee` that
/// overrides `getDetails()` to return both the name and the employee ID. Use `super` in `Employee` to call `getDetails()` of
/// `Person` and combine the result.

class Person {
    String name;

    Person(String name) {
        this.name = name;
    }

    String getDetails() {
        return name;
    }
}

class Employee extends Person {
    int employeeId;

    Employee(String name, int employeeId) {
        super(name);
        this.employeeId = employeeId;
    }

    @Override
    String getDetails() {
        return super.getDetails() + " (employee ID " + employeeId + ")";
    }
}

public class Main {
    public static void main(String[] args) {
        Person p = new Person("Rizvy");
        Employee e = new Employee("Mim", 2041);

        System.out.println(p.getDetails());
        System.out.println(e.getDetails());
    }
}

/// ### Problem 13: What `final` does to inheritance ###
/// Create a class `Vehicle` with a `final` method `start()`. Create a subclass `Car` that tries to override `start()` and watch
/// the error. Then try to make the `Vehicle` class itself `final` and see what that does to the hierarchy.
///
/// --- Part 1: overriding a final method -----

class Vehicle {
    final void start() {
        System.out.println("Vehicle started");
    }
}

class Car extends Vehicle {
    @Override
    void start() {                     // error: a final method cannot be overridden
        System.out.println("Car started");
    }
}

public class Main {
    public static void main(String[] args) {
        new Car().start();
    }
}
/// @error

/// The compiler refuses: `final` on a method means "this exact method is not allowed to change in any subclass".
///
/// --- Part 2: extending a final class -----

final class Vehicle {
    void start() {
        System.out.println("Vehicle started");
    }
}

class Car extends Vehicle {            // error: cannot inherit from a final class
}

public class Main {
    public static void main(String[] args) {
        new Car().start();
    }
}
/// @error

/// `final` on a class goes further: nobody can extend it at all. `String` is a famous final class. Inheriting the `final`
/// method is still fine, as this version shows:

class Vehicle {
    final void start() {
        System.out.println("Vehicle started");
    }
}

class Car extends Vehicle {
    void honk() {
        System.out.println("Beep");
    }
}

public class Main {
    public static void main(String[] args) {
        Car car = new Car();
        car.start();         // inherited as it is
        car.honk();
    }
}

/// ### Problem 14: Two interfaces, one class ###
/// Create two interfaces `Teacher` and `Researcher`, both with a method `work()`. Create a class `Professor` that implements
/// both and gives its own implementation of `work()`. Call `work()` on a `Professor` to show multiple inheritance.
///
/// NOTE: A class can extend only ONE class, but it can implement as many interfaces as it likes. This is how Java gets the
/// benefit of multiple inheritance.

interface Teacher {
    void work();
}

interface Researcher {
    void work();
}

class Professor implements Teacher, Researcher {
    @Override
    public void work() {
        System.out.println("The professor teaches a class and then writes a paper");
    }
}

public class Main {
    public static void main(String[] args) {
        Professor professor = new Professor();
        professor.work();

        Teacher teacher = professor;         // a Professor is also a Teacher...
        Researcher researcher = professor;   // ...and a Researcher
        teacher.work();
        researcher.work();
    }
}

/// One `work()` in `Professor` satisfies both interfaces at once, because an interface method has no body to clash with.

/// ### Problem 15: The diamond problem ###
/// Create two interfaces `A` and `B`, both with a method `display()`. Create a class `C` that implements both. Handle the
/// diamond problem by giving `C` its own `display()`, and call it in `main`.

interface A {
    void display();
}

interface B {
    void display();
}

class C implements A, B {
    @Override
    public void display() {
        System.out.println("C displays for both A and B");
    }
}

public class Main {
    public static void main(String[] args) {
        C c = new C();
        c.display();

        A a = c;
        B b = c;
        a.display();
        b.display();
    }
}

/// With abstract methods there is nothing to be confused about, so one implementation settles it. The real "diamond" trouble
/// starts when both interfaces give `display()` a default body: then the class MUST choose. The Interface problem set,
/// problem 11, shows that case.
