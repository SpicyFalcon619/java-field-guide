/*
 * Lab: Lab 5: Method overloading and abstract classes
 */

/// ============================================================================
/// WHAT YOU WILL PRACTISE
///   - Overloading methods and constructors
///   - Abstract classes and abstract methods
///   - Interfaces next to abstract classes
///   - Choosing between them for a small design
/// ============================================================================


/// ### Task 1: An overloaded calculator ###
/// Write a class `Calc` with several `add` methods: two ints, three ints, two doubles, and an int array that sums every
/// element. Call each and say which one Java chose.

class Calc {
    int add(int a, int b) {
        return a + b;
    }

    int add(int a, int b, int c) {
        return a + b + c;
    }

    double add(double a, double b) {
        return a + b;
    }

    int add(int[] numbers) {
        int sum = 0;
        for (int n : numbers) {
            sum += n;
        }
        return sum;
    }
}

public class Main {
    public static void main(String[] args) {
        Calc c = new Calc();
        System.out.println(c.add(2, 3));
        System.out.println(c.add(2, 3, 4));
        System.out.println(c.add(2.5, 3.5));
        System.out.println(c.add(new int[] {1, 2, 3, 4}));
        System.out.println(c.add(2, 3.5));            // int and double: which version runs?
    }
}

/// The last call matched `add(double, double)`, because the int was widened to a double.

/// ### Task 2: Overloaded constructors ###
/// A `Book` can be made with a title only, with a title and an author, or with title, author and price. Use constructor
/// chaining so the real work is in one constructor.

class Book {
    String title;
    String author;
    double price;

    Book(String title) {
        this(title, "Unknown", 0);
    }

    Book(String title, String author) {
        this(title, author, 0);
    }

    Book(String title, String author, double price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }

    @Override
    public String toString() {
        return title + " by " + author + " (" + price + ")";
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println(new Book("Java"));
        System.out.println(new Book("Java", "Schildt"));
        System.out.println(new Book("Java", "Schildt", 45.5));
    }
}

/// ### Task 3: Overloaded area methods ###
/// Write static methods named `area` for a circle (one double), a rectangle (two doubles) and a triangle (three doubles: the
/// three sides, using Heron's formula). Notice that the rectangle and triangle differ by the NUMBER of arguments, and the circle
/// and the single-number square would clash. That is why the circle takes a double and a square would need another name.

public class Main {
    static double area(double radius) {
        return Math.PI * radius * radius;
    }

    static double area(double length, double width) {
        return length * width;
    }

    static double area(double a, double b, double c) {
        double s = (a + b + c) / 2;
        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
    }

    public static void main(String[] args) {
        System.out.printf("Circle: %.2f%n", area(2));
        System.out.printf("Rectangle: %.2f%n", area(3, 4));
        System.out.printf("Triangle: %.2f%n", area(3, 4, 5));
    }
}

/// ### Task 4: An abstract Shape ###
/// Make `Shape` abstract, with an abstract `area()` and a method `describe()` that prints the area. Create `Circle` and
/// `Square`. Prove that `new Shape()` does not compile.

abstract class Shape {
    String name;

    Shape(String name) {
        this.name = name;
    }

    abstract double area();

    void describe() {
        System.out.printf("%s with area %.2f%n", name, area());
    }
}

class Circle extends Shape {
    double r;

    Circle(double r) {
        super("Circle");
        this.r = r;
    }

    double area() {
        return Math.PI * r * r;
    }
}

class Square extends Shape {
    double side;

    Square(double side) {
        super("Square");
        this.side = side;
    }

    double area() {
        return side * side;
    }
}

public class Main {
    public static void main(String[] args) {
        Shape[] shapes = { new Circle(1), new Square(2) };
        for (Shape s : shapes) {
            s.describe();
        }
    }
}

/// Now the attempt that must fail:

abstract class Vehicle {
    abstract void move();
}

public class Main {
    public static void main(String[] args) {
        Vehicle v = new Vehicle();
        v.move();
    }
}
/// @error

/// ### Task 5: A payment system ###
/// `Payment` is an abstract class with an `amount`, a concrete `receipt()` method and an abstract `process()`. Make `CardPayment`
/// and `CashPayment`. The same loop should process a mixed list.

abstract class Payment {
    protected double amount;

    Payment(double amount) {
        this.amount = amount;
    }

    abstract void process();

    void receipt() {
        System.out.println("Receipt: paid " + amount);
    }
}

class CardPayment extends Payment {
    String cardNumber;

    CardPayment(double amount, String cardNumber) {
        super(amount);
        this.cardNumber = cardNumber;
    }

    void process() {
        System.out.println("Charging card ending " + cardNumber.substring(cardNumber.length() - 4));
    }
}

class CashPayment extends Payment {
    CashPayment(double amount) {
        super(amount);
    }

    void process() {
        System.out.println("Taking cash");
    }
}

public class Main {
    public static void main(String[] args) {
        Payment[] today = { new CardPayment(120.0, "4111111111111234"), new CashPayment(45.5) };
        for (Payment p : today) {
            p.process();
            p.receipt();
        }
    }
}

/// ### Task 6: Abstract class plus interface ###
/// A `Duck` is an `Animal` (abstract class with shared name and sleep code) that can also `Fly` and `Swim` (interfaces).

abstract class Animal {
    protected String name;

    Animal(String name) {
        this.name = name;
    }

    abstract void sound();

    void sleep() {
        System.out.println(name + " is sleeping");
    }
}

interface Flyable {
    void fly();
}

interface Swimmable {
    void swim();
}

class Duck extends Animal implements Flyable, Swimmable {
    Duck(String name) {
        super(name);
    }

    void sound() {
        System.out.println(name + " says Quack");
    }

    public void fly() {
        System.out.println(name + " flies");
    }

    public void swim() {
        System.out.println(name + " swims");
    }
}

public class Main {
    public static void main(String[] args) {
        Duck d = new Duck("Donald");
        d.sound();
        d.fly();
        d.swim();
        d.sleep();

        Flyable f = d;              // the same object seen as a Flyable
        f.fly();
    }
}

/// ### Check yourself ###
///   - Why can `Vehicle v = new Vehicle()` not compile when Vehicle is abstract?
///   - Can an abstract class have a constructor? When is it used?
///   - Why does `add(2, 3.5)` choose the double version?
///   - When would you pick an interface rather than an abstract class?

/// ### Try next ###
///   1. Add an overloaded `print` method set (int, String, double) and call each.
///   2. Add a `Bird` family where `Bird` is abstract with `abstract void fly()`.
///   3. Write an interface `Comparable`-like `Rankable` with `int rank()` and sort a list using it.
///   4. Make `Shape` implement an interface `Printable` with `print()`.
