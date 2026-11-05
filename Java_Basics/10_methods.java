/*
 * Java Basics: Methods
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What a method is, and why splitting code into methods makes programs easier
///   - Parameters and return values, and what void means
///   - How to call a method, and what happens to the values you pass in
///   - Common errors: missing return, wrong arguments, and calling from main
///   - A first look at recursion, a method that calls itself
/// ============================================================================


/// ### Topic 1: Why methods? ###
/// Until now everything has lived inside `main`. As programs grow, `main` becomes a giant list that nobody can
/// read, and the same few lines get copied again and again. A METHOD is a named group of statements that you
/// write once and use as many times as you like.
///
/// ANALOGY: A method is a recipe card with a name. "Make tea" is written down once. Whenever tea is needed, you
/// do not rewrite the steps. You just say "make tea". And if the recipe improves, you fix it in one place.
///
/// There are two moves to learn:
///
///   1. DEFINING a method (writing the recipe card).
///   2. CALLING it (asking for it to be followed).

/// ### Topic 2: Defining and calling a method ###
/// The simplest method takes nothing and gives nothing back.

public class Main {
    public static void greet() {
        System.out.println("Hello from a method!");
    }

    public static void main(String[] args) {
        greet();
    }
}

/// Take the header apart: `public static void greet()`.
///
///   - `public static` are the same words you have been writing on `main`.
///   - `void` is the RETURN TYPE and means "this method does not hand anything back".
///   - `greet` is the name, in camelCase, usually a verb.
///   - `()` holds the parameters. Empty means none.
///
/// Defining a method does nothing by itself. The line `greet();` in `main` is the CALL, and that is when the
/// body runs. Call it three times and you get three greetings.
///
/// WHY: Java starts at `main`, so a method you write but never call is never used. Order in the file does not
/// matter. `greet` could be written below `main`.

/// ### Topic 3: Parameters and return values ###
/// Methods become powerful when they take input and give back a result.
///
///   - PARAMETERS are the inputs, declared in the brackets like little variables: `int a, int b`.
///   - ARGUMENTS are the actual values you pass when calling: `add(5, 7)`.
///   - The RETURN VALUE is the answer the method hands back, using the keyword `return`. The return type in the
///     header (here `int`) must match what is returned.

public class Main {
    public static int add(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) {
        int result = add(5, 7);
        System.out.println("Sum: " + result);
    }
}

/// When `add(5, 7)` runs, `a` becomes 5 and `b` becomes 7 inside the method. The `return` sends 12 back to the
/// place where the call was made, so `int result = add(5, 7);` is effectively `int result = 12;`.
///
/// JARGON: People mix up the two words. A parameter is the name in the method's definition. An argument is the
/// value you supply when calling it. Parameter = the empty slot; argument = what you put in the slot.
///
/// A method can be used anywhere a value of its type is allowed, including inside other expressions.

public class Main {
    public static int square(int x) {
        return x * x;
    }

    public static boolean isEven(int n) {
        return n % 2 == 0;
    }

    public static double average(int a, int b) {
        return (a + b) / 2.0;
    }

    public static void main(String[] args) {
        System.out.println(square(6));
        System.out.println(square(2) + square(3));
        System.out.println(isEven(10));
        System.out.println(average(7, 10));

        if (isEven(8)) {
            System.out.println("8 is even");
        }
    }
}

/// Naming a yes/no method `isEven` or `hasItems` makes the `if` read like English. That is one of the small
/// joys of splitting code into methods.
///
/// TIP: A good method does ONE thing and has a name that says what. If you cannot name it without using "and",
/// it probably wants to be two methods.

/// ### Topic 4: What happens to my variables? ###
/// Arguments are passed BY VALUE: the method receives a copy. Changing the parameter inside the method does not
/// change the variable outside.

public class Main {
    public static void tryToChange(int x) {
        x = 100;
        System.out.println("Inside: " + x);
    }

    public static void main(String[] args) {
        int number = 5;
        tryToChange(number);
        System.out.println("Outside: " + number);
    }
}

/// The original stayed at 5. The method was given a photocopy of the value. (Objects and arrays behave a little
/// differently, and the lesson on call by value explains exactly how.)
///
/// Variables declared inside a method also live only inside it. They are LOCAL variables, created when the method
/// starts and gone when it ends. Another method cannot see them.

public class Main {
    public static void setup() {
        int secret = 42;
    }

    public static void main(String[] args) {
        setup();
        System.out.println(secret);
    }
}
/// @error

/// The compiler says "cannot find symbol". `secret` only exists inside `setup`. The way to get information out of a
/// method is to return it, and the way to get it in is a parameter.

/// ### Topic 5: Mistakes with methods ###
/// A method that promises a value must deliver on every path. If its return type is not `void`, forgetting
/// `return` is a compile error.

public class Main {
    public static int sign(int n) {
        if (n > 0) {
            return 1;
        } else if (n < 0) {
            return -1;
        }
    }

    public static void main(String[] args) {
        System.out.println(sign(5));
    }
}
/// @error

/// The compiler notices that when `n` is 0 no branch returns, and complains "missing return statement". Adding a
/// final `return 0;` or an `else` fixes it.
///
/// Another common mistake is calling a method with the wrong arguments: too few, too many, or the wrong type.

public class Main {
    public static int add(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) {
        System.out.println(add(5));
        System.out.println(add("5", "7"));
    }
}
/// @error

/// Both calls fail. Java checks that the number and the types of the arguments match the parameters. This is the
/// compiler protecting you again.
///
/// One more, which confuses many people at first: why do we write `static` on every method so far?

public class Main {
    public void hello() {
        System.out.println("hello");
    }

    public static void main(String[] args) {
        hello();
    }
}
/// @error

/// `main` is `static`, which means it belongs to the class and runs without any object existing. A method
/// without `static` belongs to an object, so you cannot call it from `main` until you have made one. For this
/// whole stage, write `public static` on every method. The lessons on classes and the static keyword explain the
/// difference in full.

/// ### Topic 6: Methods that call methods, and recursion ###
/// Methods can call other methods. A method may even call ITSELF, which is called RECURSION. This works when each
/// call solves a slightly smaller piece of the problem and there is a case simple enough to answer directly,
/// the BASE CASE, to stop the chain.
///
/// The factorial of 4 (written 4!) is 4 x 3 x 2 x 1. Notice that 4! = 4 x 3!, so the problem contains a smaller
/// copy of itself.

public class Main {
    public static int factorial(int n) {
        if (n <= 1) {
            return 1;                      // base case: stop here
        }
        return n * factorial(n - 1);       // smaller problem
    }

    public static void main(String[] args) {
        System.out.println(factorial(4));
        System.out.println(factorial(5));
    }
}

/// Follow `factorial(3)`: it needs `3 * factorial(2)`, which needs `2 * factorial(1)`, which returns 1 straight
/// away. Then the answers come back up the chain: 2 x 1 = 2, then 3 x 2 = 6.
///
/// MISTAKE: Without a base case, or with one that is never reached, the method calls itself forever until the
/// program crashes with a `StackOverflowError`. Always ask "what is the simplest case, and does every call move
/// toward it?".
///
/// REMEMBER: Methods are the first step toward organising a big program. In the Classes and Objects lessons you
/// will see the same idea one level up: classes group methods together with the data they work on.

/// ============================================================================
/// YOUR TURN
///   1. Write `int max(int a, int b)` that returns the larger number, and use it to find the largest of three.
///   2. Write `boolean isPrime(int n)` and print all the primes below 50.
///   3. Write `void printStars(int n)` that prints a row of n stars.
///   4. Write `double celsiusToFahrenheit(double c)` and print a table from 0 to 100 in steps of 10.
///   5. Write a recursive method that returns the sum of the numbers from 1 to n.
///   6. Make `main` call a method you wrote with the wrong number of arguments, and read the error.
/// ============================================================================
