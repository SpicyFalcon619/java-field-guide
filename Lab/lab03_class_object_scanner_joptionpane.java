/*
 * Lab: Lab 3: Class and object, Scanner and JOptionPane
 */

/// ============================================================================
/// WHAT YOU WILL PRACTISE
///   - Designing classes with fields, constructors and methods
///   - Menu-driven programs that read from the keyboard with Scanner
///   - Static members and encapsulation
///   - Dialog boxes with JOptionPane
/// ============================================================================


/// ### Task 1: A Rectangle class ###
/// Write a class `Rectangle` with a length and width, a constructor, and methods `area()` and `perimeter()`. Read the
/// dimensions of two rectangles, and say which one has the larger area.

import java.util.Scanner;

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
        Scanner scan = new Scanner(System.in);

        System.out.print("Rectangle 1 length and width: ");
        Rectangle r1 = new Rectangle(scan.nextDouble(), scan.nextDouble());
        System.out.print("Rectangle 2 length and width: ");
        Rectangle r2 = new Rectangle(scan.nextDouble(), scan.nextDouble());

        System.out.println("Areas: " + r1.area() + " and " + r2.area());
        System.out.println("Perimeters: " + r1.perimeter() + " and " + r2.perimeter());
        System.out.println(r1.area() >= r2.area() ? "Rectangle 1 is larger" : "Rectangle 2 is larger");
        scan.close();
    }
}
/// @input 5 | 4 | 3 | 9

/// ### Task 2: A bank account with a menu ###
/// Build a `BankAccount` with a private balance, `deposit`, `withdraw` (refuse when there is not enough money) and
/// `getBalance`. In `main`, show a menu in a loop: 1 Deposit, 2 Withdraw, 3 Balance, 0 Exit.

import java.util.Scanner;

class BankAccount {
    private double balance;
    private final String owner;

    BankAccount(String owner, double opening) {
        this.owner = owner;
        this.balance = opening;
    }

    void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited " + amount);
        } else {
            System.out.println("Amount must be positive");
        }
    }

    void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient funds");
        } else if (amount <= 0) {
            System.out.println("Amount must be positive");
        } else {
            balance -= amount;
            System.out.println("Withdrew " + amount);
        }
    }

    double getBalance() {
        return balance;
    }

    String getOwner() {
        return owner;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        BankAccount account = new BankAccount("Rafi", 500);

        int choice;
        do {
            System.out.println("1 Deposit  2 Withdraw  3 Balance  0 Exit");
            System.out.print("Choice: ");
            choice = scan.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Amount: ");
                    account.deposit(scan.nextDouble());
                    break;
                case 2:
                    System.out.print("Amount: ");
                    account.withdraw(scan.nextDouble());
                    break;
                case 3:
                    System.out.println(account.getOwner() + " has " + account.getBalance());
                    break;
                case 0:
                    System.out.println("Goodbye");
                    break;
                default:
                    System.out.println("Unknown choice");
            }
        } while (choice != 0);
        scan.close();
    }
}
/// @input 1 | 250 | 2 | 1000 | 2 | 100 | 3 | 0

/// ### Task 3: Count the objects: a static member ###
/// Give a class `Ticket` an automatic serial number from a static counter. The first ticket is 1, the second 2, and so on.

class Ticket {
    private static int counter = 0;
    private final int number;
    private final String passenger;

    Ticket(String passenger) {
        this.passenger = passenger;
        this.number = ++counter;
    }

    static int issued() {
        return counter;
    }

    @Override
    public String toString() {
        return "Ticket #" + number + " for " + passenger;
    }
}

public class Main {
    public static void main(String[] args) {
        Ticket a = new Ticket("Rafi");
        Ticket b = new Ticket("Mim");
        Ticket c = new Ticket("Anik");

        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println("Issued so far: " + Ticket.issued());
    }
}

/// ### Task 4: A student record with validation ###
/// Make a `Student` whose marks cannot be set outside 0 to 100. Use a private field and a setter. Report the grade with a method.

class Student {
    private String name;
    private int marks;

    Student(String name) {
        this.name = name;
    }

    void setMarks(int marks) {
        if (marks >= 0 && marks <= 100) {
            this.marks = marks;
        } else {
            System.out.println("Rejected marks " + marks + " for " + name);
        }
    }

    String grade() {
        return marks >= 80 ? "A" : marks >= 60 ? "B" : marks >= 40 ? "C" : "F";
    }

    String getName() {
        return name;
    }
}

public class Main {
    public static void main(String[] args) {
        Student s = new Student("Mim");
        s.setMarks(120);
        s.setMarks(73);
        System.out.println(s.getName() + " earned grade " + s.grade());
    }
}

/// ### Task 5: Dialog boxes with JOptionPane ###
/// Do Task 1 of Lab 1 again (add two numbers), but with dialog boxes instead of the console: ask for the numbers in input
/// dialogs and show the result in a message dialog. `showInputDialog` returns text, so convert it.
///
/// NOTE: This program opens windows, so it cannot run inside the page. Copy it into your IDE.

import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        String first = JOptionPane.showInputDialog("Enter the first number:");
        String second = JOptionPane.showInputDialog("Enter the second number:");

        try {
            double a = Double.parseDouble(first);
            double b = Double.parseDouble(second);
            JOptionPane.showMessageDialog(null, a + " + " + b + " = " + (a + b), "Result", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException | NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Please enter two valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

/// ### Task 6: A yes/no question and a choice list ###
/// Ask "Do you want to continue?" with `showConfirmDialog`, then show a list to choose a favourite language with
/// `showOptionDialog` or a simple combo in `showInputDialog`.

import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        int answer = JOptionPane.showConfirmDialog(null, "Do you want to continue?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (answer != JOptionPane.YES_OPTION) {
            return;
        }

        String[] languages = {"Java", "Python", "C", "JavaScript"};
        Object choice = JOptionPane.showInputDialog(null, "Pick your favourite:", "Language",
                JOptionPane.QUESTION_MESSAGE, null, languages, languages[0]);
        JOptionPane.showMessageDialog(null, "You picked: " + choice);
    }
}

/// ### Check yourself ###
///   - Why is `balance` private, and how does the rest of the program change it?
///   - Why is `issued()` declared `static`?
///   - What does `showInputDialog` return if you press Cancel?

/// ### Try next ###
///   1. Extend the bank menu with a transfer between two accounts.
///   2. Give `Ticket` a static method that resets the counter.
///   3. Write a tiny calculator with dialog boxes for the operator and the two numbers.
///   4. Add `equals` to Rectangle so two rectangles with the same size are equal.
