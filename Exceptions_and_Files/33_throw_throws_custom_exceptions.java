/*
 * Exceptions and Files: throw, throws and custom exceptions
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - How to raise an exception yourself with `throw`
///   - How to pass the responsibility on with `throws`
///   - How to write your own exception class
///   - Good habits: when a custom exception is the right tool
/// ============================================================================


/// ### Topic 1: The throw keyword ###
/// So far Java threw the exceptions, when something went wrong by itself. Sometimes YOU know a situation is invalid
/// even though Java does not. A negative age, a withdrawal bigger than the balance. In that case you raise the
/// exception on purpose with `throw`.
///
/// NOTE: `throw` is used to manually throw an exception. You throw an object, so you create it with `new`.

public class Main {
    public static void main(String[] args) {
        int age = 15;
        if (age < 18) {
            throw new ArithmeticException("Access denied - You must be 18+");
        } else {
            System.out.println("Access granted");
        }
    }
}
/// @error

/// Output: `Exception in thread "main" java.lang.ArithmeticException: Access denied - You must be 18+`. The text you
/// gave to the constructor is the message. (An `IllegalArgumentException` would describe this case better than an
/// `ArithmeticException`, which is meant for maths. Choosing a well-named exception is part of good style.)
///
/// WHY: Throwing is how a method says "I cannot do what you asked, and I refuse to continue pretending". The caller
/// can then decide what to do, rather than the program carrying on with bad data.

/// ### Topic 2: Catching what you throw ###
/// Throwing and catching go together. The thrower detects the problem; the catcher decides how to react.

public class Main {
    static void setAge(int age) {
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException("Age must be between 0 and 150, but was " + age);
        }
        System.out.println("Age set to " + age);
    }

    public static void main(String[] args) {
        try {
            setAge(25);
            setAge(-5);
            setAge(40);              // never reached
        } catch (IllegalArgumentException e) {
            System.out.println("Problem: " + e.getMessage());
        }
        System.out.println("Carrying on.");
    }
}

/// `setAge(25)` worked. `setAge(-5)` threw, the third call was skipped, and the catch handled it. This is the better
/// version of the setter from the Encapsulation lesson: instead of quietly printing "Invalid age!", it signals the
/// failure properly so the caller cannot ignore it.

/// ### Topic 3: The throws keyword ###
/// NOTE: `throws` is used to declare an exception in the method signature. It tells Java, and everyone reading: "this
/// method might throw this exception, so whoever calls it must be ready."
///
/// It is required for CHECKED exceptions (see the previous lesson). The method has two choices for a checked
/// exception: catch it inside, or declare it with `throws` and pass the problem up to its caller.
///
/// ANALOGY: A junior employee who meets a problem they cannot solve passes it to their manager ("throws"). The manager
/// either solves it ("catch") or passes it on up again.

import java.io.*;

public class Main {
    static void checkFile() throws IOException {
        FileReader file = new FileReader("somefile.txt");
        file.read();
        file.close();
    }

    public static void main(String[] args) {
        try {
            checkFile();
        } catch (IOException e) {
            System.out.println("File not found or unable to read the file.");
        }
    }
}

/// `checkFile` does not handle the problem. It declares `throws IOException` and lets `main` deal with it. `main`
/// catches it and prints a friendly message. Remove the `try` and `catch` and the code will not compile, because the
/// problem would be left unhandled.
///
/// --- throw versus throws -----
///     throw     an action, inside a method: "raise this exception now"
///     throws    a declaration, in the method heading: "this method may raise this exception"

/// ### Topic 4: Writing your own exception ###
/// Java's built-in exceptions cover technical problems. For problems in YOUR program's own rules ("insufficient
/// funds", "username taken") you can create your own exception class. It is simply a class that extends `Exception`
/// (checked) or `RuntimeException` (unchecked).
///
/// --- Creating a user-defined exception -----

// Custom exception class (must extend Exception or RuntimeException)
class MyException extends Exception {
    public MyException(String message) {
        super(message); // Pass the message to Exception class
    }
}

public class Main {
    static void validateAge(int age) throws MyException {
        if (age < 18) {
            throw new MyException("You must be 18 or older.");
        }
        System.out.println("Access granted.");
    }

    public static void main(String[] args) {
        try {
            validateAge(16);
        } catch (MyException e) {
            System.out.println("Custom Exception: " + e.getMessage());
        }
    }
}

/// Look at how little code the exception class needs. `extends Exception` makes it an exception. The constructor
/// passes the message up with `super(message)` (the Inheritance lesson), and `getMessage()` is inherited from
/// `Exception`, so we did not need to write it.

/// ### Topic 5: Notes and best practices ###
///    - Use custom exceptions when you want more meaningful error messages.
///    - Use them to separate your business-logic failures from standard Java errors.
///    - You can create unchecked exceptions too, by extending `RuntimeException`. Then callers are not forced to
///      catch them.
///    - Name them for the problem, ending with "Exception": `InsufficientFundsException`, `InvalidMarksException`.
///
/// --- Real-world use case: a bank app -----

class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

class BankAccount {
    double balance = 1000;

    void withdraw(double amount) throws InsufficientFundsException {
        if (amount > balance) {
            throw new InsufficientFundsException("Not enough balance! You have " + balance + " but asked for " + amount);
        }
        balance -= amount;
    }
}

public class Main {
    public static void main(String[] args) {
        BankAccount acc = new BankAccount();
        try {
            acc.withdraw(300);
            System.out.println("Balance: " + acc.balance);
            acc.withdraw(900);
            System.out.println("Balance: " + acc.balance);
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage());
        }
    }
}

/// The first withdrawal succeeded. The second asked for more than the 700 that was left, so the method threw our
/// own exception, and `main` caught it with a clear message. The caller is forced to deal with the possibility,
/// because `withdraw` declares it with `throws`.

/// ### Topic 6: Unchecked custom exceptions ###
/// If you extend `RuntimeException` instead, the compiler does not force callers to catch or declare it. Choose
/// this for programming errors (the caller passed something invalid), and a checked one for problems the caller can
/// realistically recover from.

class InvalidMarksException extends RuntimeException {
    public InvalidMarksException(int marks) {
        super("Marks must be between 0 and 100, but were " + marks);
    }
}

public class Main {
    static String grade(int marks) {
        if (marks < 0 || marks > 100) throw new InvalidMarksException(marks);
        return marks >= 40 ? "Pass" : "Fail";
    }

    public static void main(String[] args) {
        System.out.println(grade(75));
        System.out.println(grade(120));
    }
}
/// @error

/// No `throws` was needed on `grade`, and `main` did not have to catch it. Because nobody did, the program stopped
/// with our message: `InvalidMarksException: Marks must be between 0 and 100, but were 120`.
///
/// REMEMBER: `throw` raises one, `throws` declares the possibility, and a custom exception is a class that extends
/// `Exception` (checked) or `RuntimeException` (unchecked).

/// ============================================================================
/// YOUR TURN
///   1. Write a method `double sqrt(double x)` that throws an `IllegalArgumentException` for a negative number. Call
///      it inside try and catch.
///   2. Create `InvalidAgeException extends Exception`, and a method `register(int age)` that throws it for ages under
///      5. Call it from `main` with a catch.
///   3. Add `withdraw` and `deposit` methods to a `BankAccount`, each throwing a custom exception for bad amounts.
///   4. Make a method that `throws IOException`, and call it from `main` without a try, to see the compile error.
///   5. When would you choose a checked custom exception, and when an unchecked one?
/// ============================================================================
