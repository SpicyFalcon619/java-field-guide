/*
 * Practice: Exception handling problem set
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - Catching a built-in exception such as `NullPointerException` with try and catch
///   - Writing your own exception class and throwing it with `throw`
///   - Declaring that a method can fail with `throws`
///   - Checking user input and turning bad values into clear error messages
///   - Using `finally` for code that must always run
/// ============================================================================

/// Six problems. In each one a program has to cope with something going wrong, and tell the user what happened instead of
/// crashing. The programs that read input are shown with different typed values, so you can see both the good and the bad
/// path.

/// ### Problem 1: NullPointerException ###
/// Create a class `Person` with a name and an age, and a method `printAge` that prints the age. In `main`, create a `Person`
/// variable without creating the object, call `printAge` on it, and handle the resulting `NullPointerException` with a proper
/// error message.

class Person {
    String name;
    int age;

    void printAge() {
        System.out.println("Age: " + age);
    }
}

public class Main {
    public static void main(String[] args) {
        Person person = null;                  // the variable exists, but no object does

        try {
            person.printAge();                 // calling a method on null
        } catch (NullPointerException e) {
            System.out.println("Error: the person was never created, so there is no age to print.");
        }

        System.out.println("The program carries on normally");
    }
}

/// A variable that holds `null` points at nothing. Calling a method through it throws `NullPointerException`. The `catch`
/// block keeps the program alive, but the better fix is to create the object (`new Person()`) or to check `person != null`
/// first.

/// ### Problem 2: InvalidAgeException ###
/// Design a custom exception `InvalidAgeException`. Write a method `validateAge` that takes an age and throws it when the age
/// is less than 0 or more than 120. In `main`, read the age from the user, call `validateAge` and print a suitable message when
/// the custom exception is caught.

import java.util.InputMismatchException;
import java.util.Scanner;

class InvalidAgeException extends Exception {
    InvalidAgeException(String message) {
        super(message);
    }
}

public class Main {
    static void validateAge(int age) throws InvalidAgeException {
        if (age < 0 || age > 120) {
            throw new InvalidAgeException("Invalid age " + age + ": it must be between 0 and 120");
        }
        System.out.println("Age " + age + " is valid");
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter your age: ");

        try {
            int age = scan.nextInt();
            validateAge(age);
        } catch (InvalidAgeException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Error: that was not a whole number");
        }
    }
}
/// @input(A valid age): 25
/// @input(Too old): 150
/// @input(Negative): -4
/// @input(Not a number): abc

/// `throw` creates and fires the exception, `throws` in the method header warns every caller that it may happen. Because
/// `InvalidAgeException` extends `Exception`, it is a CHECKED exception: the compiler forces `main` to catch it.

/// ### Problem 3: Insufficient funds ###
/// Create a class `BankAccount` with an account number and a balance. Its `withdraw` method takes the amount and throws a
/// custom `InsufficientFundsException` when the amount is more than the balance. In `main`, create an account with an opening
/// balance, ask the user for an amount to withdraw and handle the custom exception with a proper message.

import java.util.Scanner;

class InsufficientFundsException extends Exception {
    InsufficientFundsException(String message) {
        super(message);
    }
}

class BankAccount {
    String accountNumber;
    double balance;

    BankAccount(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    void withdraw(double amount) throws InsufficientFundsException {
        if (amount > balance) {
            throw new InsufficientFundsException(
                "Cannot withdraw " + amount + ": the balance is only " + balance + " (short by " + (amount - balance) + ")");
        }
        balance -= amount;
        System.out.println("Withdrew " + amount + ", new balance " + balance);
    }
}

public class Main {
    public static void main(String[] args) {
        BankAccount account = new BankAccount("AC-5001", 5000);
        Scanner scan = new Scanner(System.in);

        System.out.print("Balance is " + account.balance + ". Amount to withdraw: ");
        double amount = scan.nextDouble();

        try {
            account.withdraw(amount);
        } catch (InsufficientFundsException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
/// @input(Enough money): 1800
/// @input(Too much): 9000

/// ### Problem 4: Email validation ###
/// Write a method `validateEmail` that checks an email address. A valid address has exactly one `@`, at least one `.` in the
/// part after the `@`, and no spaces. If it breaks a rule, throw a custom `InvalidEmailException`. In `main`, read an address
/// from the user and print a proper message when the exception is caught.

import java.util.Scanner;

class InvalidEmailException extends Exception {
    InvalidEmailException(String message) {
        super(message);
    }
}

public class Main {
    static void validateEmail(String email) throws InvalidEmailException {
        if (email.contains(" ")) {
            throw new InvalidEmailException("an email address cannot contain spaces");
        }

        int at = email.indexOf('@');
        if (at == -1 || at != email.lastIndexOf('@')) {
            throw new InvalidEmailException("it must contain exactly one '@'");
        }

        String username = email.substring(0, at);
        String domain = email.substring(at + 1);
        if (username.isEmpty() || domain.isEmpty()) {
            throw new InvalidEmailException("there must be text on both sides of the '@'");
        }
        if (!domain.contains(".")) {
            throw new InvalidEmailException("the part after '@' must contain a '.'");
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter your email address: ");
        String email = scan.nextLine();

        try {
            validateEmail(email);
            System.out.println(email + " is a valid email address");
        } catch (InvalidEmailException e) {
            System.out.println("Invalid email: " + e.getMessage());
        }
    }
}
/// @input(Valid): mim@uiu.ac.bd
/// @input(No dot in the domain): rafi@gmailcom
/// @input(Two @ signs): a@@b.com
/// @input(A space): mim khan@gmail.com

/// The checks run in order and the first broken rule throws, so the message always names the problem. `nextLine()` is used
/// instead of `next()` so that an address with a space in it is read whole and can be rejected.

/// ### Problem 5: Buying a book ###
/// A `Book` has a title, an author and `numberOfAvailableCopies`. Each time someone buys the book, the available copies go
/// down by 1. When no copies are left, `buyBook()` must throw a custom exception `BookOutOfStock` with the message
/// "[BookOutOfStockException] The value of numberOfAvailableCopies is 0." Complete `buyBook()`.
///
/// NOTE: The exception class is named `BookOutOfStock` but its message says "BookOutOfStockException". That is how the
/// problem is written, so the solution follows it exactly.

class BookOutOfStock extends Exception {
    BookOutOfStock(String message) {
        super(message);
    }
}

class Book {
    String title;
    String author;
    int numberOfAvailableCopies;

    public Book(String title, String author, int numberOfAvailableCopies) {
        this.title = title;
        this.author = author;
        this.numberOfAvailableCopies = numberOfAvailableCopies;
    }

    void buyBook() throws BookOutOfStock {
        if (numberOfAvailableCopies == 0) {
            throw new BookOutOfStock("[BookOutOfStockException] The value of numberOfAvailableCopies is 0.");
        }
        numberOfAvailableCopies--;
        System.out.println("Bought \"" + title + "\", copies left: " + numberOfAvailableCopies);
    }
}

public class Main {
    public static void main(String[] args) {
        Book book = new Book("Effective Java", "Joshua Bloch", 2);

        for (int i = 1; i <= 3; i++) {
            try {
                book.buyBook();
            } catch (BookOutOfStock e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

/// The check comes BEFORE the subtraction, so the count never goes below zero. The third purchase fails and the loop goes on,
/// because the `try` and `catch` are inside it.

/// ### Problem 6: Dividing by zero ###
/// Consider a class `Math` with a method `divide(int a, int b)` that returns `a / b`, used from a `main` that divides 4 by 0
/// and then prints "Complete".
///
/// a) Write the code in `main` so that no exception escapes and "Complete" is always printed.
///
/// b) Write a user defined exception `DivbyZero`, a subtype of `ArithmeticException`. `divide()` must check whether the second
/// parameter is 0 and, if so, throw `DivbyZero` with the message "The denominator can not be zero!". Use `throw` and `throws`
/// properly.
///
/// NOTE: A class called `Math` hides the built-in `java.lang.Math` in this program. It is fine for an exercise, but in real
/// code pick a different name.
///
/// --- Part a: always print Complete -----

class Math {
    public int divide(int a, int b) {
        return a / b;
    }
}

public class Main {
    public static void main(String[] args) {
        Math m = new Math();
        int n = 4;
        int d = 0;

        try {
            System.out.println(m.divide(n, d));
        } catch (ArithmeticException e) {
            System.out.println("Cannot divide: " + e.getMessage());
        } finally {
            System.out.println("Complete");        // runs whether or not an exception happened
        }
    }
}

/// `finally` runs after the `try` (and the `catch`, if there was one) no matter what. That is why "Complete" is guaranteed.
///
/// --- Part b: your own DivbyZero exception -----

class DivbyZero extends ArithmeticException {
    DivbyZero(String message) {
        super(message);
    }
}

class Math {
    public int divide(int a, int b) throws DivbyZero {
        if (b == 0) {
            throw new DivbyZero("The denominator can not be zero!");
        }
        return a / b;
    }
}

public class Main {
    public static void main(String[] args) {
        Math m = new Math();

        try {
            System.out.println(m.divide(8, 2));
            System.out.println(m.divide(4, 0));
        } catch (DivbyZero e) {
            System.out.println("Caught: " + e.getMessage());
        } finally {
            System.out.println("Complete");
        }
    }
}

/// `DivbyZero` extends `ArithmeticException`, which is an UNCHECKED exception, so `throws DivbyZero` in the header is allowed
/// but not required. Writing it anyway tells readers what the method can do. The first call works, the second throws, and
/// "Complete" still prints.
