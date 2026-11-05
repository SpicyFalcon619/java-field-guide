/*
 * Java Basics: Making decisions with if and else
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - How a program chooses between actions with if, else if and else
///   - How conditions work, and how to combine them with && and ||
///   - Nested decisions, and how the grade program is built
///   - The common traps: = versus ==, stray semicolons and missing braces
///   - The ternary operator as a one-line if
/// ============================================================================


/// ### Topic 1: Why programs need to decide ###
/// So far every program ran straight from the first line to the last. Real programs react: "if the password is
/// wrong, show an error", "if the marks are 40 or more, pass the student". This is called CONTROL FLOW: choosing
/// which statements run, and which are skipped.
///
/// The tool for it is the `if` statement. It asks a yes/no question, and runs its block of code only when the
/// answer is yes.
///
///     if (condition) {
///         // runs only when the condition is true
///     }
///
/// The CONDITION is any expression that produces a `boolean`, such as the comparisons from the last lesson:
/// `age >= 18`, `score == 100`, `name != null`.

public class Main {
    public static void main(String[] args) {
        int temperature = 35;

        if (temperature > 30) {
            System.out.println("It is hot today.");
        }
        System.out.println("This line always runs.");
    }
}

/// Change the temperature to 20 and the first message disappears, while the second stays. A block of code inside
/// `{ }` is skipped completely when its condition is false.

/// ### Topic 2: else and else if ###
/// Often you want one action when the condition is true and a different one when it is false. Add `else`. When
/// there are several possibilities, chain them with `else if`. Java checks them from top to bottom and runs the
/// FIRST one that is true. Then it skips the rest.
///
/// ANALOGY: It is like a road with signs. At each junction you ask "is this my turn?". The moment you find the
/// right turn you take it, and you do not keep checking the later junctions.

public class Main {
    public static void main(String[] args) {
        int number = 10;

        if (number > 0) {
            System.out.println("Positive number");
        } else if (number < 0) {
            System.out.println("Negative number");
        } else {
            System.out.println("Zero");
        }
    }
}

/// The `else` at the end has no condition. It is the catch-all for everything that did not match above, so it
/// runs only when all the earlier conditions were false. Exactly one of the three branches will run, never two.
///
/// WHY: Order matters. In a chain such as `if (marks >= 40) ... else if (marks >= 60) ...` the second branch could
/// never run, because anything that is 60 or more is already 40 or more and was caught by the first. Put the
/// most specific condition first.

/// ### Topic 3: Decisions inside decisions ###
/// A block can contain another `if`. This is called NESTING. The program below turns a mark into a grade. First
/// it asks whether the student passed. Only inside that branch does it ask whether they did very well.

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter your marks: ");
        int marks = scan.nextInt();

        if (marks >= 60) {
            if (marks >= 90) {
                System.out.println("Grade: A+");
            } else {
                System.out.println("Grade: A");
            }
        } else {
            if (marks >= 40) {
                System.out.println("Grade: B");
            } else {
                System.out.println("Fail");
            }
        }
        scan.close();
    }
}
/// @input 92
/// @input 75
/// @input 50
/// @input 30

/// Use the "Typed input" menu to try each of the four marks and trace the path through the code by hand. 92 goes
/// into the first branch (60 or more) and then into the inner one (90 or more), so it earns an A+. 30 fails
/// the first test, goes to the `else`, fails the inner test too, and prints Fail.
///
/// TIP: When you read nested code, work from the outside in. Ask the outer question first, then step into the
/// branch that matches and ask the next. Indentation exists to show you the levels, so keep it neat.

/// ### Topic 4: Combining conditions ###
/// Nesting is not the only way. The logical operators `&&` (and) and `||` (or) let one condition ask several
/// questions at once, which often reads better than deep nesting.

public class Main {
    public static void main(String[] args) {
        int age = 22;
        boolean hasLicense = true;

        if (age >= 18 && hasLicense) {
            System.out.println("You may drive.");
        } else if (age >= 18) {
            System.out.println("Get a license first.");
        } else {
            System.out.println("You are too young.");
        }

        char c = 'x';
        if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
            System.out.println("vowel");
        } else {
            System.out.println("not a vowel");
        }
    }
}

/// MISTAKE: You cannot write `c == 'a' || 'e'`. In English we say "c is a or e", but in Java each side of `||`
/// must be a complete condition on its own. Repeat `c ==` each time.

/// ### Topic 5: Mistakes that look innocent ###
/// Three small slips cause most `if` bugs. The first is to use `=` where you meant `==`.

public class Main {
    public static void main(String[] args) {
        int x = 5;
        if (x = 10) {
            System.out.println("x is ten");
        }
    }
}
/// @error

/// Java rejects this: `x = 10` is an assignment, which produces the number 10, not a true/false answer. The
/// compiler says "incompatible types: int cannot be converted to boolean". It is a lucky catch. In some
/// languages the same slip compiles and silently changes your variable.
///
/// The second slip is a stray semicolon directly after the condition. It looks harmless, but it ends the `if`
/// statement with an EMPTY body, and the braces below it then run unconditionally.

public class Main {
    public static void main(String[] args) {
        int score = 10;
        if (score > 50);
        {
            System.out.println("You passed!");
        }
    }
}

/// The score is only 10, and it still says passed. The `;` after the condition is the body of the `if`, and
/// the block underneath was never part of it. Never put a semicolon after `if (...)`.
///
/// The third slip is leaving out the braces. If you do, only the ONE next line belongs to the `if`. Anything
/// after it runs regardless, even if the indentation makes it look attached.

public class Main {
    public static void main(String[] args) {
        int temperature = 10;
        if (temperature > 30)
            System.out.println("It is hot.");
            System.out.println("Drink water.");
    }
}

/// "Drink water." printed even though it is cold, because Java pays no attention to indentation. Only the braces
/// count.
///
/// TIP: Always use braces, even for a single line. It costs two characters and prevents this whole class of bug.

/// ### Topic 6: The ternary operator, a one-line if ###
/// When an `if` only picks between two values, Java has a compact form with three parts, so it is called the
/// TERNARY operator:
///
///     condition ? valueIfTrue : valueIfFalse

public class Main {
    public static void main(String[] args) {
        int marks = 55;
        String result = (marks >= 40) ? "Pass" : "Fail";
        System.out.println(result);

        int a = 8, b = 12;
        int bigger = (a > b) ? a : b;
        System.out.println("Bigger: " + bigger);
    }
}

/// Read it as "is marks at least 40? If yes, Pass, otherwise Fail". It is handy for short choices. If you find
/// yourself nesting ternaries or choosing between whole blocks of statements, go back to a normal `if`.
///
/// REMEMBER: Comparing text uses `.equals(...)` and not `==`. We explain why in the Strings lesson. For now,
/// use `==` only for numbers and characters.

/// ============================================================================
/// YOUR TURN
///   1. Read a number and print whether it is positive, negative or zero.
///   2. Read an age and print "child" (under 13), "teenager" (13 to 19) or "adult".
///   3. Read three numbers and print the largest, using if and else.
///   4. Read a year and decide whether it is a leap year: divisible by 4, except centuries, unless divisible by
///      400.
///   5. Extend the grade program with a C grade, and make sure marks above 100 or below 0 print "Invalid".
///   6. Rewrite your answer to task 1 using the ternary operator for just the positive/not-positive case.
/// ============================================================================
