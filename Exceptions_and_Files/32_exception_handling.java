/*
 * Exceptions and Files: Exception handling
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What an exception is, and what a crash looks like
///   - How to catch an exception with try and catch, so the program carries on
///   - Several catch blocks, and getMessage()
///   - The finally block, and try-with-resources
///   - The exception family tree, and checked versus unchecked exceptions
/// ============================================================================


/// ### Topic 1: What is an exception? ###
/// NOTE: An exception is an unwanted or unexpected event that disrupts the normal flow of the program.
///    - It happens at run time (not at compile time).
///    - If it is not handled properly, it will crash your program.
///
/// You already met several: `InputMismatchException` (typing a word where a number was expected),
/// `ArrayIndexOutOfBoundsException` (an index that does not exist), `NullPointerException` (using a variable that
/// points at nothing).
///
/// ANALOGY: You are cooking and the gas runs out. Without a plan, dinner stops. With a plan ("if the gas runs out,
/// switch to the electric stove") you carry on. Exception handling is that plan for your program.

/// ### Topic 2: Real-life example ###
/// Imagine you are dividing two numbers. What if the divisor is 0? Dividing by zero is mathematically impossible, so
/// Java raises an `ArithmeticException`. Without exception handling:

public class Main {
    public static void main(String[] args) {
        int a = 10;
        int b = 0;
        int result = a / b; // This will cause ArithmeticException
        System.out.println(result);
        System.out.println("Program finished normally.");
    }
}
/// @error

/// Compile = successful. Runtime = crashes with an error such as `Exception in thread "main"
/// java.lang.ArithmeticException: / by zero`. Notice that "Program finished normally." never printed. The program
/// died at the faulty line. Reading the message: the first line names the exception and why, and the lines under it
/// show where it happened, which is the "stack trace".

/// ### Topic 3: Handling with try and catch ###
/// We use these keywords: `try`, `catch`, `finally`, `throw`, `throws`. The first two do the main job.
///
///     try {
///         // code that might throw an exception
///     } catch (ExceptionType e) {
///         // code to handle the exception
///     }
///
///   - `try` block: code that might throw an exception.
///   - `catch` block: code that handles it. `e` is the exception object, with details about what went wrong.
///   - After handling, the program does NOT crash. It continues normally after the catch block.

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

/// Follow the flow. Java ran the lines of the `try` block one by one. When the division failed, it jumped straight to
/// the matching `catch`, skipping the `println(result)` that came after. Then it carried on below. If nothing had gone
/// wrong, the catch would have been skipped altogether.
///
/// TIP: Only put the risky lines in `try`. A giant try block makes it hard to tell what went wrong.

/// ### Topic 4: Several catch blocks, and getMessage() ###
/// Different problems need different reactions, so you can write several `catch` blocks. Java checks them from the top
/// and uses the FIRST one that matches.
///
/// --- Example -----

public class Main {
    public static void main(String[] args) {
        try {
            int a = 10 / 0; // This causes ArithmeticException
            int[] arr = new int[3];
            System.out.println(arr[5]); // This would cause ArrayIndexOutOfBoundsException
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

/// Only the first problem happened (the division), so only the first `catch` ran. The line with the array never
/// executed, because the try block was abandoned at the division.
///
/// `e.getMessage()` gives just the explanation text, which is tidier for users than printing the whole exception.
///
/// WHY: Put the specific exceptions first and the general `Exception` last. `Exception` catches everything, so if it
/// came first, the others could never be reached, and the compiler tells you so:

public class Main {
    public static void main(String[] args) {
        try {
            int x = 5 / 0;
        } catch (Exception e) {
            System.out.println("general");
        } catch (ArithmeticException e) {
            System.out.println("specific");
        }
    }
}
/// @error

/// "exception ArithmeticException has already been caught".

/// ### Topic 5: The finally block ###
/// NOTE: The `finally` block ALWAYS runs, whether there was an exception or not, and whether it was caught or not.
///
/// It is for cleaning up: closing files, releasing resources, printing a final message.

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

/// And when nothing goes wrong, `finally` still runs:

public class Main {
    static int risky(int n) {
        try {
            return 100 / n;
        } catch (ArithmeticException e) {
            return -1;
        } finally {
            System.out.println("cleaning up for n = " + n);
        }
    }

    public static void main(String[] args) {
        System.out.println(risky(5));
        System.out.println(risky(0));
    }
}

/// Even though the method returned from inside `try`, the `finally` printed first. Java guarantees it.

/// ### Topic 6: The exception family tree ###
/// Exceptions are objects, and they are arranged in a family (inheritance, as you learned). That is why a catch for
/// `Exception` also catches its children.
///
///     Object
///       ↳ Throwable
///            ↳ Error       (serious problems such as running out of memory; do not try to handle)
///            ↳ Exception   (can and should be handled)
///                ↳ RuntimeException (unchecked)
///                ↳ other Exceptions (checked)
///
/// --- Handling summary -----
///     Term      Meaning
///     try       code block that might cause an exception
///     catch     code block that handles the exception
///     finally   code block that always runs (cleanup)
///     throw     to manually throw an exception
///     throws    declares that a method might throw an exception

/// ### Topic 7: Checked and unchecked exceptions ###
///     Kind                 Examples                                                                         Checked?
///     Runtime exceptions   ArithmeticException, NullPointerException, ArrayIndexOutOfBoundsException         no (unchecked)
///     Checked exceptions   IOException, SQLException, FileNotFoundException                                 yes
///
/// A CHECKED exception is one the compiler forces you to deal with. You must either catch it or declare it with
/// `throws`. These are for things outside your program's control, such as a missing file or a dropped connection. An
/// UNCHECKED exception is usually a bug in your own code (a bad index, a null), so the compiler does not force a
/// handler; you fix the code instead.

import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        FileReader file = new FileReader("somefile.txt");
        System.out.println("opened");
    }
}
/// @error

/// "unreported exception FileNotFoundException; must be caught or declared to be thrown". The compiler will not let
/// the risky line stand alone. The next lesson shows both ways out: catching it, or passing it on with `throws`.

/// ### Topic 8: try-with-resources ###
/// Some things must be closed after use, such as files and the Scanner. Forgetting is a classic bug. A
/// `try-with-resources` block closes them for you, automatically, even if an exception happens:
///
///     try (Scanner sc = new Scanner(System.in)) {
///         // use sc here
///     }   // sc.close() is called for you
///
/// It works with anything that is `AutoCloseable`. You will use it in the File handling lesson.
///
/// REMEMBER: Wrap risky code in `try`. Catch the specific problems first. Use `finally` (or try-with-resources) for
/// cleanup. A caught exception does not end the program; an uncaught one does.

/// ============================================================================
/// YOUR TURN
///   1. Read two numbers from the user and divide them. Use try and catch so that dividing by zero prints a friendly
///      message instead of crashing.
///   2. Read an age as text and convert it with `Integer.parseInt`. Catch `NumberFormatException` and ask again.
///   3. Write code that can throw both an `ArithmeticException` and an `ArrayIndexOutOfBoundsException`, with a
///      separate catch for each.
///   4. Put the general `Exception` catch before a specific one, and read the compiler's complaint.
///   5. Add a `finally` that prints "done", and show it runs whether or not an exception happens.
///   6. Sort these into checked and unchecked: NullPointerException, IOException, ArithmeticException,
///      FileNotFoundException.
/// ============================================================================
