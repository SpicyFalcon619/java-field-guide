/*
 * Exceptions and Files: Exception Handling
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What is an Exception?
///   - Real-Life Example
///   - Example: Program Without Exception Handling
///   - How to Handle Exceptions?
///   - Exception Hierarchy in Java (Simplified)
///   - Exception Handling Flow Summary
/// ============================================================================


/// ### Topic 1: What is an Exception? ###
/// NOTE: An exception is an unwanted or unexpected event that disrupts the normal flow of the
/// program.
///    - It happens during runtime (not compile-time).
///    - If not handled properly, it will crash your program.


/// ### Topic 2: Real-Life Example ###
/// Imagine:
///    - You are trying to divide two numbers.
///    - What if the divisor is 0?
///    - Dividing by zero is mathematically impossible -> causes an ArithmeticException.


/// ### Topic 3: Example: Program Without Exception Handling ###
public class Main {
    public static void main(String[] args) {
        int a = 10;
        int b = 0;
        int result = a / b; // This will cause ArithmeticException
        System.out.println(result);
    }
}

/// Compile = Successful
/// Runtime = Crashes with error: `Exception in thread "main" java.lang.ArithmeticException: / by
/// zero`


/// ### Topic 4: How to Handle Exceptions? ###
/// We use:
/// `try`, `catch`, `finally`, `throw`, `throws`
///
/// --- `try`-`catch` Block -----
///
/// --- Syntax -----
///
///     try {
///         // code that might throw an exception
///     } catch (ExceptionType e) {
///         // code to handle the exception
///     }
///
/// --- Example -----

public class Main {
    public static void main(String[] args) {
        try {
            int a = 10;
            int b = 0;
            int result = a / b;
            System.out.println(result);
        } catch (ArithmeticException e) {
            System.out.println("Cannot divide by zero!\nError: " + e);
        }
        System.out.println("Program continues...");
    }
}

/// --- Key Points -----
///    - `try` block: Code that might throw an exception.
///    - `catch` block: Code that handles the exception.
///    - After handling, the program does not crash — it continues normally.
///
/// --- Multiple `catch` Blocks -----
///
/// NOTE: We use multiple `catch` Blocks to handle different types of exceptions in different ways.
///
/// Sometimes, different exceptions require different logic or messages — and this keeps your
/// program clean and organized.
///
/// --- Example -----

public class Main {
    public static void main(String[] args) {
        try {
            int a = 10 / 0; // This causes ArithmeticException

            int[] arr = new int[3];
            System.out.println(arr[5]); // This causes ArrayIndexOutOfBoundsException

        } catch (ArithmeticException e) {
            System.out.println("Math error: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Array error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }

        System.out.println("Program continues after exception handling.");
    }
}

/// --- Using `.getMessage()` -----
///
/// Instead of printing the whole exception object (`System.out.println(e)`), we can print just the
/// error message using `.getMessage()`.
///
/// --- Example -----

public class Main {
    public static void main(String[] args) {
        try {
            int a = 10;
            int b = 0;
            int result = a / b;
            System.out.println(result);
        } catch (ArithmeticException e) {
            System.out.println("Cannot divide by zero!\nError: " + e.getMessage());
        }
        System.out.println("Program continues...");
    }
}

/// --- `finally` Block -----
///
/// NOTE: The finally block always runs, whether there is an exception or not.
///
/// Useful to close files, release memory, or clean up.
///
/// --- Example -----

public class Main {
    public static void main(String[] args) {
        try {
            int arr[] = {1, 2, 3};
            System.out.println(arr[5]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Array index out of range!");
        } finally {
            System.out.println("Finally block is always executed.");
        }
    }
}

/// --- `throw` Keyword -----
///
/// NOTE: `throw` is used to manually throw an exception.
///
/// --- Example -----

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

/// Output: `Exception in thread "main" java.lang.ArithmeticException: Access denied - You must be
/// 18+`
///
/// --- `throws` Keyword -----
///
/// NOTE: `throws` is used to declare an exception in the method signature.
///
/// It tells Java: "This method might throw an exception."
///
/// --- Example -----

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


/// ### Topic 5: Exception Hierarchy in Java (Simplified) ###
///     Object
///       ↳ Throwable
///            ↳ Error       (Serious problems, don't handle)
///            ↳ Exception   (Can and should handle)
///                ↳ RuntimeException (Unchecked)
///                ↳ Other Exceptions (Checked)


/// ### Topic 6: Exception Handling Flow Summary ###
///     Term      Meaning
///     try       Code block that might cause exception
///     catch     Code block that handles the exception
///     finally   Code block that always runs (cleanup)
///     throw     To manually throw an exception
///     throws    Declares that a method might throw an exception


/// ### Topic 7: Types of Exceptions ###
///     Term                 Meaning                                                                     Checked
///     Runtime Exceptions   ArithmeticException, NullPointerException, ArrayIndexOutOfBoundsException   No (Unchecked)
///     Checked Exceptions   IOException, SQLException, FileNotFoundException                            Yes


/// ### Topic 8: User-Defined Exception ###
/// --- Creating a User-Defined Exception -----

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

/// --- Using the Custom Exception in Code -----

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

/// --- Notes & Best Practices -----
///    - Custom exceptions should be used when:
///    - We want more meaningful error messages
///    - We want to separate business logic failures from standard Java errors
///    - We can create unchecked exceptions too by extending `RuntimeException`.
///
/// --- Real-World Use Case (Bank App) -----

class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

class BankAccount {
    double balance = 1000;

    void withdraw(double amount) throws InsufficientFundsException {
        if (amount > balance) {
            throw new InsufficientFundsException("Not enough balance!");
        }
        balance -= amount;
    }
}
