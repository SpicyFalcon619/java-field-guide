/*
 * Practice: Classes and objects problem set
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - Turning a written description into a class with fields and methods
///   - Creating several objects of one class and using each one on its own
///   - Writing constructors that set up an object's state
///   - Methods that change an object (deposit, borrow, raise) and methods that calculate (area, total)
/// ============================================================================


/// ### Problem 1: Bank account ###
/// Create a `BankAccount` class with the account holder's name (String), the account id (String) and the balance (double).
/// Add methods to deposit and withdraw money. Test the class by creating some accounts and making some transactions.
///
/// TRY IT: Write the class yourself first. Think about what should happen if someone withdraws more than the balance.

class BankAccount {
    String holderName;
    String accountId;
    double balance;

    BankAccount(String holderName, String accountId, double balance) {
        this.holderName = holderName;
        this.accountId = accountId;
        this.balance = balance;
    }

    void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit failed: the amount must be positive");
            return;
        }
        balance += amount;
        System.out.println(holderName + " deposited " + amount + ", balance is now " + balance);
    }

    void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal failed: the amount must be positive");
            return;
        }
        if (amount > balance) {
            System.out.println("Withdrawal failed: " + holderName + " only has " + balance);
            return;
        }
        balance -= amount;
        System.out.println(holderName + " withdrew " + amount + ", balance is now " + balance);
    }
}

public class Main {
    public static void main(String[] args) {
        BankAccount rafi = new BankAccount("Rafi", "AC-1001", 5000);
        BankAccount mim = new BankAccount("Mim", "AC-1002", 12000.50);

        rafi.deposit(1500);
        rafi.withdraw(2000);
        rafi.withdraw(10000);      // more than the balance
        mim.withdraw(500.25);
        mim.deposit(-50);          // not a valid amount
    }
}

/// Each object keeps its own balance: Mim's withdrawal never touches Rafi's account. The checks inside `deposit` and
/// `withdraw` stop the balance from ever becoming wrong.

/// ### Problem 2: Student ###
/// Design a `Student` class with a name (String), a roll number (String) and the marks in different subjects (an array of
/// double). Add methods to calculate the total marks and the average. Create some students and compute their totals and
/// averages.

class Student {
    String name;
    String rollNumber;
    double[] marks;

    Student(String name, String rollNumber, double[] marks) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.marks = marks;
    }

    double totalMarks() {
        double total = 0;
        for (double mark : marks) {
            total += mark;
        }
        return total;
    }

    double average() {
        if (marks.length == 0) {
            return 0;                  // avoid dividing by zero
        }
        return totalMarks() / marks.length;
    }
}

public class Main {
    public static void main(String[] args) {
        Student[] students = {
            new Student("Rafi", "R-01", new double[] {78.5, 82, 91, 67.5}),
            new Student("Mim", "R-02", new double[] {88, 94.5, 79, 90})
        };

        for (Student s : students) {
            System.out.printf("%s (%s): total %.1f, average %.2f%n", s.name, s.rollNumber, s.totalMarks(), s.average());
        }
    }
}

/// `average()` calls `totalMarks()`: one method of an object can use another, so the sum is written only once.

/// ### Problem 3: Rectangle ###
/// Define a `Rectangle` class with a length and a width. Add methods to calculate the area and the perimeter. Create some
/// rectangles and compute their area and perimeter.

class Rectangle {
    double length;
    double width;

    Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    double area() {
        return length * width;
    }

    double perimeter() {
        return 2 * (length + width);
    }
}

public class Main {
    public static void main(String[] args) {
        Rectangle desk = new Rectangle(1.5, 0.75);
        Rectangle field = new Rectangle(100, 60);

        System.out.println("Desk: area " + desk.area() + ", perimeter " + desk.perimeter());
        System.out.println("Field: area " + field.area() + ", perimeter " + field.perimeter());
    }
}

/// ### Problem 4: Car ###
/// Create a `Car` class with a colour, a model and a name. Add methods to start the engine, stop the engine and display the
/// car's details. Create some cars and test the methods.

class Car {
    String color;
    String model;
    String name;
    boolean engineOn = false;

    Car(String color, String model, String name) {
        this.color = color;
        this.model = model;
        this.name = name;
    }

    void startEngine() {
        if (engineOn) {
            System.out.println(name + ": the engine is already running");
        } else {
            engineOn = true;
            System.out.println(name + ": engine started");
        }
    }

    void stopEngine() {
        if (engineOn) {
            engineOn = false;
            System.out.println(name + ": engine stopped");
        } else {
            System.out.println(name + ": the engine is already off");
        }
    }

    void displayDetails() {
        System.out.println(name + " is a " + color + " " + model + ", engine " + (engineOn ? "on" : "off"));
    }
}

public class Main {
    public static void main(String[] args) {
        Car first = new Car("red", "Toyota Axio", "Bolt");
        Car second = new Car("white", "Honda Civic", "Pearl");

        first.displayDetails();
        first.startEngine();
        first.startEngine();       // the second call is refused
        first.displayDetails();
        first.stopEngine();

        second.displayDetails();
    }
}

/// The boolean `engineOn` is the car's state. Methods read it before acting, so you cannot start a running engine twice.

/// ### Problem 5: Employee ###
/// Design an `Employee` class with an employee ID, a name and a salary. Add a method that gives a salary raise. Create some
/// employees and apply raises.

class Employee {
    int id;
    String name;
    double salary;

    Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    void giveRaise(double percent) {
        double raise = salary * percent / 100;
        salary += raise;
        System.out.println(name + " got a " + percent + "% raise of " + raise + ", new salary " + salary);
    }
}

public class Main {
    public static void main(String[] args) {
        Employee anik = new Employee(101, "Anik", 40000);
        Employee dee = new Employee(102, "Dee", 55000);

        anik.giveRaise(10);
        dee.giveRaise(5);
        anik.giveRaise(2.5);       // raises add up, because the object remembers its salary
    }
}

/// ### Problem 6: Library book ###
/// Build a `LibraryBook` class with a title, an author and an availability status. Add methods to borrow and return a book.
/// Simulate borrowing and returning with some objects.
///
/// NOTE: `return` is a Java keyword, so a method cannot be called `return`. `returnBook` (or `giveBack`) is the usual name.

class LibraryBook {
    String title;
    String author;
    boolean available = true;

    LibraryBook(String title, String author) {
        this.title = title;
        this.author = author;
    }

    void borrow() {
        if (available) {
            available = false;
            System.out.println("You borrowed \"" + title + "\" by " + author);
        } else {
            System.out.println("Sorry, \"" + title + "\" is already borrowed");
        }
    }

    void returnBook() {
        if (available) {
            System.out.println("\"" + title + "\" was not borrowed, so there is nothing to return");
        } else {
            available = true;
            System.out.println("Thank you for returning \"" + title + "\"");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        LibraryBook book = new LibraryBook("Clean Code", "Robert C. Martin");

        book.borrow();
        book.borrow();         // someone else tries to borrow it
        book.returnBook();
        book.returnBook();     // returned twice by mistake
        book.borrow();
    }
}

/// ### Problem 7: Circle ###
/// Define a `Circle` class with a radius. Add methods to calculate the circumference and the area. Create some circles and
/// compute their circumference and area.

class Circle {
    double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    double circumference() {
        return 2 * Math.PI * radius;
    }

    double area() {
        return Math.PI * radius * radius;
    }
}

public class Main {
    public static void main(String[] args) {
        Circle small = new Circle(3);
        Circle big = new Circle(7.5);

        System.out.printf("r = %.1f: circumference %.2f, area %.2f%n", small.radius, small.circumference(), small.area());
        System.out.printf("r = %.1f: circumference %.2f, area %.2f%n", big.radius, big.circumference(), big.area());
    }
}

/// `Math.PI` is the built-in constant for pi, so there is no need to type 3.14159 yourself. `%.2f` in `printf` rounds to two
/// decimal places.

/// ### Problem 8: Person ###
/// Create a `Person` class with a name, an age and an address. Add methods to update the address and to display the
/// person's details. Create some people and test the methods.

class Person {
    String name;
    int age;
    String address;

    Person(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    void updateAddress(String newAddress) {
        System.out.println(name + " moved from " + address + " to " + newAddress);
        address = newAddress;
    }

    void display() {
        System.out.println(name + ", " + age + " years old, lives in " + address);
    }
}

public class Main {
    public static void main(String[] args) {
        Person rizvy = new Person("Rizvy", 34, "Borishal");
        Person mim = new Person("Mim", 21, "Dhaka");

        rizvy.display();
        mim.display();

        rizvy.updateAddress("Chittagong");
        rizvy.display();
    }
}
