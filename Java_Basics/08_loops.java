/*
 * Java Basics: Loops
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - Why we repeat work with loops instead of copying code
///   - The four loops: for, while, do-while and for-each, and when to pick each
///   - break and continue, to leave a loop or skip one round
///   - Loops inside loops, and the classic mistakes: infinite loops and off-by-one errors
/// ============================================================================


/// ### Topic 1: Why loops? ###
/// Imagine printing the numbers 1 to 1000. You could write a thousand `println` lines, but that would be
/// miserable, and changing it to 2000 would be worse. A LOOP repeats a block of code as many times as you want.
/// Each repetition is called an ITERATION.
///
/// ANALOGY: A washing machine cycle. "While there is still dirt, keep rinsing." A recipe that says "stir 10
/// times" is also a loop. You say the instruction once and say how many times, or until when.
///
/// Every loop has the same three ingredients, and most bugs come from getting one of them wrong:
///
///   1. A STARTING point (where the counter begins).
///   2. A CONDITION that decides whether to go round again.
///   3. A CHANGE to the counter on each round, so the loop eventually ends.

/// ### Topic 2: The for loop ###
/// Use `for` when you know how many times to repeat. All three ingredients sit together in one line, separated
/// by semicolons:
///
///     for (start; condition; change) { ... }
///
/// This loop starts with `i = 1`, keeps going as long as `i <= 5`, and adds one to `i` after each round.

public class Main {
    public static void main(String[] args) {
        // For loop
        for (int i = 1; i <= 5; i++) {
            System.out.println("For loop: " + i);
        }
    }
}

/// Follow what Java does, one step at a time:
///
///   1. Run the start once: `int i = 1`.
///   2. Check the condition `i <= 5`. True, so run the body.
///   3. After the body, run the change `i++`, so i becomes 2.
///   4. Check the condition again. Repeat until it is false. When i becomes 6 the check fails and the loop ends.
///
/// JARGON: The variable `i` is the LOOP COUNTER. By tradition it is named i (then j and k for loops inside
/// loops). It exists only inside the loop, which the Scope lesson explains later.
///
/// TIP: Counting from 0 is normal in programming (you will see why in the Arrays lesson). The usual pattern for
/// "do it n times" is `for (int i = 0; i < n; i++)`.

/// ### Topic 3: The while loop ###
/// Use `while` when you do NOT know how many rounds in advance, only the condition for carrying on. The
/// condition is checked BEFORE every round, so if it is false at the start the body never runs at all.

public class Main {
    public static void main(String[] args) {
        // While loop
        int j = 1;
        while (j <= 5) {
            System.out.println("While loop: " + j);
            j++;
        }
    }
}

/// Here the three ingredients are spread out: the start is above the loop, the condition is in the brackets, and
/// the change is the last line inside the body.
///
/// MISTAKE: If you forget the change (`j++`), the condition stays true forever and the program never stops. That
/// is an INFINITE LOOP. Here is a harmless version of it that stops itself, so you can see what it looks like.

public class Main {
    public static void main(String[] args) {
        int count = 0;
        while (true) {
            System.out.println("Still looping, round " + count);
            count++;
            if (count == 3) {
                break;          // the emergency exit
            }
        }
        System.out.println("Out of the loop.");
    }
}

/// `while (true)` has a condition that can never be false, so the only way out is `break`, which is explained
/// below. If you ever run a program that seems frozen, an infinite loop is the first suspect. Press Ctrl+C in the
/// terminal to stop it.

/// ### Topic 4: The do-while loop ###
/// A `do-while` is a `while` that checks its condition AFTER the body. That means the body always runs at least
/// once, even if the condition is false from the start.

public class Main {
    public static void main(String[] args) {
        // Do-while loop
        int k = 1;
        do {
            System.out.println("Do-while loop: " + k);
            k++;
        } while (k <= 5);
    }
}

/// Notice the semicolon after the closing `while (...)`. It is required here and nowhere else in loops.
///
/// WHY: The classic use is a menu or an input check: you must ask the user at least once, then ask again only if
/// the answer was bad. Compare the two loops when the starting value already fails the condition:

public class Main {
    public static void main(String[] args) {
        int n = 10;

        while (n < 5) {
            System.out.println("while body: never runs");
        }

        do {
            System.out.println("do-while body: runs once");
        } while (n < 5);
    }
}

/// ### Topic 5: The for-each loop ###
/// When you want to visit every item of a collection, such as an array, the for-each loop is the simplest. You
/// do not manage a counter. Java hands you the items one by one. Read `for (int mark : marks)` as "for each int
/// called mark in marks".

public class Main {
    public static void main(String[] args) {
        // For-each loop
        int[] marks = {85, 90, 78};

        for (int mark : marks) {
            System.out.println("Mark: " + mark);
        }
    }
}

/// An array is a numbered row of values; the Arrays lesson covers it fully. There is one thing about for-each to
/// know now. The variable in the loop is a COPY of each item, so changing it does not change the original.

public class Main {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3};

        for (int num : nums) {
            num = num * 2; // Doesn't affect the original array
        }

        for (int x : nums) {
            System.out.print(x + " ");
        }
    }
}

/// The numbers are still 1 2 3. Each `num` was a photocopy of the real value, and doubling a photocopy leaves the
/// original untouched. If you need to change the items, use a normal `for` loop with an index.
///
/// --- Which loop should I use? -----
///
///     for        you know how many times, or you are counting
///     while      repeat until something happens
///     do-while   the body must run at least once
///     for-each   visit every item of an array or list

/// ### Topic 6: break and continue ###
/// Two keywords give you control inside a loop.
///
///   - `break` leaves the whole loop immediately.
///   - `continue` skips the rest of THIS round and jumps to the next one.

public class Main {
    public static void main(String[] args) {
        for (int i = 1; i <= 5; i++) {
            if (i == 3) {
                break;
            }
            System.out.println(i);
        }
    }
}

/// The loop printed 1 and 2, then `break` ended everything when i reached 3. Compare with `continue`:

public class Main {
    public static void main(String[] args) {
        for (int i = 1; i <= 5; i++) {
            if (i == 3) {
                continue;
            }
            System.out.println(i);
        }
    }
}

/// Now 3 is missing, but 4 and 5 are printed. `continue` skipped only the printing for that one round and the
/// loop carried on. Remember it as: `break` = stop the whole thing, `continue` = skip just this one.

/// ### Topic 7: Loops inside loops ###
/// A loop can contain another loop. For every single round of the outer loop, the inner loop runs completely.
/// This is how you make tables, grids and patterns.

public class Main {
    public static void main(String[] args) {
        for (int row = 1; row <= 3; row++) {
            for (int col = 1; col <= 4; col++) {
                System.out.print("* ");
            }
            System.out.println();     // end the row
        }
    }
}

/// The outer loop makes the 3 rows. For each row, the inner loop prints 4 stars on the same line using `print`,
/// then the `println()` after it starts a new line. The inner body runs 3 x 4 = 12 times in total.
///
/// The inner loop's limit can depend on the outer counter. This makes a triangle:

public class Main {
    public static void main(String[] args) {
        for (int row = 1; row <= 4; row++) {
            for (int col = 1; col <= row; col++) {
                System.out.print(row + " ");
            }
            System.out.println();
        }
    }
}

/// ### Topic 8: Classic mistakes ###
/// Almost every loop bug is the same bug: the loop runs one time too many or one time too few. This is the
/// OFF-BY-ONE error. It usually comes from a wrong `<` versus `<=`.

public class Main {
    public static void main(String[] args) {
        // Goal: print 1 2 3 4 5
        for (int i = 1; i < 5; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        // Goal: print 5 numbers, counting from 0
        for (int i = 0; i <= 5; i++) {
            System.out.print(i + " ");
        }
    }
}

/// The first loop stops at 4 because `i < 5` is false when i is 5. The second prints SIX numbers because
/// `i <= 5` includes 5 when counting from 0. When you write a loop, check the first value and the last value by
/// hand.
///
/// TIP: To debug a loop, print the counter at the top of the body. Seeing the values is faster than guessing.

/// ============================================================================
/// YOUR TURN
///   1. Print the numbers from 10 down to 1 with a for loop.
///   2. Print the sum of all the numbers from 1 to 100.
///   3. Print the multiplication table of a number the user types, from 1 to 10.
///   4. Use a while loop to count the digits of a number such as 48213 (divide by 10 until it is 0).
///   5. Print only the odd numbers up to 20, first with continue, then by adding 2 each time.
///   6. Print this pattern with nested loops: a triangle of stars where row 1 has 1 star, row 2 has 2, and so on
///      up to 5.
///   7. Write a do-while that keeps asking for a password until the user types the right one.
/// ============================================================================
