/*
 * Lab: Lab 4: Inheritance and method overriding
 */

/// ============================================================================
/// WHAT YOU WILL PRACTISE
///   - Building a class hierarchy with `extends`
///   - Using `super` for constructors and methods
///   - Overriding methods, and `@Override`
///   - Polymorphism: one array, many kinds of object
/// ============================================================================


/// ### Task 1: Animals ###
/// Make a parent class `Animal` with a name and a method `speak()`. Make `Dog` and `Cat` children that override `speak()`. Put
/// one of each in an `Animal` array and call `speak()` on all.

class Animal {
    String name;

    Animal(String name) {
        this.name = name;
    }

    void speak() {
        System.out.println(name + " makes a sound");
    }
}

class Dog extends Animal {
    Dog(String name) {
        super(name);
    }

    @Override
    void speak() {
        System.out.println(name + " says Woof");
    }
}

class Cat extends Animal {
    Cat(String name) {
        super(name);
    }

    @Override
    void speak() {
        System.out.println(name + " says Meow");
    }
}

public class Main {
    public static void main(String[] args) {
        Animal[] pets = { new Dog("Rex"), new Cat("Tom"), new Animal("Thing") };
        for (Animal a : pets) {
            a.speak();
        }
    }
}

/// ### Task 2: Employees and salaries ###
/// `Employee` has a name and a base salary and a method `pay()` that returns the monthly pay (the base salary). `Manager` adds
/// a bonus, and `Intern` is paid half. Use `super` in the overriding methods where it helps. Print every employee's pay.

class Employee {
    String name;
    double base;

    Employee(String name, double base) {
        this.name = name;
        this.base = base;
    }

    double pay() {
        return base;
    }

    @Override
    public String toString() {
        return name + " earns " + pay();
    }
}

class Manager extends Employee {
    double bonus;

    Manager(String name, double base, double bonus) {
        super(name, base);
        this.bonus = bonus;
    }

    @Override
    double pay() {
        return super.pay() + bonus;
    }
}

class Intern extends Employee {
    Intern(String name, double base) {
        super(name, base);
    }

    @Override
    double pay() {
        return super.pay() / 2;
    }
}

public class Main {
    public static void main(String[] args) {
        Employee[] staff = {
            new Employee("Rafi", 40000),
            new Manager("Mim", 60000, 15000),
            new Intern("Anik", 30000)
        };

        double total = 0;
        for (Employee e : staff) {
            System.out.println(e);
            total += e.pay();
        }
        System.out.println("Total payroll: " + total);
    }
}

/// ### Task 3: Multilevel inheritance ###
/// `Person` has a name. `Student` extends Person and adds an ID. `GraduateStudent` extends Student and adds a thesis title. Each
/// constructor should pass its part up with `super`. Print a graduate student's details using a `describe()` method that each
/// level extends by calling `super.describe()`.

class Person {
    String name;

    Person(String name) {
        this.name = name;
    }

    String describe() {
        return "Name: " + name;
    }
}

class Student extends Person {
    int id;

    Student(String name, int id) {
        super(name);
        this.id = id;
    }

    @Override
    String describe() {
        return super.describe() + ", ID: " + id;
    }
}

class GraduateStudent extends Student {
    String thesis;

    GraduateStudent(String name, int id, String thesis) {
        super(name, id);
        this.thesis = thesis;
    }

    @Override
    String describe() {
        return super.describe() + ", Thesis: " + thesis;
    }
}

public class Main {
    public static void main(String[] args) {
        Person p = new GraduateStudent("Mim", 202, "Neural networks for crop disease");
        System.out.println(p.describe());
    }
}

/// ### Task 4: The order of constructors ###
/// Predict the output of this program before running it. Which constructor prints first, and why?

class A {
    A() {
        System.out.println("A constructor");
    }
}

class B extends A {
    B() {
        System.out.println("B constructor");
    }
}

class C extends B {
    C() {
        System.out.println("C constructor");
    }
}

public class Main {
    public static void main(String[] args) {
        new C();
    }
}

/// Parents are built first: A, then B, then C. Each constructor starts with an invisible call to its parent's constructor.

/// ### Task 5: Overriding mistakes ###
/// Each of these has an error with overriding. Find it, then see what the compiler says.

class Base {
    public void greet() {
        System.out.println("Hello");
    }

    final void locked() { }
}

class Child extends Base {
    void greet() {                       // mistake 1: weaker access
        System.out.println("Hi");
    }

    void locked() { }                    // mistake 2: overriding a final method
}

public class Main {
    public static void main(String[] args) {
        new Child().greet();
    }
}
/// @error

/// Mistake 1: a child cannot reduce visibility (`public` became package-private). Mistake 2: a `final` method cannot be
/// overridden. Fix by writing `public void greet()` and removing `locked()` from Child.

/// ### Task 6: Shapes and polymorphism ###
/// `Shape` has a method `area()`. Circle, Rectangle and Triangle override it. Write a method `printAll(Shape[] shapes)` that
/// prints each area, and sums them.

class Shape {
    double area() {
        return 0;
    }
}

class Circle extends Shape {
    double r;

    Circle(double r) {
        this.r = r;
    }

    @Override
    double area() {
        return Math.PI * r * r;
    }
}

class Rectangle extends Shape {
    double w, h;

    Rectangle(double w, double h) {
        this.w = w;
        this.h = h;
    }

    @Override
    double area() {
        return w * h;
    }
}

class Triangle extends Shape {
    double b, h;

    Triangle(double b, double h) {
        this.b = b;
        this.h = h;
    }

    @Override
    double area() {
        return 0.5 * b * h;
    }
}

public class Main {
    static double printAll(Shape[] shapes) {
        double total = 0;
        for (Shape s : shapes) {
            System.out.printf("%s: %.2f%n", s.getClass().getSimpleName(), s.area());
            total += s.area();
        }
        return total;
    }

    public static void main(String[] args) {
        Shape[] shapes = { new Circle(1.5), new Rectangle(3, 4), new Triangle(6, 2) };
        System.out.printf("Total area: %.2f%n", printAll(shapes));
    }
}

/// ### Check yourself ###
///   - What does `super(name)` do, and why must it be the first line of a constructor?
///   - What is the difference between overloading and overriding?
///   - Which version of `speak()` runs when an `Animal` variable holds a `Dog`?

/// ### Try next ###
///   1. Add a `Bird` that overrides `speak()` and has an extra method `fly()`. Call `fly()` using `instanceof`.
///   2. Add a `Teacher` to the Person family.
///   3. Override `toString()` in each Shape to describe it.
///   4. Make a `Vehicle` family: Car, Bike, Truck, each with its own `wheels()` method.
