/*
 * Lab: Lab 1: Setting up, Hello World and first programs
 */

/// ============================================================================
/// WHAT YOU WILL PRACTISE
///   - Installing the tools and running a program from the terminal and from an IDE
///   - Printing, variables, arithmetic and user input
///   - Reading compile errors and fixing them
/// ============================================================================
///
/// How to use a lab page: every task has a statement. Try it on your own computer FIRST. Then compare with the worked
/// solution under it. The solutions are complete programs that run, with their real output beside them.


/// ### Setup: get ready ###
///   1. Install a JDK (Java Development Kit), version 17 or newer.
///   2. Open a terminal and check both tools: `java -version` and `javac -version`.
///   3. Install an IDE: IntelliJ IDEA Community or Visual Studio Code with the "Extension Pack for Java".
///   4. Make a folder for your lab work, such as `JavaLab`, and a file `Main.java` inside it.
///
/// --- Run it from the terminal -----
///
///     cd JavaLab
///     javac Main.java      compiles, producing Main.class
///     java Main            runs it
///
/// --- Run it from the IDE -----
/// Create a new Java project, add a class called `Main`, type the program, and press the green Run button.
///
/// TIP: The file name must match the public class name exactly, including capitals: `Main` goes in `Main.java`.

/// ### Task 1: Hello, World ###
/// Print "Hello, World!" on the screen.

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}

/// ### Task 2: About me ###
/// Print your name, your university ID and your department on three separate lines. Then print them all on ONE line,
/// separated by commas, using `print` instead of `println`.

public class Main {
    public static void main(String[] args) {
        System.out.println("Name: Rafi Ahmed");
        System.out.println("ID: 011231234");
        System.out.println("Department: CSE");

        System.out.print("Rafi Ahmed, ");
        System.out.print("011231234, ");
        System.out.println("CSE");
    }
}

/// ### Task 3: A shape made of stars ###
/// Print this triangle. (Hint: one `println` per row, or a loop.)
///
///     *
///     **
///     ***
///     ****

public class Main {
    public static void main(String[] args) {
        for (int row = 1; row <= 4; row++) {
            for (int star = 1; star <= row; star++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}

/// ### Task 4: Variables and arithmetic ###
/// Declare two numbers, 25 and 4. Print their sum, difference, product, quotient and remainder, with a label on each. Notice
/// what the division gives, and then print the quotient again as a decimal.

public class Main {
    public static void main(String[] args) {
        int a = 25;
        int b = 4;

        System.out.println("Sum: " + (a + b));
        System.out.println("Difference: " + (a - b));
        System.out.println("Product: " + (a * b));
        System.out.println("Quotient (int): " + (a / b));
        System.out.println("Quotient (decimal): " + ((double) a / b));
        System.out.println("Remainder: " + (a % b));
    }
}

/// ### Task 5: Read two numbers and add them ###
/// Ask the user for two whole numbers and print their sum.

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.print("First number: ");
        int a = scan.nextInt();
        System.out.print("Second number: ");
        int b = scan.nextInt();

        System.out.println(a + " + " + b + " = " + (a + b));
        scan.close();
    }
}
/// @input 12 | 30

/// ### Task 6: Area and perimeter of a circle ###
/// Read a radius (a decimal number) and print the area (pi r squared) and the circumference (2 pi r) with two decimal places.

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Radius: ");
        double r = scan.nextDouble();

        double area = Math.PI * r * r;
        double circumference = 2 * Math.PI * r;

        System.out.printf("Area = %.2f%n", area);
        System.out.printf("Circumference = %.2f%n", circumference);
        scan.close();
    }
}
/// @input 5

/// ### Task 7: Convert temperature ###
/// Read a temperature in Celsius and print it in Fahrenheit. The formula is F = C x 9 / 5 + 32. Careful with integer division:
/// use `9.0 / 5`.

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Celsius: ");
        double c = scan.nextDouble();

        double f = c * 9.0 / 5 + 32;
        System.out.println(c + " C = " + f + " F");
        scan.close();
    }
}
/// @input 36.6

/// ### Task 8: Swap two numbers ###
/// Read two numbers and swap their values using a third variable. Print them before and after.

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.print("a: ");
        int a = scan.nextInt();
        System.out.print("b: ");
        int b = scan.nextInt();

        System.out.println("Before: a = " + a + ", b = " + b);
        int temp = a;
        a = b;
        b = temp;
        System.out.println("After:  a = " + a + ", b = " + b);
        scan.close();
    }
}
/// @input 7 | 99

/// ### Task 9: Find and fix the errors ###
/// This program has three mistakes. Without running it, find them. Then compile it and see whether the compiler agrees.

public class Main {
    public static void main(String[] args) {
        int age = 20
        System.out.println("Age: " + Age);
        System.out.println("Done);
    }
}
/// @error

/// The mistakes: a missing semicolon after `20`; `Age` should be `age` (Java is case-sensitive); and the last string has no
/// closing quote. Fix all three. The compiler reports the first problem it can understand, and fixing one often reveals the
/// next, so work from the top.

/// ### Check yourself ###
///   - Can you compile and run from the terminal without the IDE?
///   - Why does `25 / 4` print 6 but `25 / 4.0` print 6.25?
///   - What is the difference between `print` and `println`?
///   - What happens if you save the class `Main` in a file called `main.java`?

/// ### Try next ###
///   1. Print a diamond of stars.
///   2. Read a name and an age, and print "Hello <name>, next year you will be <age + 1>."
///   3. Convert minutes into hours and minutes (for example 135 becomes 2 hours 15 minutes).
///   4. Read the length and width of a rectangle and print its area and perimeter.
