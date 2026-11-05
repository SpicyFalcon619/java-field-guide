/*
 * Java Basics: Operators
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - The arithmetic operators, and why 7 / 2 is 3 in Java
///   - The remainder operator %, and what it is used for
///   - Shortcuts: +=, ++ and --
///   - Comparison and logical operators, which produce true or false
///   - Operator precedence, and how + behaves with text
/// ============================================================================


/// ### Topic 1: What is an operator? ###
/// An OPERATOR is a symbol that does something to values. In `3 + 4`, the `+` is the operator and 3 and 4 are its
/// OPERANDS. The whole thing, which produces a value, is called an EXPRESSION.
///
/// Java has four groups of operators you will use every day:
///
///     arithmetic    +  -  *  /  %          do maths
///     assignment    =  +=  -=  *=  /=      store a value
///     comparison    ==  !=  <  >  <=  >=   compare, give true or false
///     logical       &&  ||  !              combine true/false answers

/// ### Topic 2: Arithmetic ###
/// The first four are the ones you know from school: add, subtract, multiply, divide. The fifth, `%`, is the
/// REMAINDER (also called modulus) and gives what is left over after a division.

public class Main {
    public static void main(String[] args) {
        int a = 17;
        int b = 5;

        System.out.println("a + b = " + (a + b));
        System.out.println("a - b = " + (a - b));
        System.out.println("a * b = " + (a * b));
        System.out.println("a / b = " + (a / b));
        System.out.println("a % b = " + (a % b));
    }
}

/// Wait: 17 divided by 5 is 3.4, but Java printed 3. This is the single most important thing in this lesson.
///
/// WHY: When BOTH sides of a division are int, Java does INTEGER DIVISION. The answer is also an int, so the
/// decimal part is chopped off (not rounded). 17 / 5 is 3, and the 2 that is left over is what `17 % 5` gives.
/// The result has the type of the operands, because an int cannot hold a fraction.
///
/// ANALOGY: Share 17 sweets among 5 children. Each child gets 3 (that is `/`), and 2 sweets are left over (that is
/// `%`). Integer division is "how many whole shares", and remainder is "what is left".
///
/// To get the true answer, at least one side must be a double.

public class Main {
    public static void main(String[] args) {
        System.out.println(17 / 5);        // int / int      -> 3
        System.out.println(17 / 5.0);      // int / double   -> 3.4
        System.out.println(17.0 / 5);      // double / int   -> 3.4

        int a = 17, b = 5;
        System.out.println(a / b);              // 3, the damage is done before printing
        System.out.println((double) a / b);     // 3.4, cast first, then divide
        System.out.println((double) (a / b));   // 3.0, divided first, cast too late
    }
}

/// MISTAKE: The last two lines look similar and give different answers. The cast `(double)` applies only to the
/// thing right next to it. In `(double) a / b` it converts `a` first, so the division is a double division. In
/// `(double) (a / b)` the integer division happens first and the damage is already done.
///
/// What about dividing by zero? Integers cannot do it, and the program stops with an `ArithmeticException`.
/// Doubles are kinder and give `Infinity`.

public class Main {
    public static void main(String[] args) {
        System.out.println(5.0 / 0);
        System.out.println(5 / 0);
    }
}
/// @error

/// ### Topic 3: What is the remainder used for? ###
/// `%` looks odd, but programmers use it all the time.
///
///   - Even or odd: `n % 2 == 0` means n is even.
///   - Is one number divisible by another: `n % 5 == 0`.
///   - Wrapping around: the clock. `(hour + 5) % 12` gives the hour five hours from now.
///   - Taking digits apart: `n % 10` is the last digit, and `n / 10` is the number without it.

public class Main {
    public static void main(String[] args) {
        int n = 4827;
        System.out.println("Last digit: " + n % 10);
        System.out.println("Without it: " + n / 10);
        System.out.println("Is 4827 even? " + (n % 2 == 0));
        System.out.println("10 hours after 7 o'clock: " + (7 + 10) % 12);
    }
}

/// ### Topic 4: Assignment shortcuts ###
/// Adding to a variable is so common that Java gives you shorter ways to write it.
///
///     x = x + 5;   same as   x += 5;
///     x = x - 2;   same as   x -= 2;
///     x = x * 3;   same as   x *= 3;
///     x = x / 4;   same as   x /= 4;
///     x = x + 1;   same as   x++;
///     x = x - 1;   same as   x--;

public class Main {
    public static void main(String[] args) {
        int score = 10;

        score += 5;
        System.out.println(score);     // 15

        score *= 2;
        System.out.println(score);     // 30

        score++;
        System.out.println(score);     // 31

        score--;
        score--;
        System.out.println(score);     // 29
    }
}

/// `++` and `--` are called INCREMENT and DECREMENT: add one, subtract one. They have two forms, and the
/// difference matters when you use them inside a larger expression.
///
///   - `x++` (post-increment): use the current value first, then add one.
///   - `++x` (pre-increment): add one first, then use the new value.

public class Main {
    public static void main(String[] args) {
        int x = 5;
        int y = x++;      // y gets the OLD value, then x grows
        System.out.println("x = " + x + ", y = " + y);

        int p = 5;
        int q = ++p;      // p grows first, q gets the NEW value
        System.out.println("p = " + p + ", q = " + q);
    }
}

/// TIP: When `x++` stands on a line by itself, both forms do the same thing. Only the version that is part of
/// something bigger shows the difference. If it ever feels confusing, write it on two separate lines instead.

/// ### Topic 5: Comparison operators ###
/// Comparison operators ask a question about two values and answer with a `boolean`: `true` or `false`.
///
///     ==   is equal to            !=   is not equal to
///     >    greater than           <    less than
///     >=   greater or equal       <=   less or equal

public class Main {
    public static void main(String[] args) {
        int age = 18;

        System.out.println(age == 18);
        System.out.println(age != 18);
        System.out.println(age > 20);
        System.out.println(age >= 18);
        System.out.println(age < 10);
    }
}

/// MISTAKE: `=` and `==` are completely different. `age = 18` STORES 18 into age. `age == 18` ASKS whether age is
/// 18. Writing one where you meant the other is a classic beginner bug. Say it aloud: single equals is "becomes",
/// double equals is "is it equal to?".

/// ### Topic 6: Logical operators ###
/// Logical operators combine true/false answers, like the words "and", "or" and "not" in a sentence.
///
///     &&   AND   true only if BOTH sides are true
///     ||   OR    true if AT LEAST ONE side is true
///     !    NOT   flips true to false and false to true
///
/// ANALOGY: "You can enter the ride if you are tall AND you have a ticket." Both conditions are required. "You
/// get free entry if you are a child OR a senior." Either one is enough.

public class Main {
    public static void main(String[] args) {
        int age = 25;
        boolean hasTicket = true;

        System.out.println(age >= 18 && hasTicket);     // both true
        System.out.println(age < 18 || hasTicket);      // one is true
        System.out.println(!hasTicket);                 // flipped
        System.out.println(age < 18 && hasTicket);      // one is false
    }
}

/// Java is clever about `&&` and `||`: it stops looking as soon as the answer is certain. In `false && anything`,
/// the answer is already false, so the right side is never evaluated. This is called SHORT-CIRCUITING, and it
/// lets you write safe checks such as `b != 0 && a / b > 2`, which never divides by zero.

/// ### Topic 7: Precedence, and + with text ###
/// When an expression has several operators, Java follows an order, just like maths class: multiplication and
/// division first, then addition and subtraction. This ordering is called PRECEDENCE. Brackets override it.

public class Main {
    public static void main(String[] args) {
        System.out.println(2 + 3 * 4);       // 14, the * goes first
        System.out.println((2 + 3) * 4);     // 20, brackets win
        System.out.println(10 - 2 - 3);      // 5, left to right
    }
}

/// TIP: When in doubt, add brackets. They cost nothing and make the meaning obvious to the next reader.
///
/// Finally, `+` has a second job. When one side is a String it joins instead of adding, and Java works left to
/// right, so the order of things can change the answer.

public class Main {
    public static void main(String[] args) {
        System.out.println("Sum: " + 1 + 2);       // text first: joins "1" then "2"
        System.out.println("Sum: " + (1 + 2));     // brackets: add first
        System.out.println(1 + 2 + " is the sum"); // numbers first: 3, then joined
    }
}

/// REMEMBER: Once a String is involved, everything to its right is joined as text unless you use brackets. When
/// you print a calculation inside a message, wrap it in brackets.

/// ============================================================================
/// YOUR TURN
///   1. Predict the value of each, then check: `7 / 2`, `7 % 2`, `7 / 2.0`, `-7 / 2`, `-7 % 2`.
///   2. Given `int n = 9;`, print whether n is even and whether n is divisible by 3.
///   3. Convert 500 minutes into hours and leftover minutes, using / and %.
///   4. Write an expression that gives the middle digit of a three-digit number.
///   5. What does this print: `int a = 3; int b = a++ + ++a;`, and what are a and b? Work it out on paper first.
///   6. Why does `"Total: " + 5 + 5` print 55, and how do you get 10?
/// ============================================================================
