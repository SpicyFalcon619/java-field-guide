/*
 * Java Basics: The switch statement
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - When switch is a better choice than a long if / else if chain
///   - What case, break and default do
///   - Fall-through, the classic switch trap
///   - The modern arrow form of switch
/// ============================================================================


/// ### Topic 1: Many choices, one value ###
/// Suppose a menu asks the user to type 1, 2 or 3. You could write three `else if` tests, each comparing the same
/// variable to a different number. That works, but it is repetitive. When you are comparing ONE value against
/// several fixed possibilities, `switch` is cleaner.
///
/// ANALOGY: A lift panel. You press one button, and the lift goes to exactly one floor. There is no thinking
/// involved: the value you gave selects the matching action directly.

/// ### Topic 2: The classic switch ###
/// The value in the brackets is compared with each `case`. When one matches, Java starts running the code from
/// there. The `default` branch is the catch-all, like `else`, and runs when nothing matched.

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter a number between 1 and 3: ");
        int choice = scan.nextInt();

        switch (choice) {
            case 1:
                System.out.println("You chose One");
                break;
            case 2:
                System.out.println("You chose Two");
                break;
            case 3:
                System.out.println("You chose Three");
                break;
            default:
                System.out.println("Invalid choice");
        }
        scan.close();
    }
}
/// @input 2
/// @input 7

/// Look closely at the word `break` at the end of every case. It is not decoration, and the next section shows
/// why.

/// ### Topic 3: The fall-through trap ###
/// WHY: `break` means "leave the switch now". Without it, Java keeps running into the NEXT case, and the next,
/// whether or not they match, until it hits a `break` or the end. This is called FALL-THROUGH. Watch what
/// happens when the breaks are missing.

public class Main {
    public static void main(String[] args) {
        int choice = 2;

        switch (choice) {
            case 1:
                System.out.println("One");
            case 2:
                System.out.println("Two");
            case 3:
                System.out.println("Three");
            default:
                System.out.println("Other");
        }
    }
}

/// Three lines came out of a single match. Java jumped to `case 2` and then simply kept going. This is the most
/// common switch bug.
///
/// Fall-through can also be useful on purpose, when several cases share the same action.

public class Main {
    public static void main(String[] args) {
        int month = 4;

        switch (month) {
            case 12:
            case 1:
            case 2:
                System.out.println("Winter");
                break;
            case 3:
            case 4:
            case 5:
                System.out.println("Spring");
                break;
            default:
                System.out.println("Some other season");
        }
    }
}

/// ### Topic 4: What can you switch on? ###
/// A switch works on `int`, `char`, `String` and a few related types (and enums, which come later). It does not
/// work on `double`, nor on ranges such as "marks above 60". For ranges, use `if / else if`.

public class Main {
    public static void main(String[] args) {
        String day = "SAT";

        switch (day) {
            case "SAT":
            case "SUN":
                System.out.println("Weekend");
                break;
            case "MON":
                System.out.println("Back to work");
                break;
            default:
                System.out.println("A normal day");
        }
    }
}

/// ### Topic 5: The modern arrow form ###
/// Newer versions of Java (14 and up) added a shorter switch. With `->` there is no fall-through, so `break` is
/// not needed, and it can even produce a value directly. Many programmers prefer it.

public class Main {
    public static void main(String[] args) {
        int day = 3;

        String name = switch (day) {
            case 1 -> "Monday";
            case 2 -> "Tuesday";
            case 3 -> "Wednesday";
            case 6, 7 -> "Weekend";
            default -> "Unknown";
        };

        System.out.println(name);
    }
}

/// TIP: Use `switch` when one value has several fixed possibilities. Use `if / else if` for ranges or for
/// conditions that involve several different variables.
///
/// REMEMBER: Always add a `default`, even if you believe it can never happen. It is the safety net.

/// ============================================================================
/// YOUR TURN
///   1. Read a number from 1 to 7 and print the name of the weekday.
///   2. Read a single character and print "vowel" or "consonant" using a switch with fall-through.
///   3. Build a small calculator: read two numbers and an operator (+ - * /) and print the result.
///   4. Delete a `break` from your answer to task 1 and describe what changes.
///   5. Rewrite task 1 using the arrow form.
/// ============================================================================
