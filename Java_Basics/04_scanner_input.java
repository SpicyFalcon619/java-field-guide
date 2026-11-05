/*
 * Java Basics: Reading input with Scanner
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - Why programs need input, and how Scanner reads it from the keyboard
///   - What `import java.util.Scanner;` means and why Java makes you write it
///   - Reading text, whole numbers and decimals
///   - The famous nextInt / nextLine trap, and what happens when the user types the wrong thing
/// ============================================================================


/// ### Topic 1: Why input? ###
/// Until now every program did the same thing each time you ran it, because we typed all the values into the
/// code. Real programs ask the user. A calculator needs your numbers, a login screen needs your password.
///
/// In Java, the standard way to read what a person types on the keyboard is a tool called Scanner. Using it
/// takes three steps:
///
///   1. IMPORT it (tell Java where to find the tool).
///   2. CREATE a Scanner connected to the keyboard.
///   3. CALL one of its methods to read a value.

/// ### Topic 2: Your first Scanner program ###
/// Here is the complete program. Read the comments, then we will go through the three steps.

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = scan.nextLine();         // Reads full line of text

        System.out.print("Enter your age: ");
        int age = scan.nextInt();              // Reads an integer

        System.out.println("Hello " + name + ", you are " + age + " years old.");
        scan.close();
    }
}
/// @input Noman | 35

/// The "Typed input" menu under the block shows what the person typed. When you run it yourself, you type the
/// name, press Enter, type the age, and press Enter again.

/// ### Topic 3: What does `import java.util.Scanner;` mean? ###
/// This is the line that confuses almost everyone, so let us take it slowly.
///
/// Java comes with a huge library of ready-made tools: for printing, maths, dates, lists, files, networking and
/// much more. There are thousands of them. To keep them organised, Java groups related tools into folders called
/// PACKAGES. The package `java.util` ("utilities") holds handy general-purpose tools, and Scanner lives inside it.
///
/// ANALOGY: Think of a huge school library. The books are sorted into sections. If you want a cookery book, you
/// cannot just say its title and expect it to appear in your hands. You have to say which section it is in. The
/// import line is you telling Java: "I will be using the Scanner book from the java.util section."
///
///     import java.util.Scanner;
///          ^ package ^ the tool
///
/// WHY: Why doesn't Java load everything automatically? Because most programs use only a few tools, and loading
/// thousands would be wasteful and would cause name clashes (two tools might share a name). So Java makes you
/// ask for what you need.
///
/// The one exception is the package `java.lang`, which holds the essentials: `System`, `String`, `Math` and a few
/// others. Java imports it for you, which is why `System.out.println` works without any import line. That is the
/// whole reason Scanner needs one and `System` does not.
///
/// TIP: The import lines always go at the very top of the file, above the class. You can also write
/// `import java.util.*;` which means "everything in java.util". That works, but naming the exact tool, as we do
/// here, makes it clearer what the program uses.
///
/// Here is what happens if you forget it:

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("This never runs.");
    }
}
/// @error

/// The compiler says "cannot find symbol ... class Scanner". It is not that Scanner does not exist. Java just
/// does not know which section of the library to look in. Add the import line and the error disappears. Most IDEs
/// can add it for you: click the red word and accept the suggestion.

/// ### Topic 4: The line `Scanner scan = new Scanner(System.in);` ###
/// This single line looks heavy, so split it in two.
///
///     Scanner scan                  declares a variable called scan whose type is Scanner
///     = new Scanner(System.in)      builds a new Scanner and stores it in that variable
///
/// `new` makes a fresh OBJECT, a working copy of the Scanner tool. `System.in` says where the Scanner should
/// read from: the standard input, which is the keyboard. If you gave it a file instead, it would read the file.
/// You can name the variable anything. `scan`, `sc` and `input` are all common.
///
/// JARGON: An OBJECT is a live thing made from a class. The class Scanner is the blueprint; `scan` is one real
/// Scanner built from it. Objects are the heart of Java, and the later lessons are all about them. For now,
/// just copy this line exactly, the way you would learn a spell before the theory of magic.
///
/// The last line, `scan.close()`, tells Java you have finished with the keyboard, which is good manners and
/// frees resources.

/// ### Topic 5: Reading different kinds of values ###
/// Scanner has a method for each type of thing you might want to read.
///
///     nextLine()      a whole line of text, including spaces
///     next()          a single word (stops at the first space)
///     nextInt()       a whole number
///     nextDouble()    a decimal number
///     nextBoolean()   true or false
///
/// The method has to match what you intend to store: `int age = scan.nextInt();`, `double price =
/// scan.nextDouble();`, `String city = scan.nextLine();`.

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.print("First number: ");
        int a = scan.nextInt();
        System.out.print("Second number: ");
        int b = scan.nextInt();

        System.out.println("Sum = " + (a + b));
        scan.close();
    }
}
/// @input 10 | 20

/// The brackets around `a + b` matter. Without them Java would join the numbers as text and print 1020, because
/// the `+` after the string `"Sum = "` means concatenation. You will see why in the Operators lesson.
///
/// A program can ask several questions, one after another, and each one gets the next piece of typed input.

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter radius: ");
        double radius = scan.nextDouble();

        double area = 3.14159 * radius * radius;
        System.out.println("Area = " + area);
        scan.close();
    }
}
/// @input 2.5

/// ### Topic 6: The nextInt / nextLine trap ###
/// This one catches almost every beginner. Read it carefully, because it will save you hours.
///
/// When you type something and press Enter, the keyboard sends your text AND an invisible new-line character.
/// `nextInt()` reads only the number and leaves the new-line sitting in the queue. The next `nextLine()` then
/// reads from where `nextInt()` stopped, finds the leftover new-line immediately, and returns an EMPTY string,
/// without waiting for you to type anything.

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter your age: ");
        int age = scan.nextInt();

        System.out.print("Enter your name: ");
        String name = scan.nextLine();     // reads the leftover new-line, not your name!

        System.out.println("Name = [" + name + "], age = " + age);
        scan.close();
    }
}
/// @input 25 | Rafi

/// The name came out empty, and the program never waited for "Rafi". The brackets in the output make the empty
/// text visible. The fix is to read and throw away the leftover new-line with an extra `nextLine()` before the
/// real one.

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter your age: ");
        int age = scan.nextInt();
        scan.nextLine();                   // eat the leftover new-line

        System.out.print("Enter your name: ");
        String name = scan.nextLine();

        System.out.println("Name = [" + name + "], age = " + age);
        scan.close();
    }
}
/// @input 25 | Rafi

/// MISTAKE: Whenever you read a number (`nextInt`, `nextDouble`) and then a full line (`nextLine`), put an extra
/// `scan.nextLine();` between them. The simplest way to avoid the trap altogether is to read everything with
/// `nextLine()` and convert it, or to ask for the text first and the numbers last.

/// ### Topic 7: When the user types the wrong thing ###
/// You asked for a number, and the user typed "abc". Java cannot turn that into an int, so the program stops with
/// an EXCEPTION. An exception is Java's way of saying "something went wrong while the program was running".

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int n = scan.nextInt();

        System.out.println("You typed " + n);
        scan.close();
    }
}
/// @input abc
/// @error

/// The error output is called a STACK TRACE. Read it from the top: the first line names the problem
/// (`InputMismatchException`), and the lines under it show where it happened, beginning with your own file. The
/// "Run" output above shows exactly that.
///
/// This is a runtime error, different from a compile error. The program compiled perfectly, and only broke when
/// it met a bad value. We will learn how to catch and handle such errors gracefully in the Exception Handling
/// lesson.
///
/// REMEMBER: Never trust what a user types. Real programs check input before using it.

/// ============================================================================
/// YOUR TURN
///   1. Ask the user for their name and print "Welcome, <name>!".
///   2. Ask for two decimal numbers and print their product.
///   3. Ask for a length and a width, then print the area and the perimeter of the rectangle.
///   4. Ask for the user's age and then their full name (with spaces), and make sure the name is read correctly.
///   5. Remove the import line from a working program. Read the error, then put it back.
///   6. Ask for a number, type a word instead, and find the line of the stack trace that points at your code.
/// ============================================================================
