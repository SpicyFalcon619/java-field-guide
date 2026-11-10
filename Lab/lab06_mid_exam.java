/*
 * Lab: Lab 6: Mid exam practice
 */

/// ============================================================================
/// WHAT YOU WILL PRACTISE
///   - A practice mid exam covering Labs 1 to 5: basics, arrays, classes, inheritance, overloading and abstract classes
///   - Working to a time limit, and checking your answer against a worked solution
/// ============================================================================
///
/// How to use this page: set a timer for 90 minutes. Write each answer in your IDE WITHOUT looking at the solution. Then
/// compare. Where yours differs, work out whether it is wrong or just a different good way.
///
/// Marks (out of 50): Q1 8, Q2 10, Q3 12, Q4 10, Q5 10.


/// ### Question 1 (8 marks): Number analysis ###
/// Read an integer. Print whether it is even or odd, whether it is positive, negative or zero, and the sum of its digits (of its
/// absolute value).

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter an integer: ");
        int n = scan.nextInt();

        System.out.println(n % 2 == 0 ? "even" : "odd");
        System.out.println(n > 0 ? "positive" : n < 0 ? "negative" : "zero");

        int m = Math.abs(n);
        int sum = 0;
        while (m > 0) {
            sum += m % 10;
            m /= 10;
        }
        System.out.println("Digit sum: " + sum);
        scan.close();
    }
}
/// @input -4821

/// ### Question 2 (10 marks): Array operations ###
/// An array holds {3, 8, 1, 9, 4, 7}. Print the second largest value, the count of even numbers, and the array after the
/// elements at the first and last positions are swapped.

public class Main {
    public static void main(String[] args) {
        int[] a = {3, 8, 1, 9, 4, 7};

        int largest = Integer.MIN_VALUE;
        int second = Integer.MIN_VALUE;
        int evens = 0;
        for (int x : a) {
            if (x > largest) {
                second = largest;
                largest = x;
            } else if (x > second) {
                second = x;
            }
            if (x % 2 == 0) {
                evens++;
            }
        }
        System.out.println("Second largest: " + second);
        System.out.println("Even numbers: " + evens);

        int temp = a[0];
        a[0] = a[a.length - 1];
        a[a.length - 1] = temp;
        System.out.println(java.util.Arrays.toString(a));
    }
}

/// ### Question 3 (12 marks): A class ###
/// Write a class `Product` with a name, a price and a quantity. Include a constructor, a method `total()` (price times
/// quantity), and a method `applyDiscount(int percent)` that reduces the price, but ignores a percent outside 0 to 90. In
/// `main`, create three products in an array, apply a 10 percent discount to all, and print each product's total and the
/// grand total.

class Product {
    private String name;
    private double price;
    private int quantity;

    Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    double total() {
        return price * quantity;
    }

    void applyDiscount(int percent) {
        if (percent >= 0 && percent <= 90) {
            price = price * (100 - percent) / 100.0;
        }
    }

    String getName() {
        return name;
    }
}

public class Main {
    public static void main(String[] args) {
        Product[] items = {
            new Product("Pen", 10, 12),
            new Product("Notebook", 55, 4),
            new Product("Bag", 900, 1)
        };

        double grand = 0;
        for (Product p : items) {
            p.applyDiscount(10);
            System.out.printf("%s: %.2f%n", p.getName(), p.total());
            grand += p.total();
        }
        System.out.printf("Grand total: %.2f%n", grand);
    }
}

/// ### Question 4 (10 marks): Inheritance ###
/// `Vehicle` has a brand and a method `info()`. `Car` adds a number of doors, and `ElectricCar` adds a battery capacity. Each
/// `info()` should extend its parent's by calling `super.info()`. Print the info of an ElectricCar through a Vehicle variable.

class Vehicle {
    String brand;

    Vehicle(String brand) {
        this.brand = brand;
    }

    String info() {
        return "Brand: " + brand;
    }
}

class Car extends Vehicle {
    int doors;

    Car(String brand, int doors) {
        super(brand);
        this.doors = doors;
    }

    @Override
    String info() {
        return super.info() + ", Doors: " + doors;
    }
}

class ElectricCar extends Car {
    int battery;

    ElectricCar(String brand, int doors, int battery) {
        super(brand, doors);
        this.battery = battery;
    }

    @Override
    String info() {
        return super.info() + ", Battery: " + battery + " kWh";
    }
}

public class Main {
    public static void main(String[] args) {
        Vehicle v = new ElectricCar("Tesla", 4, 75);
        System.out.println(v.info());
    }
}

/// ### Question 5 (10 marks): Abstract class and overloading ###
/// Create an abstract class `Account` with an abstract `monthlyFee()` and a concrete `describe()`. Make `Savings` (fee 0) and
/// `Current` (fee 5). Overload a static method `show` so that it accepts one Account or an array of Accounts.

abstract class Account {
    String owner;

    Account(String owner) {
        this.owner = owner;
    }

    abstract double monthlyFee();

    String describe() {
        return owner + " pays " + monthlyFee() + " per month";
    }
}

class Savings extends Account {
    Savings(String owner) {
        super(owner);
    }

    double monthlyFee() {
        return 0;
    }
}

class Current extends Account {
    Current(String owner) {
        super(owner);
    }

    double monthlyFee() {
        return 5;
    }
}

public class Main {
    static void show(Account a) {
        System.out.println(a.describe());
    }

    static void show(Account[] all) {
        for (Account a : all) {
            show(a);
        }
    }

    public static void main(String[] args) {
        show(new Savings("Rafi"));
        show(new Account[] { new Savings("Mim"), new Current("Anik") });
    }
}

/// ### Marking guide ###
///   - Correct output on the sample data: about half the marks for each question.
///   - Clean structure (private fields, constructors, no copy-pasted code): a quarter.
///   - Correct use of the concept the question tests (loops, arrays, `super`, `abstract`, overloading): a quarter.
///
/// ### After the exam ###
/// List every question where you lost marks, and write the reason in one sentence ("forgot to start the max with the first
/// element", "wrote private field but no getter"). That list is your revision plan.
